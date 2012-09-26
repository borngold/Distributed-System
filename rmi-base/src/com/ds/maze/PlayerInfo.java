package com.ds.maze;

import java.io.Serializable;

public class PlayerInfo implements Serializable{
	
	private int xCord;
	private int yCord;
	private int uniqueId;
	private int numberOftreasures;
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
	 * @return the uniqueId
	 */
	public int getUniqueId() {
		return uniqueId;
	}
	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
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
	
	@Override 
	public String toString(){
		
		return "Your game state is as follows \n"+"Cordinate: "+"["+xCord+","+yCord+"]\n"+"Number of treasures collected: "+numberOftreasures+"\n";
		
	}

}
