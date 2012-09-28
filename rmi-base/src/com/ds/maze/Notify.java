package com.ds.maze;

import java.rmi.*;
import java.util.HashMap;

public interface Notify extends Remote {
    public void onSuccess(HashMap<String,Object> gameState) throws RemoteException;
    public void onFailure(String crashedClient,HashMap<String,Object> gameState) throws RemoteException;
}
