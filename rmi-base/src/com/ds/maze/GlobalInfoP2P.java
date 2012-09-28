package com.ds.maze;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GlobalInfoP2P implements Serializable{
    
    private int [][]atomicToIntGrid;
    private int sumOftreasures;
    private int numberOfplayers;
    private ConcurrentLinkedQueue<String> peerIPList;
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
    

}
