/**
 * 
 */
package com.mowitnow.mower.transverse.utils;

import static com.google.common.collect.ImmutableList.of;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.mowitnow.mower.enums.ActionCommandEnum;
import com.mowitnow.mower.enums.OrientationEnum;
import com.mowitnow.mower.metier.Lawn;
import com.mowitnow.mower.metier.Mower;
import com.mowitnow.mower.metier.Position;

/**
 * @author aZeufack
 */
public class LawnMowersUtils {
	/**
	 * Instantiates a lawn using the given instructions
	 * @param instructions : A list of instructions
	 * @return an instantiated lawn
	 */
	public static Lawn instantiateLawn(List<String> instructions) {
		if (isEmpty(instructions)) {
			return null;
		}
		String[] coordinatesArray = instructions.get(0).split("\\s");
		return new Lawn(Integer.parseInt(coordinatesArray[0]), Integer.parseInt(coordinatesArray[1]));
	}
	
	/**
	 * Instantiates a list of mowers using the given instructions
	 * @param instructions : The list of instructions
	 * @return a list of instantiated mowers
	 */
	public static List<Mower> instantiateMowers(List<String> instructions) {
		if (isEmpty(instructions)) {
			return of();
		}
		List<Mower> mowers = new ArrayList<>();
		IntStream.iterate(1, i -> i + 2).limit(instructions.size()/2).forEach(idx -> {
			Position position = buildPosition(instructions.get(idx));
			List<ActionCommandEnum> itinerary = buildItinerary(instructions.get(idx+1));
			mowers.add(new Mower((idx/2)+1, position, itinerary));
		});
		return mowers;
	}
	
	/**
	 * Builds a string information using the mowers identifier and position.
	 * @param mowers : A list of mowers
	 * @return A formatted string
	 */
	public static String format(List<Mower> mowers) {
		if (isEmpty(mowers)) {
			return "";
		}
		return mowers.stream().map(mower ->  String.format("\nMower%d: %s", mower.getIdentifier(), mower.getPosition().toString()))
				.collect(Collectors.joining());
	}

	/**
	 * Builds a list of actionCommandEnum
	 * @param instructions : A string of itinerary instructions
	 * @return A list of actionCommandEnum
	 */
	private static List<ActionCommandEnum> buildItinerary(String itineraryInstructions) {
		List<ActionCommandEnum> itinerary = new ArrayList<>(); 
		Arrays.asList(itineraryInstructions.split("")).forEach(instruction -> {
			itinerary.add(ActionCommandEnum.valueOf(instruction));
		});
		return itinerary;
	}

	/**
	 * Builds a mower position
	 * @param instructions : A string of position instructions
	 * @return a Position Object
	 */
	private static Position buildPosition(String positionInstructions) {
		String[] positionArray = positionInstructions.split("\\s");
		Position position = new Position(Integer.parseInt(positionArray[0]),
				Integer.parseInt(positionArray[1]), OrientationEnum.valueOf(positionArray[2]));
		return position;
	}
}
