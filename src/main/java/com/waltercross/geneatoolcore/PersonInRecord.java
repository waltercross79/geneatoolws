package com.waltercross.geneatoolcore;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class PersonInRecord {
	@DBRef
	public Person person;
	public PersonRole personRole;
	public List<PersonSimpleRecord> parents;
	
	public PersonInRecord(Person person, PersonRole personRole, List<PersonSimpleRecord> parents) {
		super();
		this.person = person;
		this.personRole = personRole;
		this.parents = parents;
	}	
}
