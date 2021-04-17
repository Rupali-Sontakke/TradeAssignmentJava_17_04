package com.trade.transmission.exception;

public class LowerVersionException extends RuntimeException{
	
	public LowerVersionException(String message) {
		super(message);
	}
}
