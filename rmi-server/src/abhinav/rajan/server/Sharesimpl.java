package abhinav.rajan.server;

/*******************************************************************************************
*	Code: sharesimpl remote object		Filename: sharesimpl.java                  *
*	                                                                                   *
*	Date: 18th November 2000                                                           *
*                                                                                          *
*	The remote object contains a shared data structure holding the                     *
*	prices of 100 shares to simulate the FTSE 100. This is a simple                    * 
*	array of elements. Each element has a name, share code, starting price	   	   *
*	, a current price and the number of available shares.		                   *
*	                                                                                   *
*	This code is DELIBERATELY INCOMPLETE. The issue of dealing with conflicting        * 
*	threads needs to be added. Also, the implementation of three methods needs         *
*	to be completed - ReadNumberShares, BuyShares & SellShares.                        *
*	                                                                                   *
********************************************************************************************/

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.rmi.*;

import com.abhinav.rajan.Shares;

/******************************************************************************
* Class: ShareElement							      * 
* Description: Share record held in the FTSE data structure 		      *
******************************************************************************/

class ShareElement{

	String ShareName;	// Name of the share
	double SharePrice;	// Current price
	double StartingPrice;	// Starting Price
	String ShareCode;       // Unique reference of share 
	AtomicInteger NumberofShares;

	// Constructor
	public ShareElement(AtomicInteger Number, String Name, double Price, String Code){
		this.ShareName=Name;
		this.SharePrice=Price;
		this.StartingPrice=Price;
		this.ShareCode=Code;	
		this.NumberofShares=Number;
	}
}

@SuppressWarnings("serial")
public class Sharesimpl implements Shares {

    public static int DELAYTIME = 5; 			// Length in seconds for delay
    public long OPENINGTIME;

    //Create the FTSE data structure - a Hashtable with initial capacity of 100
    @SuppressWarnings("rawtypes")
	ConcurrentHashMap<String,ShareElement> FTSE = new ConcurrentHashMap<String,ShareElement>(100);
    
    // Constructor
    @SuppressWarnings("unchecked")
	public Sharesimpl()
        throws RemoteException {
        super();

	// Store opening time
	OPENINGTIME = java.lang.System.currentTimeMillis();

	// Add shares to the data structure
       FTSE.put("BA", new ShareElement(new AtomicInteger(1500), "British Airways",10.54,"BA"));
       FTSE.put("LU", new ShareElement(new AtomicInteger(1750), "Lucent Technologies",6.54,"LU"));
       FTSE.put("BT", new ShareElement(new AtomicInteger(1900),"British Telecom",18.45,"BT"));
       FTSE.put("MS", new ShareElement(new AtomicInteger(1900), "Microsoft",180.45,"MS"));
       FTSE.put("APP", new ShareElement(new AtomicInteger(1900), "Apple",280.45,"APPLE"));
       FTSE.put("GOO", new ShareElement(new AtomicInteger(2900), "Google",480.45,"GOO"));
       FTSE.put("INF", new ShareElement(new AtomicInteger(100), "Infosys",1.00,"INF"));
       
	
    }

    /********************************************************************
    * Method: ReadSharePrice 						*
    * Description: Read a share price from the FTSE data structure.	*
    * Parameters: String ShareCode - unique FTSE reference e.g "BA"	*
    * Returns:	  double - Current price of the requested share. 	*
    ********************************************************************/ 

    public double ReadSharePrice(String ShareCode)
	throws RemoteException {
	
        // Test to see if the sharecode exists - throw exception if doesn't
	if (!FTSE.containsKey(ShareCode))
		throw new RemoteException("Share does not exist");

	//Return the current price of a given ShareCode
        ShareElement Temp = (ShareElement) FTSE.get(ShareCode);
        return Temp.SharePrice;
    }

    /********************************************************************
    * Method: ReadShareName 						*
    * Description: Read a share name from the FTSE data structure.	*
    * Parameters: String ShareCode - unique FTSE reference e.g "BA"	*
    * Returns:	  String - Name of the requested share. 		*
    ********************************************************************/ 

    public String ReadShareName(String ShareCode)
	throws RemoteException {
	
        // Test to see if the sharecode exists - throw exception if doesn't
	if (!FTSE.containsKey(ShareCode))
		throw new RemoteException("Share does not exist");

	//Return the current price of a given ShareCode
        ShareElement Temp = (ShareElement) FTSE.get(ShareCode);
        return Temp.ShareName;
    }

    /********************************************************************
    * Method: ReadNumberShares						*
    * Description: Read number of shares remaining.			*
    * Parameters: String ShareCode - unique FTSE reference e.g "BA"	*
    * Returns:	  int- Number of shares.		 		*
    ********************************************************************/ 

    public int ReadNumberShares(String ShareCode)
	throws RemoteException {

    	return FTSE.size();
    }

    /********************************************************************
    * Method: WriteSharePrice 						*
    * Description: Change the current price of the stated share.	*  
    * Parameters: String ShareCode - unique FTSE reference e.g "BA"	*
    * Returns:	  Nothing						*
    ********************************************************************/

