package com.ds.maze;

import java.io.Serializable;

/**
 * this is the bean class containing information specific to a peer in the game
 * 
 */
public class PlayerInfoP2P implements Serializable {

   /**
	 * 
	 */
   private static final long serialVersionUID = -6653181472207284450L;
   private int xCord;
   private int yCord;
   private int numberOftreasures;
   private String IpAddress;

   /**
    * @return the xCord
    */
   public int getxCord() {
      return xCord;
   }

   /**
    * @param xCord the xCord to set
    */
   public void setxCord(int xCord) {
      this.xCord = xCord;
   }

   /**
    * @return the yCord
    */
   public int getyCord() {
      return yCord;
   }

   /**
    * @param yCord the yCord to set
    */
   public void setyCord(int yCord) {
      this.yCord = yCord;
   }

   /**
    * @return the numberOftreasures
    */
   public int getNumberOftreasures() {
      return numberOftreasures;
   }

   /**
    * @param numberOftreasures the numberOftreasures to set
    */
   public void setNumberOftreasures(int numberOftreasures) {
      this.numberOftreasures = numberOftreasures;
   }

   /**
    * @return the ipAddress
    */
   public String getIpAddress() {
      return IpAddress;
   }

   /**
    * @param ipAddress the ipAddress to set
    */
   public void setIpAddress(String ipAddress) {
      IpAddress = ipAddress;
   }

   @Override
   public String toString() {

      return "Your game state is as follows \n" + "Cordinate: " + "[" + xCord
            + "," + yCord + "]\n" + "Number of treasures collected: "
            + numberOftreasures + "\n";

   }

}
