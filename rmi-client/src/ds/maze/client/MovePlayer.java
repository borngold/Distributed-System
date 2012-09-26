package ds.maze.client;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Set;

import com.ds.maze.ChangeCoordinates;
import com.ds.maze.Notify;
import com.ds.maze.PlayerInfo;
import com.ds.maze.PolicyFileLocator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MovePlayer extends JFrame  {
	private static ChangeCoordinates changecord;
	private static String myKey;
	private Board board;
	private static String addr ="127.0.0.1";
	private static int gridSize = 0;
	public MovePlayer(){
		myKey=new ClientKeygen().genKey();
	}

	public void doCustomRmiHandling() {
		Registry registry;
		int [][] cord;
		try {
			//Set system properties
			System.setProperty("java.security.policy", PolicyFileLocator.getLocationOfPolicyFile());
			registry = LocateRegistry.getRegistry(addr,9000);
			changecord= (ChangeCoordinates)registry.lookup(ChangeCoordinates.SERVICE_NAME);
			 
			HashMap<String, Object> connect=changecord.connectToServer(myKey);
			if(connect==null){
				System.out.println("The game has already started please try again later...");
				System.exit(0);
				
			}
			
			//Once connection has been successfully established, start sending periodic heart beats to the server
			Thread heartbeat=new Thread(new HeartBeatSender());   
			heartbeat.setDaemon(true);
			heartbeat.start();
		    
			

		    //Print the current state 
			
			System.out.println("_________________________");
			System.out.println("MY STATE"+"==>"+((PlayerInfo)connect.get(myKey)).toString());
			System.out.println("NO_OF_PLAYERS"+"==>"+connect.get("NO_OF_PLAYERS"));
			System.out.println("GRID WITH TREASURES");
			gridSize = (Integer) (connect.get("SIZE"));
			Set keys = connect.keySet();
			int i =0, j = 0;
			int np = (Integer)(connect.get("NO_OF_PLAYERS")) ;
			cord = new int[np][2];
			for(Object value : keys){
				j = 0;
				if(!((String)value).equals(myKey) && !((String)value).equals("SIZE") && !(((String)value).equals("NO_OF_PLAYERS")) && !((String)value).equals("GRID") && !((String)value).equals("TREASURE_SUM") ){
				
					PlayerInfo cordinates = (PlayerInfo) connect.get(value);
					cord[i][j] = cordinates.getxCord();
					j++;
					cord[i][j] = cordinates.getyCord();
					i++;
				}
			}
			int[][] grid=(int[][]) connect.get("GRID");
			for(i=0;i<gridSize;i++){
				for(j=0;j<gridSize;j++){
					System.out.print(grid[i][j]+" ");
				}
				System.out.println("");
			}
		
			
			System.out.println("_________________________");
			
			//Wait for a second before executing the moves
			Thread.sleep(1000);
			PlayerInfo myinfo=(PlayerInfo) connect.get(myKey);			
			board = new Board(gridSize,myinfo.getxCord(),myinfo.getyCord(),grid,cord,np);
	        add(board);
	        setTitle("Skeleton");
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setSize(gridSize*77, gridSize*78);
	        setLocationRelativeTo(null);
	        setVisible(true);
	        setResizable(false);
						 
			System.out.println("Please start moving. Enter L/l for LEFT, R/r for RIGHT, U/u for UP and any key for DOWN ");
			
						            	
		           
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		
	}
	public void move(String move) throws InterruptedException {
		
		HashMap<String, Object> afterMove = null;
		int[][] gridAfterMove = null;
		try {
			afterMove = changecord.moveToLocation(move,myKey);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		if(afterMove==null){
 			System.out.println("Game Over...");
 			System.exit(0);
 		}else if(afterMove.get(myKey)=="DISCONNECTED"){
 			System.out.println("Time out error. Game over!!");
 			System.exit(0);
 			
 		}else{
 			
 			System.out.println("________AFTER MOVE__________");
			System.out.println("MY STATE"+"==>"+afterMove.get(myKey));
			System.out.println("NO_OF_PLAYERS"+"==>"+afterMove.get("NO_OF_PLAYERS"));
			System.out.println("GRID WITH TREASURES");
			
			gridAfterMove=(int[][]) afterMove.get("GRID");
			for(int i=0;i<gridSize;i++){
				for(int j=0;j<gridSize;j++){
					System.out.print(gridAfterMove[i][j]+" ");
				}
				System.out.println("");
			}
		
			
			System.out.println("_______END OF AFTER MOVE________");
		
 		}	
 		updateTable(afterMove);
 		
 		Thread.sleep(100);
					
	}
	
	public void updateTable(HashMap<String, Object> afterMove){
 		PlayerInfo myinfo=(PlayerInfo) afterMove.get(myKey);
    	
 		Set<String> keys = afterMove.keySet();
		int i =0, j = 0;
		int np = (Integer) afterMove.get("NO_OF_PLAYERS") ;
		int[][] cord = new int[np-1][2];
		for(Object value : keys){
			j = 0;
			if(!((String)value).equals(myKey) && !((String)value).equals("SIZE") &&  !(((String)value).equals("NO_OF_PLAYERS")) && !((String)value).equals("GRID") && !((String)value).equals("TREASURE_SUM") ){
			
				PlayerInfo cordinates=(PlayerInfo) afterMove.get(value);
				cord[i][j] = cordinates.getxCord();
				j++;
				cord[i][j] = cordinates.getyCord();
				i++;
			}
		}
		this.board.drawAgain(myinfo.getxCord(),myinfo.getyCord(),(int[][]) afterMove.get("GRID"),cord,np);
		
	}
	
	public static void main(String[] args) {
		if( args != null)
			addr = args[0];
		new MovePlayer().doCustomRmiHandling();
	        
	}
	 	 
	 private static class HeartBeatSender extends Thread{
		 @Override
		 public void run(){
			 while(true){
				 try {
					 Notify note=new NotifyImpl();
					 changecord.heartBeat(myKey,note);
					 Thread.sleep(2000);
				 } catch (RemoteException e) {
					 e.printStackTrace(); 
				 } catch (InterruptedException e) {
				
					 e.printStackTrace();
				 }
			 
			 }
		 }
		 
	 }
	 
	 @SuppressWarnings("serial")
	 private static class NotifyImpl extends UnicastRemoteObject implements Notify{
		 protected NotifyImpl() throws RemoteException {
			 super();
		 }

		 @Override
		 public void onSuccess() throws RemoteException {
			 //System.out.println("\n All players alive");				
		 }
		 
		 @Override
		 public void onFailure(String crashedClient) throws RemoteException {
			 System.out.println(crashedClient+" crashed");
			 
		 }
		 
		 
	 }

	 public class Board extends JPanel  {
		 
		 private String location = "src/image.png";
		 private int boardSize;
		 private Image image;
		 private int x = 10;
		 private int y = 10;
		 private int [][]grid;
		 private int [][] players;
		 private int numberPlayers;
		 private int gridSize;
		 
		 public Board(int boardSize,int x, int y, int[][] grid2,int [][] players,int np) {
			 
			 setFocusable(true);
			 addKeyListener(new TAdapter());
			 setDoubleBuffered(true);
			 gridSize = boardSize;
			 this.boardSize=boardSize * 75;
			 this.x = x;
			 this.y = y;
			 this.grid = grid2;
			 this.players = players;
			 this.numberPlayers = np;
			 try {
				 image = ImageIO.read(new File(location));
			 } catch (IOException e) {
				 e.printStackTrace();
			 }


		 }


		 public void paint(Graphics g) {
			 super.paint(g);
			 Graphics2D g2d = (Graphics2D)g;
			 g2d.setColor(Color.RED);        
			 g2d.drawImage(image,40+y*75,40+x*75, this);
			 for( int i = 2; i <= boardSize+2; i=i+75 ){
				 g2d.drawLine(2, i, boardSize+2, i);
				 g2d.drawLine(i, 2, i, boardSize+2);
			 }
			 for(int k=0;k<gridSize;k++){
				 for(int j=0;j<gridSize;j++){
					 g.drawString(Integer.toString(grid[k][j]),20+j*75,20+k*75);					 
				 }	
			 }
			 if(numberPlayers > 1){
				 for(int k=0; k<(numberPlayers-1);k++){
					 int xCrd = players[k][0];
					 int yCrd = players[k][1];
					 g.drawString("P",20+yCrd*75,50+xCrd*75);
					 
				 }				 
			 }


		        
			 Toolkit.getDefaultToolkit().sync();
			 g.dispose();
		 }
		    
		    
		 public void drawAgain(int newX, int newY,int [][] newGrd,int [][] newPlayers,int newNp) {
			 x = newX ;
			 y = newY ;
			 grid = newGrd;
			 players = newPlayers;
			 numberPlayers = newNp;
			 
			 repaint();  
		    	
		 }

		 private class TAdapter extends KeyAdapter {
			 
			 public void keyPressed(KeyEvent e) {
				 int key = e.getKeyCode();
				 switch(key) { 
				 	case KeyEvent.VK_RIGHT: 
				 		try {
				 			MovePlayer.this.move("r");
				 		} catch (InterruptedException e1) {
				 			// TODO Auto-generated catch block
				 			e1.printStackTrace();
						} 
				 		break; 

				 	case KeyEvent.VK_LEFT: 
				 		try {
				 			MovePlayer.this.move("l");
				 		} catch (InterruptedException e1) {
				 			// TODO Auto-generated catch block
				 			e1.printStackTrace();
				 		} 
				 		break; 

				 	case KeyEvent.VK_UP: 
				 		try {
				 			MovePlayer.this.move("u");
				 		} catch (InterruptedException e1) {
				 			// TODO Auto-generated catch block
				 			e1.printStackTrace();
				 		} 
				 		break; 

				 	case KeyEvent.VK_DOWN: 
				 		try {
				 			MovePlayer.this.move("d");
				 		} catch (InterruptedException e1) {
				 			// TODO Auto-generated catch block
				 			e1.printStackTrace();
				 		}
				 		break; 
				 }
			 }
			
		 }
		    
	 }
	 	
}
