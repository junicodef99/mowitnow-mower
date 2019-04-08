package com.mowitnow.mower.rules.impl;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.mowitnow.mower.rules.LawnMowerInstructionsValidator;
import com.mowitnow.mower.rules.MowitnowMowerInputFileLineMatcher;

/**
 * @author aZeufack
 */
@Service
public class MowitnowMowerInstructionsValidator implements LawnMowerInstructionsValidator {
	MowitnowMowerInputFileLineMatcher inputFileLineMatcher = new MowitnowMowerInputFileLineMatcher();
	
	/**
	 * @param instructions : A list of instructions
	 * @return true or false
	 */
	@Override
	public boolean areInstructionsValid(List<String> instructions) {
		return (instructions.size() % 2 != 1) ? false :
			verifySyntax(instructions);
	}

	/**
	 * @param instructions : A list of instructions
	 * @return true or false
	 */
	boolean verifySyntax(List<String> instructions) {
		if (!inputFileLineMatcher.doesMatchLawnCoordinates(instructions.get(0))) {
			return false;
		}
		String[] lawnCoordinates = instructions.get(0).split("\\s");
		return !IntStream.iterate(1, i -> i + 2).limit(instructions.size()/2).anyMatch(idx -> {
				String[] mowerPosition = instructions.get(idx).split("\\s");
					return !inputFileLineMatcher.doesMatchMowerPosition(instructions.get(idx))
						|| !inputFileLineMatcher.doesMatchMowerItinerary(instructions.get(idx+1))
							|| !(Integer.parseInt(mowerPosition[0]) <= Integer.parseInt(lawnCoordinates[0]))
							|| !(Integer.parseInt(mowerPosition[1]) <= Integer.parseInt(lawnCoordinates[1]));
		});
	}
}
