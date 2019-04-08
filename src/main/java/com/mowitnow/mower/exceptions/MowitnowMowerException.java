/**
 * 
 */
package com.mowitnow.mower.exceptions;

/**
 * @author aZeufack
 *
 */
public class MowitnowMowerException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5880354336068971789L;
	
	private String message;
	private Throwable exception;
	
	/*
	 * Constructor
	 */
	public MowitnowMowerException() {
		super();
	}
	
	/**
	 * @param message : The message of the exception
	 * @param exception : The thrown exception
	 */
	public MowitnowMowerException(String message, Throwable exception) {
		super();
		this.message = message;
		this.exception = exception;
	}

	/**
	 * @return the exception
	 */
	public Throwable getException() {
		return exception;
	}
	
	/**
	 *
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
