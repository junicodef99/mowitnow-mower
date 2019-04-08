/**
 * 
 */
package com.mowitnow.mower.metier;

/**
 * @author aZeufack
 */
public class Lawn {
	private int xSize;
	private int ySize;
	
	/**
	 * @param xSize : The X size of the lawn
	 * @param ySize : The Y size of the lawn
	 */
	public Lawn(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
	}

	/**
	 * @return the xSize
	 */
	public int getXSize() {
		return xSize;
	}

	/**
	 * @return the ySize
	 */
	public int getYSize() {
		return ySize;
	}
}
