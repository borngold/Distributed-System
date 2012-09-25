package com.cs5223.maze;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
//Defining the interface for client server interaction 

public interface ChangeCoordinates extends Remote {
	public static final String SERVICE_NAME = "ChangeCord"; 
	
	HashMap<String, Object> connectToServer(String clientKey) throws RemoteException;
	
	HashMap<String, Object> moveToLocation(String keyPressed, String playerId) throws RemoteException;
	
	void heartBeat(String myKey, Notify notify) throws RemoteException; //To check whether the client is alive

}
