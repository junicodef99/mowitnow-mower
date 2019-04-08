/**
 * 
 */
package com.mowitnow.mower.services;

import java.util.List;

/**
 * @author aZeufack
 */
public interface LawnMowerReader {
	/**
	 * Reads a file of instructions and store the instructions inside a string list.
	 * @param absoluteFilePath : The absolute path of the file to read
	 * @return A list of read instructions
	 */
	public List<String> readFile(String absoluteFilePath);
}
