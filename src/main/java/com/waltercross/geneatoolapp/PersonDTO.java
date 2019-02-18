package com.waltercross.geneatoolapp;

import com.waltercross.geneatoolcore.Person;

public class PersonDTO {
	
	public Integer id;
	public String firstName;
	public String lastName;

	public static Person convert(PersonDTO dto) {
		return Person.create(dto.firstName, dto.lastName, dto.id);
	}
	
	public static PersonDTO convert(Person person) {
		PersonDTO result = new PersonDTO();
		
		result.firstName = person.firstName;
		result.id = person.id;
		result.lastName = person.lastName;

		return result;
	}
}
