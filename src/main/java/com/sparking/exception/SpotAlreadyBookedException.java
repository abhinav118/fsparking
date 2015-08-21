package com.sparking.exception;

public class SpotAlreadyBookedException extends Exception{
	
	public SpotAlreadyBookedException(){}
	
	public SpotAlreadyBookedException(String message){
		super(message);
	}

}
