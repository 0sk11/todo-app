package com.spring.demo.exception;

public class ModelCollectionExpection extends java.lang.Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ModelCollectionExpection(String message) {
		super(message);
	}
	public static String NotFoundExeption(String id) {
		return "Todo with " + id + "is not found ";
	}
	
	public static String TodoAlreadyExists(String id) {
		return "Todo already exists ";
	}
}
