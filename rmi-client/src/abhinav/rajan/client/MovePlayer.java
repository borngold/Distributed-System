package abhinav.rajan.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.abhinav.rajan.ChangeCoordinates;
import com.abhinav.rajan.Notify;
import com.abhinav.rajan.PolicyFileLocator;

public class MovePlayer{
	private static ChangeCoordinates changecord;
	private static String myKey;
	
	public MovePlayer(){
		
		myKey=new ClientKeygen().genKey();
		
	}

	

	public void doCustomRmiHandling() {
		Registry registry;
		try {
			//Set system properties
			System.setProperty("java.security.policy", PolicyFileLocator.getLocationOfPolicyFile());
			registry = LocateRegistry.getRegistry();
			changecord= (ChangeCoordinates)registry.lookup(ChangeCoordinates.SERVICE_NAME);
			AtomicInteger[][] inputarr=new AtomicInteger[10][10];
			for(int i=0;i<10;i++)
				for(int j=0;j<10;j++){
					inputarr[i][j]= new AtomicInteger(0);
;
				}
			 
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
			System.out.println("MY STATE"+"==>"+connect.get(myKey));
			System.out.println("NO_OF_PLAYERS"+"==>"+connect.get("NO_OF_PLAYERS"));
			System.out.println("GRID WITH TREASURES");
			
			AtomicInteger[][] grid=(AtomicInteger[][]) connect.get("GRID");
			for(int i=0;i<10;i++){
				for(int j=0;j<10;j++){
					System.out.print(grid[i][j]+" ");
				}
				System.out.println("");
			}
		
			
			System.out.println("_________________________");
			
			//Wait for a second before executing the moves
			Thread.sleep(1000);
						 
						Random random=new Random();
						String move;
						while(true){
		            	int map=random.nextInt(4);
		            	switch(map){
		            	case 0:move="LEFT";
		            			break;
		            	case 1:move="RIGHT";
		            			break;
		            	case 2: move="UP";
		            			break;
		            	default: move="DOWN";
		            			break;
		            	
		            	}
		            	
		            		HashMap<String, Object> afterMove=changecord.moveToLocation(grid, move,myKey);
		            		
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
		        			
		        			final AtomicInteger[][] gridAfterMove=(AtomicInteger[][]) afterMove.get("GRID");
		        			for(int i=0;i<10;i++){
		        				for(int j=0;j<10;j++){
		        					System.out.print(grid[i][j]+" ");
		        				}
		        				System.out.println("");
		        			}
		        		
		        			
		        			System.out.println("_______END OF AFTER MOVE________");
		        			for(int i=0;i<10;i++)
		        				for(int j=0;j<10;j++)
		        			       grid[i][j].set(gridAfterMove[i][j].get());
		        			
		        			//Move once in every 1000s  
		        			Thread.sleep(1000);
		        		
						}	
						}
								            	
		           
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
		
	}
	 public static void main(String[] args) {
		 
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
					System.out.println("\n All players alive");				
				}

				@Override
				public void onFailure(String crashedClient) throws RemoteException {
					System.out.println(crashedClient+" crashed");
					
				}

			 
		 }

	 
	 
	
}
