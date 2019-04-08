package com.mowitnow.mower.rules;
import java.util.Arrays;
import java.util.List;

import com.mowitnow.mower.enums.ActionCommandEnum;
import com.mowitnow.mower.enums.OrientationEnum;

/**
 * @author aZeufack
 */
public abstract class LawnMowerInputFileLineMatcher {
	
	private String lawnCoordinatesPattern;
	private String mowerPositionPattern;
	private String mowerItineraryPattern;
	
	/**
	 * Constructor
	 */
	public LawnMowerInputFileLineMatcher() {
		buildLawnCoordinatesPattern();
		buildMowerPositionPattern();
		buildMowerItineraryPattern();
	}
	
	/**
	 * Constructor with parameters
	 * @param lawnCoordinatesPattern : The lawn coordinates pattern
	 * @param mowerPositionPattern : The mower position pattern
	 * @param mowerItineraryPattern : the mower itinerary pattern
	 */
	public LawnMowerInputFileLineMatcher(String lawnCoordinatesPattern, String mowerPositionPattern,
			String mowerItineraryPattern) {
		this.lawnCoordinatesPattern = lawnCoordinatesPattern;
		this.mowerPositionPattern = mowerPositionPattern;
		this.mowerItineraryPattern = mowerItineraryPattern;
	}
	
	/**
	 * Sets the lawnCoordinatesPattern
	 */
	private void buildLawnCoordinatesPattern() {
		lawnCoordinatesPattern = "^\\d+\\s\\d+$";
	}

	/**
	 * Builds the mowerPositionPattern pattern
	 */
	private void buildMowerPositionPattern() {
		String patternCompleter = this.buildPatternCompleter(Arrays.asList(OrientationEnum.values()));
		mowerPositionPattern = "^\\d+\\s\\d+\\s".concat(patternCompleter).concat("$");
	}
	
	/**
	 * Builds the mowerItineraryPattern pattern
	 */
	private void buildMowerItineraryPattern() {
		String patternCompleter = this.buildPatternCompleter(Arrays.asList(ActionCommandEnum.values()));
		mowerItineraryPattern = "^".concat(patternCompleter).concat("+$");
	}
	
	/**
	 * Build a pattern completer corresponding to the list of enumeration given in parameter
	 * @param <T> : A generic type of enumeration
	 * @param enums : A list of enumerations
	 * @return A pattern completer
	 */
	private <T> String buildPatternCompleter(List<T> enums) {
		StringBuilder sb = new StringBuilder("(");
		enums.forEach(enum_ -> {
			sb.append(enum_.toString().concat("|"));
		});
		sb.setLength(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * Checks if the coordinates given in parameter matches the lawn coordinates pattern
	 * @param lawnCoordinates : The coordinates instructions that needs to be verified
	 * @return A boolean
	 */
	public boolean doesMatchLawnCoordinates(String lawnCoordinates) {
		return lawnCoordinates.matches(lawnCoordinatesPattern);
	}
	
	/**
	 * Checks if the position given in parameter matches the mower position pattern
	 * @param mowerPosition : The position instructions that needs to be verified
	 * @return A boolean
	 */
	public boolean doesMatchMowerPosition(String mowerPosition) {
		return mowerPosition.matches(mowerPositionPattern);
	}
	
	/**
	 * Checks if the itinerary given in parameter matches the mower itinerary pattern
	 * @param mowerItinerary : The mower itinerary instructions which need to be verified
	 * @return A boolean
	 */
	public boolean doesMatchMowerItinerary(String mowerItinerary) {
		return mowerItinerary.matches(mowerItineraryPattern);
	}
}
