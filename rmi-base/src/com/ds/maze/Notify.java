package com.ds.maze;

import java.rmi.*;

public interface Notify extends Remote {
    public void onSuccess() throws RemoteException;
    public void onFailure(String crashedClient) throws RemoteException;
}
