package com.waltercross.geneatoolapp;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.waltercross.geneatoolcore.Person;
import com.waltercross.geneatoolcore.PersonRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class PersonController {

	private final PersonRepository repository;

	public PersonController(PersonRepository repository) {
		super();
		this.repository = repository;
	}
	
	// Aggregate root.
	
	@GetMapping("/persons")
	List<Person> all() {
		return repository.findAll();
	}
	
	@PostMapping("/persons")
	Person newPerson(@RequestBody Person newPerson) {
		return repository.save(newPerson);
	}
	
	// Single item.
	@GetMapping("/persons/{id}")
	Person one(@PathVariable String id) {
		return repository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(id));
	}
	
	@PutMapping("/persons/{id}")
	Person replacePerson(@RequestBody Person newPerson, @PathVariable String id) {
		return repository.findById(id)
				.map(person -> {
					person.firstName = newPerson.firstName;
					person.lastName = newPerson.lastName;
					person.middleName = newPerson.middleName;
					person.dateOfBirth = newPerson.dateOfBirth;
					person.dateOfDeath = newPerson.dateOfDeath;
					person.gender = newPerson.gender;
					return repository.save(person);
				})
				.orElseGet(() -> {
					newPerson.id = id;
					return repository.save(newPerson);
				});
	}
	
	@DeleteMapping("persons/{id}")
	void deletePerson(@PathVariable String id) {
		repository.deleteById(id);
	}
}
