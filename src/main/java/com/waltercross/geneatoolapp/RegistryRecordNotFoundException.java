package com.waltercross.geneatoolapp;

public class RegistryRecordNotFoundException extends RuntimeException {
	public RegistryRecordNotFoundException(String id) {
		super("Could not find record " + id);
	}
}
