package com.waltercross.geneatoolapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.waltercross.geneatoolcore.PersonInRecord;
import com.waltercross.geneatoolcore.RecordType;
import com.waltercross.geneatoolcore.RegistryRecord;

public class RecordDTO {
	public String id;
	public Date recordDate;
	public String folio;
	public String street;
	public String houseNumber;
	public String city;
	public String country;
	public String registryBook;
	public int recordType;
    public List<PersonInRecordDTO> peopleInRecord;
    
    public static RegistryRecord convert(RecordDTO recordDto) {
    	RegistryRecord result = new RegistryRecord(recordDto.recordDate, 
    			recordDto.folio, recordDto.houseNumber, recordDto.street, recordDto.city, 
    			recordDto.country, RecordType.values()[recordDto.recordType], recordDto.registryBook, 
    			new ArrayList<PersonInRecord>(), null, recordDto.id);
    	    	
    	recordDto.peopleInRecord.forEach(pir -> result.people.add(PersonInRecordDTO.convert(pir)));    	
    	
    	return result;
    }
    
    public static RecordDTO convert(RegistryRecord record) {
    	RecordDTO result = new RecordDTO();
    	
    	result.folio = record.folio;
    	if(record.location != null) {
	    	result.city = record.location.city;
	    	result.country = record.location.country;
	    	result.street = record.location.streetName;
	    	result.houseNumber = record.location.houseNumber;
    	}
    	result.recordDate = record.recordDate;
    	result.recordType = record.recordType.value();
    	result.registryBook = record.registryBook;
    	List<PersonInRecordDTO> pirs = new ArrayList<PersonInRecordDTO>();
    	record.people.forEach(pir -> pirs.add(PersonInRecordDTO.convert(pir)));
    	result.peopleInRecord = pirs;
    	result.id = record.id;
    	
    	return result;
    }
}
