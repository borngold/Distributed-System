package com.ds.maze;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public interface P2PBase extends Remote {
	
public static final String SERVICE_NAME = "PEERIMPLEMENT"; 
	
	public void connectToServer(String peerIp,ClientConnect connect) throws RemoteException;
	
	public HashMap<String, Object> moveToLocation(String keyPressed, String playerId) throws RemoteException;
	
	public void serverToServer(HashMap<String, Object> gameState, ConcurrentLinkedQueue<String> clientList,AtomicInteger [][] gridState) throws RemoteException;
	
	public void startBackup() throws RemoteException;
	
	public void heartBeat(String myKey, Notify notify) throws RemoteException; //To check whether the client is alive


}
