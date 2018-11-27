package com.waltercross.geneatoolapp;

import com.waltercross.geneatoolcore.PersonInRecord;
import com.waltercross.geneatoolcore.PersonRole;

public class PersonInRecordDTO {
	public PersonDTO person;
	public int personType;
	
	public PersonInRecordDTO(PersonDTO person, int personType) {
		this.person = person;
		this.personType = personType;
	}
	
	public static PersonInRecord convert(PersonInRecordDTO dto) {
		return new PersonInRecord(PersonDTO.convert(dto.person), PersonRole.values()[dto.personType], null);		
	}
	
	public static PersonInRecordDTO convert(PersonInRecord personInRecord) {
		return new PersonInRecordDTO(PersonDTO.convert(personInRecord.person), personInRecord.personRole.value());
	}
}
