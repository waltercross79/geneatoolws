package com.waltercross.geneatoolapp;

import com.waltercross.geneatoolcore.Person;

public class PersonDTO {
	
	public String id;	
	public String firstName;
	public String lastName;
	public String middleName;
	public String dateOfBirth;
	public String dateOfDeath;	   
	public char gender;

	public static Person convert(PersonDTO dto) {
		return Person.create(dto.firstName, dto.lastName, dto.middleName, 
				dto.dateOfBirth, dto.dateOfDeath, dto.gender, null, null, dto.id);
	}
	
	public static PersonDTO convert(Person person) {
		PersonDTO result = new PersonDTO();
		
		result.dateOfBirth = person.dateOfBirth;
		result.dateOfDeath = person.dateOfDeath;
		result.firstName = person.firstName;
		result.gender = person.gender;
		result.id = person.id;
		result.lastName = person.lastName;
		result.middleName = person.middleName;		
		
		return result;
	}
}
