package com.waltercross.geneatoolcore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;

public class RegistryRecord {
	@Id
	public String id;
	public Date recordDate;
	public String folio;
	public Location location;    
	public String registryBook;
	public RecordType recordType;
    public List<PersonInRecord> people;
    public List<PersonSimpleRecord> others;

    /**
     * Default constructor for reflection and MongoDB repository.
     */
    public RegistryRecord() {
    	
    }
    
    /**
     * 
     * @param recordDate
     * @param folio
     * @param houseNumber
     * @param streetName
     * @param city
     * @param country
     * @param recordType
     * @param registryBook
     */
    public RegistryRecord(Date recordDate, String folio, String houseNumber,
                          String streetName, String city, String country,
                          RecordType recordType, String registryBook,
                          List<PersonInRecord> people, List<PersonSimpleRecord> others) {
    	
        this(recordDate,folio,houseNumber,streetName,city,
                country, recordType, registryBook, people, others, null);        
    }

    /**
     * 
     * @param recordDate
     * @param folio
     * @param houseNumber
     * @param streetName
     * @param city
     * @param country
     * @param recordType
     * @param registryBook
     * @param id
     */
    public RegistryRecord(Date recordDate, String folio, String houseNumber,
                          String streetName, String city, String country,
                          RecordType recordType, String registryBook, 
                          List<PersonInRecord> people, List<PersonSimpleRecord> others,
                          String id) {
    	
        this.recordDate = recordDate;
        this.folio = folio;
        this.location = new Location(country, streetName, houseNumber, city );                
        this.id = id;
        this.recordType = recordType;
        this.registryBook = registryBook;  
        this.people = people;
        this.others = others;
    }

    /**
     * 
     * @param recordDate
     * @param folio
     * @param houseNumber
     * @param streetName
     * @param city
     * @param country
     * @param recordType
     * @param registryBook
     * @param father
     * @param mother
     * @param paternalGrandparens
     * @param maternalGrandparens
     * @param otherPeopleInRecord
     * @return
     */
    public static RegistryRecord createBirthRecord(Date recordDate, String folio, String houseNumber,
                          String streetName, String city, String country,
                          String registryBook, 
                          Person newborn, Person father, Person mother, 
                          List<PersonSimpleRecord> paternalGrandparents, List<PersonSimpleRecord> maternalGrandparents,
                          List<PersonSimpleRecord> otherPeopleInRecord) {
    	
    	ArrayList<PersonInRecord> peopleInRecord = new ArrayList<PersonInRecord>();
    	
    	peopleInRecord.add(new PersonInRecord(newborn, PersonRole.Newborn, null));
    	
    	if(father != null) {
    		peopleInRecord.add(new PersonInRecord(father, PersonRole.Father, paternalGrandparents));
    	}
    	
    	if(mother != null) {
    		peopleInRecord.add(new PersonInRecord(mother, PersonRole.Mother, maternalGrandparents));
    	}
    	
        RegistryRecord result = new RegistryRecord(recordDate,folio,houseNumber,
                streetName, city, country, RecordType.Birth, registryBook, peopleInRecord, otherPeopleInRecord);

        return result;        
    }   
    
    /**
     * 
     * @param recordDate
     * @param folio
     * @param houseNumber
     * @param streetName
     * @param city
     * @param country
     * @param registryBook
     * @param groom
     * @param bride
     * @param groomsParents
     * @param bridesParents
     * @param otherPeopleInRecord
     * @return
     */
    public static RegistryRecord createMarriageRecord(Date recordDate, String folio, String houseNumber,
            String streetName, String city, String country,
            String registryBook, Person groom, Person bride, 
            List<PersonSimpleRecord> groomsParents, List<PersonSimpleRecord> bridesParents,
            List<PersonSimpleRecord> otherPeopleInRecord) {

		ArrayList<PersonInRecord> spouses = new ArrayList<PersonInRecord>();
		
		if(groom != null) {
			spouses.add(new PersonInRecord(groom, PersonRole.Groom, groomsParents));
		}
		
		if(bride != null) {
			spouses.add(new PersonInRecord(bride, PersonRole.Bride, bridesParents));
		}
		
		RegistryRecord result = new RegistryRecord(recordDate,folio,houseNumber,
		  streetName, city, country, RecordType.Marriage, registryBook, spouses, otherPeopleInRecord);
		
		return result;        
	}   
    
    /**
     * 
     * @param recordDate
     * @param folio
     * @param houseNumber
     * @param streetName
     * @param city
     * @param country
     * @param registryBook
     * @param deceased
     * @param parents
     * @param otherPeopleInRecord
     * @return
     */
    public static RegistryRecord createDeathRecord(Date recordDate, String folio, String houseNumber,
            String streetName, String city, String country,
            String registryBook, Person deceased, 
            List<PersonSimpleRecord> parents, List<PersonSimpleRecord> otherPeopleInRecord) {

		ArrayList<PersonInRecord> deceasedPeople = new ArrayList<PersonInRecord>();
		
		deceasedPeople.add(new PersonInRecord(deceased, PersonRole.Deceased, parents));
			
				
		RegistryRecord result = new RegistryRecord(recordDate,folio,houseNumber,
		  streetName, city, country, RecordType.Death, registryBook, deceasedPeople, otherPeopleInRecord);
		
		return result;        
	}   
}
