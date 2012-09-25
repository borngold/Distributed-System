package abhinav.rajan.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import com.abhinav.rajan.PolicyFileLocator;
import com.abhinav.rajan.Shares;

public class StockOperations{
	private Shares shares;
	
	public void doCustomRmiHandling() {
	Registry registry;
	try {
		//Set system properties
		System.setProperty("java.security.policy", PolicyFileLocator.getLocationOfPolicyFile());
		registry = LocateRegistry.getRegistry();
		shares = (Shares)registry.lookup(Shares.SERVICE_NAME);
		new Operartion1().start();
		new Operartion2().start();
		
		
	}catch (RemoteException e) {
		e.printStackTrace();
	} catch (NotBoundException e) {
		e.printStackTrace();
	}
	}

	
	
	public static void main(String []args){
		new StockOperations().doCustomRmiHandling();
		
	}
	
	private class Operartion1 extends Thread {
	      @Override
	      public void run() {
	    	  try {
	    		  for(int i=1;i<=100;i++){
	    		  	 ArrayList<Double> retVal=shares.BuyShares("INF", i*2);
	    		  	System.out.println("Total number of shares bought is "+retVal.get(0)+" and total number of shares with company is "+retVal.get(1));
	    		  }
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	     }
	  }
	
	
	private class Operartion2 extends Thread {
	      @Override
	      public void run() {
	    	  try {
	    		  for(int i=1;i<=100;i++){
	    			  ArrayList<Double> retVal=shares.SellShares("INF", i*2);
	    		  	 System.out.println("Total number of shares sold is "+retVal.get(0)+" and total number of shares with company is "+retVal.get(1));
	    		  }
			} catch (RemoteException e) {
				e.printStackTrace();
			}

	      }
	   }



}
