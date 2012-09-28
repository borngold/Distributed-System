package com.ds.maze;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface P2PBase extends Remote {
	
public static final String SERVICE_NAME = "PEERIMPLEMENT"; 
	
	HashMap<String, Object> connectToServer(String clientKey, String peerIp) throws RemoteException;
	
	HashMap<String, Object> moveToLocation(String keyPressed, String playerId) throws RemoteException;
	
	void heartBeat(String myKey, Notify notify) throws RemoteException; //To check whether the client is alive


}
