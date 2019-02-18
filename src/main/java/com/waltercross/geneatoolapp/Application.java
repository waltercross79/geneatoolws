package com.waltercross.geneatoolapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.waltercross.geneatoolcore.RegistryRecord;
import com.waltercross.geneatoolcore.RegistryRecordRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses=RegistryRecord.class)
public class Application {
//public class Application implements CommandLineRunner {
	
	@Autowired
	private RegistryRecordRepository registryRecordRepo;	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

/*	@Override
	public void run(String... args) throws Exception {

		registryRecordRepo.deleteAll();
		personRepo.deleteAll();
		
		// Save couple people.
		Person jZmekSr = Person.create("Jan", "Zmek", "", null, null, 'M', null, null);
		Person anna = Person.create("Anna", "", "", null, null, 'F', null, null);		
		
		jZmekSr = personRepo.save(jZmekSr);		
		anna = personRepo.save(anna);
						
		Person jZmekJr = Person.create("Jan", "Zmek", "", "4/2/1782", null, 'M', jZmekSr, anna);
		Person magdalena = Person.create("Magdalena", "", "", "1793", null, 'F', null, null);
		
		jZmekJr = personRepo.save(jZmekJr);
		magdalena = personRepo.save(magdalena);

		RegistryRecord birth = RegistryRecord.createBirthRecord(
			new Calendar.Builder().setDate(1782, 4, 2).build().getTime(), null, "16", null, "Simanov", null, null, 
			jZmekJr, jZmekSr, anna, null, null, null);
		
		List<PersonSimpleRecord> groomsParents = new ArrayList<PersonSimpleRecord>();
		groomsParents.add(new PersonSimpleRecord("Jan Zmek", "Simanov"));
		groomsParents.add(new PersonSimpleRecord("Anna", "Simanov"));
		
		RegistryRecord marriage = RegistryRecord.createMarriageRecord(new Calendar.Builder().setDate(1817, 1, 1).build().getTime(), null, "16", null, "Simanov", null, null, 
			jZmekJr, magdalena, groomsParents, null, null);
		
		registryRecordRepo.save(birth);
		registryRecordRepo.save(marriage);
	}*/

}
