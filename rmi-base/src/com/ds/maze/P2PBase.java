package com.ds.maze;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The interface which is invoked by the client and implemented by the server
 * 
 */
public interface P2PBase extends Remote {

   /**
    * The RMI service name
    */
   public static final String SERVICE_NAME = "PEERIMPLEMENT";

   /**
    * @param peerIp -- Uniquely identifies all clients and the server also uses
    * this to have list of all players connected
    * @param connect
    * @throws RemoteException
    * 
    * This method is called when all clients connect to the server
    */
   public void connectToServer(String peerIp, ClientConnect connect)
         throws RemoteException;

   /**
    * @param keyPressed
    * @param playerId
    * @return game state
    * @throws RemoteException Method called by a client when it wants to make a
    * move
    */
   public HashMap<String, Object> moveToLocation(String keyPressed,
         String playerId) throws RemoteException;

   /**
    * @param gameState
    * @param clientList
    * @param gridState
    * @throws RemoteException
    * 
    * This method represents the interaction between main and the backup servers
    */
   public void serverToServer(HashMap<String, Object> gameState,
         ConcurrentLinkedQueue<String> clientList, AtomicInteger[][] gridState)
         throws RemoteException;

   /**
    * @throws RemoteException Called by clients when they detect a server crash
    * so as to connect to the backup server
    */
   public void startBackup() throws RemoteException;

   /**
    * @param myKey
    * @param notify
    * @throws RemoteException
    * 
    * represents the heart beat method called by all clients to update their
    * game state continuously
    */
   public void heartBeat(String myKey, Notify notify) throws RemoteException;
}
