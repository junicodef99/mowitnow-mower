/**
 * 
 */
package com.mowitnow.mower.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mowitnow.mower.exceptions.MowitnowMowerException;
import com.mowitnow.mower.services.LawnMowerReader;

/**
 * @author aZeufack
 */
@Service
public class MowitnowMowerReaderService implements LawnMowerReader {

	/**
	 * Reads a file of instructions and store the instructions inside a string list.
	 * @param absoluteFilePath : The path of the instructions file to read.
	 * @return A list of read instructions. Each line of the file corresponds to an element of the list.
	 * @throws MowitnowMowerException : The exception that can be generated
	 */
	@Override
	public List<String> readFile(String absoluteFilePath) {
		try {
			return Files.lines(Paths.get(absoluteFilePath))
					.map(l -> l.trim())
					.filter(l -> !l.isEmpty())
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new MowitnowMowerException("An error occured while attempting to read the file ".concat(absoluteFilePath), e);
		}
	}
}
