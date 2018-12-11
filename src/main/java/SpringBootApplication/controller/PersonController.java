package SpringBootApplication.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import SpringBootApplication.model.Person;
import SpringBootApplication.service.PersonService;

@CrossOrigin(origins = "http://localhost:4300", maxAge = 3600)
@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService personService;

	// at startup

	public void cleanRepository() {
		personService.deleteAll();
	}

	public void savePersons() {
		personService.save(new Person("Alice", "Smith", "+00 00 000001"));
		personService.save(new Person("Bob", "Smith", "+00 00 000002"));
		personService.save(new Person("Gregory", "Smith", "+00 00 000003"));
		personService.save(new Person("Stanislas", "Burger", "+00 00 000004"));
		personService.save(new Person("Tom", "Snel", "+00 00 000005"));

	}

	@GetMapping()
	public List<Person> execute() {
		List<Person> persons = new ArrayList<>();
		persons = personService.findAll();
		return persons;
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.OK)
	public void createPerson(@RequestBody Person person) {

	}

	@GetMapping("/{id}")
	public Optional<Person> getPersonById(@PathVariable("id") String id) {
		return personService.findById(id);
	}

	@GetMapping("/firstname:{firstName}")
	public List<Person> getPersonsByFirstName(@PathVariable("firstName") String firstName) {
		System.out.println("personByFirstName"+firstName);
		return personService.findByFirstName(firstName);
	}

	@GetMapping("/lastname:{lastName}")
	public List<Person> getPersonsByLastName(@PathVariable("lastName") String lastName) {
		return personService.findByLastName(lastName);
	}

	@GetMapping("/phonenumber:{phoneNumber}")
	public List<Person> getPersonsByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
		return personService.findByPhoneNumber(phoneNumber);
	}
	
	@RequestMapping(value="/search?firstname={firstName}", method=RequestMethod.GET)
	public List<Person> gettest(@PathVariable("firstName") String firstName){
		return personService.findByFirstName(firstName);
	}

	@GetMapping("/search?firstname={firstName}&lastname={lastName}&telephone={phoneNumber}")
	public List<Person> getPersonsByFirstNameLastNameOrTelephone(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @PathVariable("phoneNumber") String phoneNumber){
		List<Person> persons= new ArrayList<>();
		List<Person> personByFirstName=personService.findByFirstName(firstName);
		List<Person> personByLastName=personService.findByLastName(lastName);
		List<Person> personByPhoneNumber=personService.findByPhoneNumber(phoneNumber);
		
		System.out.println("personByFirstName"+personByFirstName);
		System.out.println("personByLastName"+personByLastName);
		System.out.println("personByPhoneNumber"+personByPhoneNumber);
		
		if(!personByFirstName.isEmpty()) {
			persons.addAll(personByFirstName);
			if(!personByLastName.isEmpty()) {
				for(Person person:personByLastName) {
					if(!personByFirstName.contains(person)){
						persons.add(person);
					}
				}
			}
		}if(!personByPhoneNumber.isEmpty()) {
			persons.addAll(personByPhoneNumber);
		}
		return persons;
	}

}
