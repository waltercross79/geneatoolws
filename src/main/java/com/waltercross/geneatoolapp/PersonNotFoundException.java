package com.waltercross.geneatoolapp;

public class PersonNotFoundException extends RuntimeException {
	PersonNotFoundException(String id) {
		super("Could not find person " + id);
	}
}
