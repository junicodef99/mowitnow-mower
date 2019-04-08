/**
 * 
 */
package com.mowitnow.mower.metier;

import java.util.List;

import com.mowitnow.mower.enums.ActionCommandEnum;

/**
 * @author aZeufack
 */
public class Mower {
	private int identifier;
	private Position position;
	private List<ActionCommandEnum> itinerary;
	
	/**
	 * @param identifier : The unique identifier of the mower
	 * @param position : The position of the mower
	 * @param itinerary : The itinerary of the mower
	 */
	public Mower(int identifier, Position position, List<ActionCommandEnum> itinerary) {
		this.identifier = identifier;
		this.position = position;
		this.itinerary = itinerary;
	}

	/**
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @return the itinerary
	 */
	public List<ActionCommandEnum> getItinerary() {
		return itinerary;
	}
}
