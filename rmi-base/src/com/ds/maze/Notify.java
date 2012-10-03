package com.ds.maze;

import java.rmi.*;
import java.util.HashMap;

/**
 * 
 * Class contains the call back methods called by the server when the client requests a 
 * continuous game state update
 *
 */

public interface Notify extends Remote {
    public void onSuccess(HashMap<String,Object> gameState) throws RemoteException;
    public void onFailure(String crashedClient,HashMap<String,Object> gameState) throws RemoteException;
}
