import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.ds.maze.GlobalInfoP2P;
import com.ds.maze.Notify;
import com.ds.maze.P2PBase;
import com.ds.maze.PlayerInfoP2P;
import com.ds.maze.PolicyFileLocator;

public class PlayerMoveImplement implements P2PBase {
	
	private ConcurrentLinkedQueue<String> peerList;
	private static AtomicInteger NUMBER_OF_PLAYERS;
	private static int XCORD;
	private static int YCORD;
	private static AtomicInteger[][] initGrid;
	private static HashMap <String,Object> connectReturn;
	private static boolean CONNECT_FLAG;
	private static int sumOfTreasures;
	private static boolean trasuresExist;
	private static ConcurrentHashMap<String,Long> peerHeartBeatUpdate;
	private static P2PBase backupServer;
	
	
	public PlayerMoveImplement(){
		
		
		//Instantiate all class variables	
		peerList=new ConcurrentLinkedQueue<String>();
		connectReturn=new HashMap<String,Object>();
		NUMBER_OF_PLAYERS=new AtomicInteger();
		initGrid= new AtomicInteger[10][10];
		CONNECT_FLAG=true;
		XCORD=7;
		YCORD=3;
		sumOfTreasures=0;
		trasuresExist=true;
		peerHeartBeatUpdate=new ConcurrentHashMap<String,Long>();
		backupServer = null;
		//Making the number of treasure from 0 to 4
		Random random=new Random();
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				initGrid[i][j]=new AtomicInteger(random.nextInt(5)); 
				sumOfTreasures+=initGrid[i][j].get();
			}	
		}
		
	}

	@Override
	public HashMap<String, Object> connectToServer(String clientKey, String peerIp)
			throws RemoteException {
		if(CONNECT_FLAG){
		//Maintain a list of all available servers
		peerList.add(peerIp);
		
		//Instantiate the Global and Player Info beans to update the information as players connect
		
		PlayerInfoP2P playerInfo=new PlayerInfoP2P();
		GlobalInfoP2P globalInfo=new GlobalInfoP2P();
		playerInfo.setxCord(XCORD);
		playerInfo.setyCord(YCORD);
		playerInfo.setNumberOftreasures(0);
		playerInfo.setIpAddress(peerIp);
		
		updateGlobalInfo(clientKey,playerInfo,globalInfo);
		
		//Start the thread to check for client heart beats every 10 seconds
		Thread th=new Thread(new CheckForAndUpdateFailures());
		th.start();
				
				
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//There should be atleast one player to begin with.
			/*if(peerList.size() < 1){
					return null;
				}*/
			
			connectToBackup();

			CONNECT_FLAG=false;
			return connectReturn;
		}else{
			return null;
		}
	}
	
	private void connectToBackup() throws RemoteException{
		if(peerList.size() > 1){
			int loopCount = 0;
			String backUpIpAddr = null;
			for(String ipaddr:peerList){
				if(loopCount == 2)
					break;
				backUpIpAddr = ipaddr;
				loopCount++;			
					
			}
				
			System.setProperty("java.security.policy", PolicyFileLocator.getLocationOfPolicyFile());
			Registry registry = LocateRegistry.getRegistry(backUpIpAddr,9000);
			try {
				backupServer = (P2PBase)registry.lookup(P2PBase.SERVICE_NAME);
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendBackupData ();
		}
		
	}
	
	private void sendBackupData () {
		ScheduledExecutorService executor = Executors
	               .newSingleThreadScheduledExecutor();
		Runnable periodicTask = new Runnable() {
			public void run() {
				
				try {
					backupServer.serverToServer(connectReturn, peerList,initGrid);
				} catch (RemoteException e) {
					try {
						connectToBackup();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		};
		executor.scheduleAtFixedRate(periodicTask, 0, 20,
				TimeUnit.MILLISECONDS);
			
	}

	@Override
	public HashMap<String, Object> moveToLocation(String keyPressed,
			String playerId) throws RemoteException {
		
		if(trasuresExist){
			if(connectReturn.get(playerId)==null){
				HashMap <String,Object> error=new HashMap<String,Object>();
				error.put(playerId, "DISCONNECTED");
				return error;
				
			}
		PlayerInfoP2P playerInfo=	(PlayerInfoP2P) connectReturn.get(playerId);
		int xCord=playerInfo.getxCord();
		int yCord=playerInfo.getyCord();
		int flag = 0;
		if(keyPressed.equalsIgnoreCase("L")){
			if(yCord-1>=0){
				flag = 1;
				playerInfo.setyCord(--yCord);
			}		
		}else if(keyPressed.equalsIgnoreCase("R")){
			if((yCord+1)<=9){
				flag = 1;
				playerInfo.setyCord(++yCord);
			}
			
		}else if(keyPressed.equalsIgnoreCase( "U")){
			if((xCord-1)>=0){
				flag = 1;
				playerInfo.setxCord(--xCord);				
			}
			
		}else{
			if(xCord+1<=9){
				flag = 1;
				playerInfo.setxCord(++xCord);
			}
		}
		if(initGrid[xCord][yCord].get()>0 && flag == 1) {
			initGrid[xCord][yCord].set(initGrid[xCord][yCord].decrementAndGet());
			int treasureCollected=playerInfo.getNumberOftreasures();
			playerInfo.setNumberOftreasures(++treasureCollected);
			connectReturn.put(playerId, playerInfo);
			//Convert to integer before returning
			int [][]atomicToIntGrid = new int[10][10];
			for(int i=0;i<10;i++){
				for(int j=0;j<10;j++)
					atomicToIntGrid[i][j]=initGrid[i][j].get();
			}
			
			GlobalInfoP2P globalInfo=(GlobalInfoP2P) connectReturn.get("GLOBALINFO");
			globalInfo.setAtomicToIntGrid(atomicToIntGrid);
			sumOfTreasures--;
			if(sumOfTreasures==0){
				trasuresExist=false;
			}
		}
		
		return connectReturn;
		}else{
			return null;
		}		
		
	}

	@Override
	public void heartBeat(String clientKey, Notify notify) throws RemoteException {
		
long newTimeStamp=Calendar.getInstance().getTimeInMillis();
		
		if(!peerHeartBeatUpdate.keySet().contains(clientKey)){
			
			peerHeartBeatUpdate.put(clientKey, newTimeStamp);
		}
					
		if(peerHeartBeatUpdate.get(clientKey)==-1L){
			notify.onFailure(clientKey,connectReturn);
		}else{
			peerHeartBeatUpdate.put(clientKey, newTimeStamp);
			notify.onSuccess(connectReturn);
		}
		
	}
	
	
	//Private method which populates all global parameters
	private void updateGlobalInfo(String playerID,PlayerInfoP2P playerInfo, GlobalInfoP2P globalInfo){
		XCORD=(XCORD+7)%10;
		YCORD=(YCORD+13)%10;
		NUMBER_OF_PLAYERS.set(NUMBER_OF_PLAYERS.incrementAndGet());
		
		connectReturn.put(playerID, playerInfo);
		globalInfo.setNumberOfplayers(NUMBER_OF_PLAYERS.get());
		globalInfo.setPeerIPList(peerList);
		
		//Convert to integer before returning
		int [][]atomicToIntGrid = new int[10][10];
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++)
				atomicToIntGrid[i][j]=initGrid[i][j].get();
		}
		
		globalInfo.setAtomicToIntGrid(atomicToIntGrid);
		globalInfo.setSumOftreasures(sumOfTreasures);
		
		//update the number of peers
		globalInfo.setPeerIPList(peerList);
		connectReturn.put("GLOBALINFO", globalInfo);
		
		
		
		
	}
	

   //The private class which checks for heart beat update
	
private class CheckForAndUpdateFailures extends Thread{
		
		private static final long UPDATE_INTERVAL = 12000;
		private  long currentTimeStamp;
		@Override
		public void run(){
			
			
			 while(true){
					try {
						currentTimeStamp=Calendar.getInstance().getTimeInMillis();		
			for (Entry<String, Long> entry : peerHeartBeatUpdate.entrySet()) {
				
				if(entry.getValue()==-1)
					break;
				
				if((currentTimeStamp-entry.getValue()) > UPDATE_INTERVAL){
					entry.setValue(-1L);
					//update the number of players in the game
					NUMBER_OF_PLAYERS.set(NUMBER_OF_PLAYERS.decrementAndGet());
					//connectReturn.put("NO_OF_PLAYERS", NUMBER_OF_PLAYERS.get());
					GlobalInfoP2P globalInfo=(GlobalInfoP2P) connectReturn.get("GLOBALINFO");
					globalInfo.setNumberOfplayers(NUMBER_OF_PLAYERS.get());
					//Remove from the global list and also update the peer list queue
					PlayerInfoP2P playerInfo=(PlayerInfoP2P) connectReturn.get(entry.getKey());
					peerList.remove(playerInfo.getIpAddress());
					globalInfo.setPeerIPList(peerList);
					connectReturn.remove(entry.getKey());
					
				}
			}
				Thread.sleep(10000);
				//update
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	  }
	}



	public void serverToServer(HashMap<String, Object> gameState, ConcurrentLinkedQueue<String> clientList,AtomicInteger [][] gridState) throws RemoteException {	
		connectReturn = gameState;
		peerList = clientList; 
		GlobalInfoP2P globalInfo=(GlobalInfoP2P)gameState.get("GLOBALINFO");
		initGrid = gridState;
		sumOfTreasures = globalInfo.getSumOftreasures();
		NUMBER_OF_PLAYERS.set(globalInfo.getNumberOfplayers());
		
	}


	public void startBackup() throws RemoteException {
		
		Thread th=new Thread(new CheckForAndUpdateFailures());
		th.start();
		peerList.remove();
		NUMBER_OF_PLAYERS.set(NUMBER_OF_PLAYERS.decrementAndGet());
		connectToBackup();
	
	}



}
