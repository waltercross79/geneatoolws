package com.waltercross.geneatoolcore;

public class Person {
    public Integer id;	
	public String firstName;
	public String lastName;
	
    public Person() {
    }

    public Person(String firstName, String lastName, Integer id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    } 
    
    public static Person create(String firstName, String lastName, Integer id) {
        return new Person(firstName, lastName, id);
    }     
}
