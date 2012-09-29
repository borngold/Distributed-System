package com.ds.maze;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public interface P2PBase extends Remote {
	
public static final String SERVICE_NAME = "PEERIMPLEMENT"; 
	
	HashMap<String, Object> connectToServer(String clientKey, String peerIp) throws RemoteException;
	
	HashMap<String, Object> moveToLocation(String keyPressed, String playerId) throws RemoteException;
	
	void serverToServer(HashMap<String, Object> gameState, ConcurrentLinkedQueue<String> clientList,AtomicInteger [][] gridState) throws RemoteException;
	
	void startBackup() throws RemoteException;
	
	void heartBeat(String myKey, Notify notify) throws RemoteException; //To check whether the client is alive


}
