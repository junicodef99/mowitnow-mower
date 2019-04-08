package com.mowitnow.mower.transverse;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mowitnow.mower.exceptions.MowitnowMowerException;

/**
 * @author aZeufack
 */
public class ExceptionsManager {
	private static Logger logger = LoggerFactory.getLogger(ExceptionsManager.class);
	
	/**
	 * @param exceptions : A list of MowitnowMowerException exceptions
	 */
	public static void logExceptions(List<MowitnowMowerException> exceptions) {
		exceptions.forEach(excp -> {
			if (excp.getException() != null) {
				logger.error(excp.getMessage(), excp.getException());
			}
		});
	}
}
