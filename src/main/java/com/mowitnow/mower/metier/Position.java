package com.mowitnow.mower.metier;

import com.mowitnow.mower.enums.OrientationEnum;

public class Position {
	private int xCoordinate;
	private int yCoordinate;
	private OrientationEnum orientation;
	
	/**
	 * @param xCoordinate : The X coordinate
	 * @param yCoordinate : The Y coordinate
	 * @param orientation : The orientation 
	 */
	public Position(int xCoordinate, int yCoordinate, OrientationEnum orientation) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.orientation = orientation;
	}

	/**
	 * @return the xCoordinate
	 */
	public int getXCoordinate() {
		return xCoordinate;
	}

	/**
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setXCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * @return the yCoordinate
	 */
	public int getYCoordinate() {
		return yCoordinate;
	}

	/**
	 * @param yCoordinate the yCoordinate to set
	 */
	public void setYCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/**
	 * @return the orientation
	 */
	public OrientationEnum getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(OrientationEnum orientation) {
		this.orientation = orientation;
	}
	
	/**
	 *
	 */
	@Override
	public String toString() {
		return  this.xCoordinate + " " +
				this.yCoordinate + " " +
				this.orientation;
	}
}
