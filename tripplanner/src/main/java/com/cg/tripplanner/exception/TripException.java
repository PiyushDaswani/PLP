/**
 * 
 */
package com.cg.tripplanner.exception;

/**
 * @author Admin
 *
 */
public class TripException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TripException() {
		super();
	}

	public TripException(String exceptionMessage) {
		super(exceptionMessage);
	}
		
}
