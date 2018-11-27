package com.waltercross.geneatoolcore;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Person {
	@Id
    public String id;	
	public String firstName;
	public String lastName;
	public String middleName;
	public String dateOfBirth;
	public String dateOfDeath;	   
	public char gender;
	@DBRef
	public Person father;
	@DBRef
	public Person mother;
	
    public Person() {
    }

    public Person(String firstName, String lastName, String middleName, 
    		String dateOfBirth, String dateOfDeath, char gender, 
    		Person father, Person mother, String id) {
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.id = id;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
    }

    public static Person create(String firstName, String lastName, String middleName, 
    		String dateOfBirth, String dateOfDeath, char gender,
    		Person father, Person mother) {               
        return Person.create(firstName, lastName, middleName, dateOfBirth, 
        		dateOfDeath, gender, father, mother, null);
    }     
    
    public static Person create(String firstName, String lastName, String middleName, 
    		String dateOfBirth, String dateOfDeath, char gender,
    		Person father, Person mother, String id) {               
        return new Person(firstName, lastName, middleName, dateOfBirth, 
        		dateOfDeath, gender, father, mother, id);
    }     
}
