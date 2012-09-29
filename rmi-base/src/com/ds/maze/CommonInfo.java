package com.ds.maze;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CommonInfo implements Serializable {

	
	private int [][]atomicToIntGrid;
	private int sumOftreasures;
	private int numberOfplayers;
	private int gridSize;
	
	
	
	public CommonInfo(){
		//Instantiate the grid array
		
		this.atomicToIntGrid= new int[10][];
	}
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
	
}
