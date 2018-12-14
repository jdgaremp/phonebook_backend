package SpringBootApplication.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import SpringBootApplication.model.Person;
import SpringBootApplication.service.PersonService;

@Controller
@RequestMapping("/persons")
public class PersonController {

	private static final String PHONE_NUMBER = "phoneNumber";
	private static final String LAST_NAME = "lastName";
	private static final String FIRST_NAME = "firstName";
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
	public ResponseEntity<Person> getPersonById(@PathVariable("id") String id) {
		Optional<Person> personFound = personService.findById(id);
		if (personFound.isPresent()) {
			return ResponseEntity.ok(personFound.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	private void check(String attributeName, String attributeValue, List<HashMap<String, String>> personAttributeTypesToValues ) {
		if(attributeValue != null) {
			HashMap<String, String> attributeNameToValue = new HashMap<String, String>();
			attributeNameToValue.put(attributeName, attributeValue);
			personAttributeTypesToValues.add(attributeNameToValue);
		}
	}

	@GetMapping("/search")
	public List<Person> getPersonsByIdOrFirstNameLastNameOrTelephone(
			@RequestParam(value = FIRST_NAME, required = false) String firstName,
			@RequestParam(value = LAST_NAME, required = false) String lastName,
			@RequestParam(value = PHONE_NUMBER, required = false) String phoneNumber) {

		List<HashMap<String, String>> personAttributeTypesToValues = new ArrayList<HashMap<String, String>>();
		
		check(FIRST_NAME,firstName,personAttributeTypesToValues);
		check(LAST_NAME,lastName, personAttributeTypesToValues);
		check(PHONE_NUMBER,phoneNumber, personAttributeTypesToValues);
		
		return personService.search(personAttributeTypesToValues);
	}

}