    public void WriteSharePrice(String ShareCode, double NewPrice)
	throws java.rmi.RemoteException {
	
	// Test to see if sharecode exists - throw exception if doesn't
	if (!FTSE.containsKey(ShareCode))
		throw new RemoteException("Share does not exist");

	/****************************************************************************************
	* N.B. THE FOLLOWING IMPLEMENTATION OF A WRITE IS FOR DEMONSTRATION PURPOSES - It will	*
	* allow you to illustrate that your code is thread safe. IT IS NOT THE MOST EFFICIENT	*
	* METHOD OF WRITING TO A HASH TABLE!! DO NOT USE IT AS A TEMPLATE FOR Buy & Sell.	*
	*											*
	* AFTER TASK 7.2 HAS BEEN DEMONSTRATED YOU MAY CHANGE THIS METHOD & REMOVE THE DELAY	*
	****************************************************************************************/
	
	long TimeOne = java.lang.System.currentTimeMillis();

	// Update the price by removing a copy of object from structure and then re-inserting
	ShareElement Temp = (ShareElement) FTSE.remove(ShareCode);
	ShareElement Temp2 =  new ShareElement( Temp.NumberofShares, Temp.ShareName, Temp.SharePrice, ShareCode);
	FTSE.put(ShareCode, Temp2);
	Temp.SharePrice = NewPrice;
	
	// Insert delay for demonstration purposes
	do{	
	}while ((TimeOne+(DELAYTIME*1000))>java.lang.System.currentTimeMillis());
		
	FTSE.put(ShareCode, Temp);			
    }

    /********************************************************************
    * Method: AddNewShare						*
    * Description: Add a new share record to the FTSE data structure.	*
    * Parameters: String ShareName - Name of share			*
    *		  String ShareCode - unique FTSE reference 		*
    *		  double InitialPrice - Starting price of new share	*
    * Returns:	  Nothing 						*
    ********************************************************************/

    public void AddNewShare(String ShareName, String ShareCode, double InitialPrice, int Number)
	throws RemoteException{

	//Verify ShareCode (1-3 letters)(not taken)
	if (FTSE.containsKey(ShareCode))
		throw new RemoteException("Share does not exist");

	if (!((ShareCode.length()>=1)&&(ShareCode.length()<=3)))
		throw new RemoteException("Illegal share code");

	FTSE.put(ShareCode, new ShareElement(new AtomicInteger(Number), ShareName,InitialPrice,ShareCode));
    }

    /********************************************************************
    * Method: DeleteShare 						*
    * Description: Delete the requested share from the FTSE index.	*
    * Parameters: String ShareCode - unique FTSE reference 		*
    * Returns:	  Nothing						*
    ********************************************************************/

    public void DeleteShare(String ShareCode)
	throws RemoteException{

        // Test to see if sharecode exists - throw exception if doesn't
	if (FTSE.containsKey(ShareCode))
		FTSE.remove(ShareCode);
	else
		throw new RemoteException("Share does not exist");

	
    }

    /********************************************************************
    * Method: BuyShares 						*
    * Description: Buy N Shares				*
    * Parameters: String ShareCode - unique FTSE reference 		*
    *		  int Number- Number of shares to buy			*
    * Returns:	  double - cost of shares						*
    ********************************************************************/

    public synchronized ArrayList<Double> BuyShares(String ShareCode, int Number)
	throws RemoteException{
    	// Test to see if sharecode exists - throw exception if doesn't
    	if (FTSE.containsKey(ShareCode)){
    		ShareElement share=FTSE.get(ShareCode);
    		share.NumberofShares.set(share.NumberofShares.addAndGet(-Number));
    		ArrayList<Double> retList=new ArrayList<Double>(); 
    		retList.add(share.SharePrice*Number);
    		retList.add((double) share.NumberofShares.intValue());
    		return retList;
    	}else
    		throw new RemoteException("Share does not exist");

    }

    /********************************************************************
    * Method: SellShares 						*
    * Description: Sell N shares				*
    * Parameters: String ShareCode - unique FTSE reference 		*
    *		  int Number - Number of shares to sell			*
    * Returns:	  double - price obtained for Number shares.		*
    ********************************************************************/

    public synchronized ArrayList<Double> SellShares(String ShareCode, int Number)
	throws RemoteException{
    	
    	if (FTSE.containsKey(ShareCode)){
    		ShareElement share=FTSE.get(ShareCode);
    		share.NumberofShares.set(share.NumberofShares.addAndGet(Number));
    		ArrayList<Double> retList=new ArrayList<Double>(); 
    		retList.add(share.SharePrice*Number);
    		retList.add((double) share.NumberofShares.intValue());
    		return retList;    		
    	}else
    		throw new RemoteException("Share does not exist");

    }

	@Override
	public AtomicInteger getNumberOfShares(String ShareCode)
			throws RemoteException {
		// TODO Auto-generated method stub
		if (FTSE.containsKey(ShareCode)){
    		ShareElement share=FTSE.get(ShareCode);
    		return share.NumberofShares;    		
    	}else
    		throw new RemoteException("Share does not exist");

	}

	}	




