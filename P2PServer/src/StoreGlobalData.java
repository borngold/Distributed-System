import java.net.InetAddress;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class StoreGlobalData {
	
	private static int UNIQUE_ID=1000;
	private static AtomicInteger NUMBER_OF_PLAYERS=new AtomicInteger();
	private static int XCORD=7;
	private static int YCORD=3;
	private static AtomicInteger[][] initGrid= new AtomicInteger[10][10];
	private static HashMap <String,Object> connectReturn;
	private static boolean CONNECT_FLAG=true;
	private static int sumOfTreasures=0;
	private static boolean trasuresExist=true;
	private static ConcurrentHashMap<String, InetAddress> peerList;
	/**
	 * @return the uNIQUE_ID
	 */
	public static int getUNIQUE_ID() {
		return UNIQUE_ID;
	}
	/**
	 * @param uNIQUE_ID the uNIQUE_ID to set
	 */
	public static void setUNIQUE_ID(int uNIQUE_ID) {
		UNIQUE_ID = uNIQUE_ID;
	}
	/**
	 * @return the nUMBER_OF_PLAYERS
	 */
	public static AtomicInteger getNUMBER_OF_PLAYERS() {
		return NUMBER_OF_PLAYERS;
	}
	/**
	 * @param nUMBER_OF_PLAYERS the nUMBER_OF_PLAYERS to set
	 */
	public static void setNUMBER_OF_PLAYERS(AtomicInteger nUMBER_OF_PLAYERS) {
		NUMBER_OF_PLAYERS = nUMBER_OF_PLAYERS;
	}
	/**
	 * @return the xCORD
	 */
	public static int getXCORD() {
		return XCORD;
	}
	/**
	 * @param xCORD the xCORD to set
	 */
	public static void setXCORD(int xCORD) {
		XCORD = xCORD;
	}
	/**
	 * @return the yCORD
	 */
	public static int getYCORD() {
		return YCORD;
	}
	/**
	 * @param yCORD the yCORD to set
	 */
	public static void setYCORD(int yCORD) {
		YCORD = yCORD;
	}
	/**
	 * @return the initGrid
	 */
	public static AtomicInteger[][] getInitGrid() {
		return initGrid;
	}
	/**
	 * @param initGrid the initGrid to set
	 */
	public static void setInitGrid(AtomicInteger[][] initGrid) {
		StoreGlobalData.initGrid = initGrid;
	}
	/**
	 * @return the connectReturn
	 */
	public static HashMap<String, Object> getConnectReturn() {
		return connectReturn;
	}
	/**
	 * @param connectReturn the connectReturn to set
	 */
	public static void setConnectReturn(HashMap<String, Object> connectReturn) {
		StoreGlobalData.connectReturn = connectReturn;
	}
	/**
	 * @return the cONNECT_FLAG
	 */
	public static boolean isCONNECT_FLAG() {
		return CONNECT_FLAG;
	}
	/**
	 * @param cONNECT_FLAG the cONNECT_FLAG to set
	 */
	public static void setCONNECT_FLAG(boolean cONNECT_FLAG) {
		CONNECT_FLAG = cONNECT_FLAG;
	}
	/**
	 * @return the sumOfTreasures
	 */
	public static int getSumOfTreasures() {
		return sumOfTreasures;
	}
	/**
	 * @param sumOfTreasures the sumOfTreasures to set
	 */
	public static void setSumOfTreasures(int sumOfTreasures) {
		StoreGlobalData.sumOfTreasures = sumOfTreasures;
	}
	/**
	 * @return the trasuresExist
	 */
	public static boolean isTrasuresExist() {
		return trasuresExist;
	}
	/**
	 * @param trasuresExist the trasuresExist to set
	 */
	public static void setTrasuresExist(boolean trasuresExist) {
		StoreGlobalData.trasuresExist = trasuresExist;
	}
	/**
	 * @return the peerList
	 */
	public static ConcurrentHashMap<String, InetAddress> getPeerList() {
		return peerList;
	}
	/**
	 * @param peerList the peerList to set
	 */
	public static void setPeerList(ConcurrentHashMap<String, InetAddress> peerList) {
		StoreGlobalData.peerList = peerList;
	}
	
	
	
}
