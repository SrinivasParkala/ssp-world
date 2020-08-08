package com.ssp.maintainance.exception;

public class InvalidInputException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public InvalidInputException(String message){
		super(message);
	}
	
	public InvalidInputException(String message, Exception e){
		super(message,e);
		this.setStackTrace(e.getStackTrace());
	}
	
}
