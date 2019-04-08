/**
 * 
 */
package com.mowitnow.mower.enums;

/**
 * @author aZeufack
 */
public enum OrientationEnum {
	N("North", '^'),
    S("South", 'v'), 
    E("East", '>'),
    W("West", '<');
	
	private String orientation;
	private char symbol;

	/**
	 * @param orientation
	 * @param symbol
	 */
	private OrientationEnum(String orientation, char symbol) {
		this.orientation = orientation;
		this.symbol = symbol;
	}

	/**
	 * @return the orientation
	 */
	public String getOrientation() {
		return orientation;
	}
	
	/**
	 * @return the orientationSymbol
	 */
	public char getSymbol() {
		return symbol;
	}
	
	/**
	 * @return : The orientationEnum at the left of the current one
	 */
	public OrientationEnum getLeft() {
		switch (this) {
		case N:
			return W;
		case W:
			return S;
		case S:
			return E;
		case E:
			return N;
		default:
			return null;
		}
	}
	
	/**
	 * @return The orientationEnum at the right of the current one
	 */
	public OrientationEnum getRight() {
		switch (this) {
		case N:
			return E;
		case E:
			return S;
		case S:
			return W;
		case W:
			return N;
		default:
			return null;
		}
	}
}
