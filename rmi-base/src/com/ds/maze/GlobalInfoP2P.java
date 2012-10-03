package com.ds.maze;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is the bean class containing all elements representative of the game
 * state which would be common to all peers
 * 
 */
public class GlobalInfoP2P implements Serializable {

   /**
	 * 
	 */
   private static final long serialVersionUID = 1999655940450123470L;
   private int[][] atomicToIntGrid;
   private int sumOftreasures;
   private int numberOfplayers;
   private ConcurrentLinkedQueue<String> peerIPList;
   private int gridSize;

   /**
    * @return the atomicToIntGrid
    */
   public int[][] getAtomicToIntGrid() {
      return atomicToIntGrid;
   }

   /**
    * @param atomicToIntGrid the atomicToIntGrid to set
    */
   public void setAtomicToIntGrid(int[][] atomicToIntGrid) {
      this.atomicToIntGrid = atomicToIntGrid;
   }

   /**
    * @return the sumOftreasures
    */
   public int getSumOftreasures() {
      return sumOftreasures;
   }

   /**
    * @param sumOftreasures the sumOftreasures to set
    */
   public void setSumOftreasures(int sumOftreasures) {
      this.sumOftreasures = sumOftreasures;
   }

   /**
    * @return the numberOfplayers
    */
   public int getNumberOfplayers() {
      return numberOfplayers;
   }

   /**
    * @param numberOfplayers the numberOfplayers to set
    */
   public void setNumberOfplayers(int numberOfplayers) {
      this.numberOfplayers = numberOfplayers;
   }

   /**
    * @return the peerIPList
    */
   public ConcurrentLinkedQueue<String> getPeerIPList() {
      return peerIPList;
   }

   /**
    * @param peerIPList the peerIPList to set
    */
   public void setPeerIPList(ConcurrentLinkedQueue<String> peerIPList) {
      this.peerIPList = peerIPList;
   }

   /**
    * @return the gridSize
    */
   public int getGridSize() {
      return gridSize;
   }

   /**
    * @param size the GridSize to set
    */
   public void setGridSize(int size) {
      this.gridSize = size;
   }

}
