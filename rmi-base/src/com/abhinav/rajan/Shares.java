package com.abhinav.rajan;

/************************************************************************
*                                                            		*
*	Code: Shares Interface	Filename: shares.java        		*
*                                                            		*
*	Date: 18th November 2000                             		*
*                                                            		*
*	Description: Interface defnition outlining the 6 remote		* 
*		     methods available as part of the Shares Service 	*
*		     implemented by the remote object sharesimpl.       *
*                                                      			*
************************************************************************/	

import java.rmi.*;			// Import RMI package
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public interface Shares
          extends Remote{
	
	public static final String SERVICE_NAME = "ShareOperations";
    
    /********************************************************************
    * Method: ReadSharePrice 						*
    * Description: Read current share price from remote share service.	*
    * Parameters: String ShareCode - unique FTSE reference e.g "BA"	*
    * Returns:	  double - Current price of the requested share. 	*
    ********************************************************************/
    public double ReadSharePrice(String ShareCode)
        throws RemoteException;

    /********************************************************************
    * Method: WriteSharePrice 						*
    * Description: Change the current price of the stated share on the	*
    *		   remote share service.   				*
    * Parameters: String ShareCode - unique FTSE reference e.g "BA"	*
    * Returns:	  Nothing						*
    ********************************************************************/
    public void WriteSharePrice(String ShareCode, double NewPrice)
        throws RemoteException;

    /********************************************************************
    * Method: ReadShareName 					        *
    * Description: Read a share name from the FTSE data structure.	*
    * Parameters: String ShareCode - unique FTSE reference e.g "BA"	*
    * Returns:	  String - Name of the requested share. 		*
    ********************************************************************/
    public String ReadShareName(String ShareCode)	
	throws RemoteException;

    /********************************************************************
    * Method: ReadNumberShares						*
    * Description: Read number of shares remaining.			*
    * Parameters: String ShareCode - unique FTSE reference e.g "BA"	*
    * Returns:	  int- Number of shares.		 		*
    ********************************************************************/ 
    public int ReadNumberShares(String ShareCode)	
	throws RemoteException;

    /********************************************************************
    * Method: AddNewShare						*
    * Description: Add a new share record to the remote share service.	*
    * Parameters: String ShareName - Name of share			*
    *		  String ShareCode - unique FTSE reference 		*
    *		  double InitialPrice - Starting price of new share	*
    *		  int - Initial number of shares			*
    * Returns:	  Nothing 						*
    ********************************************************************/
    public void AddNewShare(String ShareName, String ShareCode, double InitialPrice, int Number)
        throws RemoteException;

    /********************************************************************
    * Method: DeleteShare 						*
    * Description: Delete the requested share from the share service.	*
    * Parameters: String ShareCode - unique FTSE reference 		*
    * Returns:	  Nothing						*
    ********************************************************************/
    public void DeleteShare(String ShareCode)
        throws RemoteException;

    /********************************************************************
    * Method: BuyShares 						*
    * Description: Buy N shares.					*
    * Parameters: String ShareCode - unique FTSE reference 		*
    *		  int Number - Number of shares to buy.			*
    * Returns:	  double - Cost of shares.				*
    ********************************************************************/
    public ArrayList<Double> BuyShares(String ShareCode, int Number)
        throws RemoteException;

    /********************************************************************
    * Method: SellShares 						*
    * Description: Sell N shares.					*
    * Parameters: String ShareCode - unique FTSE reference 		*
    *		  int Number - Number of shares to Sell.		*
    * Returns:	  double - Price obtained.				*
    ********************************************************************/
    public ArrayList<Double> SellShares(String ShareCode, int Number)
        throws RemoteException;
    

    /********************************************************************
    * Method: getNumberOfShares 						*
    * Description: Return number of shares with the company.				*					*
    * Parameters: String ShareCode - unique FTSE reference 		*
    * Returns:	  int - Number of shares with the company.				*
    ********************************************************************/
    public AtomicInteger getNumberOfShares(String ShareCode)
        throws RemoteException;
   
    
    
}

