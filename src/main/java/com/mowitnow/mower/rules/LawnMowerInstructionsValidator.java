/**
 * 
 */
package com.mowitnow.mower.rules;

import java.util.List;

/**
 * @author aZeufack
 */
public interface LawnMowerInstructionsValidator {
	/**
	 * @param instructions : The instructions to verify
	 * @return A boolean indicating the verification result
	 */
	public boolean areInstructionsValid(List<String> instructions);
}
