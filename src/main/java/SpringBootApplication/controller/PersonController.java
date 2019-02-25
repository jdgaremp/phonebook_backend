package SpringBootApplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SpringBootApplication.model.Person;
import SpringBootApplication.service.PersonService;

@CrossOrigin(origins = "http://localhost:4400")
@RestController
@RequestMapping("/persons")
public class PersonController {

	private static final String PHONE_NUMBER = "phonenumber";
	private static final String LAST_NAME = "lastname";
	private static final String FIRST_NAME = "firstname";
	@Autowired
	private PersonService personService;

	// at startup

	public void cleanRepository() {
		personService.deleteAll();
	}

	public void savePersons() {
		personService.save(new Person("1", "Alice", "Smith", "+00 00 000001"));
		personService.save(new Person("2", "Bob", "Smith", "+00 00 000002"));
		personService.save(new Person("3", "Gregory", "Smith", "+00 00 000003"));
		personService.save(new Person("4", "Stanislas", "Burger", "+00 00 000004"));
		personService.save(new Person("5", "Tom", "Snel", "+00 00 000005"));

	}

	@GetMapping()
	public List<Person> execute() {
		List<Person> persons = new ArrayList<>();
		persons = personService.findAll();
		return persons;
	}

	@GetMapping("/{id}")
	public Person getPersonById(@PathVariable("id") String id) {
		Person personFound = personService.findByIdString(id);
		return personFound;
	}

	@CrossOrigin(origins = "http://localhost:4400")
	@GetMapping("/search")
	public List<Person> search(@RequestParam(value = FIRST_NAME, required = false) String firstName,
			@RequestParam(value = LAST_NAME, required = false) String lastName,
			@RequestParam(value = PHONE_NUMBER, required = false) String phoneNumber) {

		List<Person> peopleFoundByFirstName = personService.findByFirstName(firstName);
		List<Person> peopleFoundByLastName = personService.findByLastName(lastName);
		List<Person> peopleFoundByPhoneNumber = personService.findByPhoneNumber(phoneNumber);

		boolean isFirstName = !peopleFoundByFirstName.isEmpty();
		boolean isLastName = !peopleFoundByLastName.isEmpty();
		boolean isPhoneNumber = !peopleFoundByPhoneNumber.isEmpty();

		List<Person> peopleFound = new ArrayList<>();

		if (isFirstName) {
			peopleFound = peopleFoundByFirstName;
		} else if (isLastName) {
			peopleFound = peopleFoundByLastName;
		} else if (isPhoneNumber) {
			peopleFound = peopleFoundByPhoneNumber;
		}

		removeDuplicates(peopleFound);
		removeWrongPeople(firstName, peopleFound);

		return peopleFound;
	}

	private void removeWrongPeople(String firstName, List<Person> peopleFound) {
		String[] firstNameSplited = firstName.split(" ");
		List<Person> test = new ArrayList<>();
		test.addAll(peopleFound);
		for (String string : firstNameSplited) {
			for (Person person : test) {
				person.getFirstName();
				if (!person.getFirstName().toLowerCase().contains(string.toLowerCase())
						&& !person.getLastName().toLowerCase().contains(string.toLowerCase())
						&& !person.getPhoneNumber().toLowerCase().contains(string.toLowerCase())) {
					peopleFound.remove(person);
				}
			}
		}
	}

	private void removeDuplicates(List<Person> peopleFound) {
		if (peopleFound.size() > 1) {
			for (Person person : peopleFound) {
				List<Person> peopleFoundWithoutDuplicates = new ArrayList<>();
				peopleFoundWithoutDuplicates.addAll(peopleFound);
				peopleFoundWithoutDuplicates.remove(person);
				for (Person person2 : peopleFoundWithoutDuplicates) {
					if (person.getId().equals(person2.getId())) {
						peopleFound.remove(person2);
					}
				}
			}
		}
	}

	@PostMapping()
	public Person addPerson(@RequestBody Person person) {
		return personService.save(person);
	}

	@PutMapping("/{id}")
	public void editPerson(@PathVariable("id") String id, @RequestBody Person person) {
		Person personToEdit = personService.findById(id).get();
		if (personService.findById(id).isPresent()) {
			personToEdit.setFirstName(person.getFirstName());
			personToEdit.setLastName(person.getLastName());
			personToEdit.setPhoneNumber(person.getPhoneNumber());
			personService.save(personToEdit);
		}

	}

}

/*
 * @GetMapping("/search") public List<Person>
 * getPersonsByFirstNameLastNameOrTelephone(
 * 
 * @RequestParam(value = FIRST_NAME, required = false) String firstName,
 * 
 * @RequestParam(value = LAST_NAME, required = false) String lastName,
 * 
 * @RequestParam(value = PHONE_NUMBER, required = false) String phoneNumber) {
 * 
 * HashMap<String, String> personAttributeTypeToValue = new HashMap<>();
 * 
 * putPersonAttributeTypeToValue(firstName, personAttributeTypeToValue);
 * putPersonAttributeTypeToValue(lastName, personAttributeTypeToValue);
 * putPersonAttributeTypeToValue(phoneNumber,personAttributeTypeToValue);
 * 
 * List<Person> persons = new ArrayList<>(); List<Person> peopleByFirstName =
 * new ArrayList<>(); List<Person> peopleByLastName = new ArrayList<>();
 * List<Person> peopleByPhoneNumber = new ArrayList<>();
 * 
 * if (personAttributeTypeToValue.get(FIRST_NAME) != null) { peopleByFirstName =
 * personService.findByFirstName(firstName); } if
 * (personAttributeTypeToValue.get(LAST_NAME) != null) { peopleByLastName =
 * personService.findByLastName(lastName); } if
 * (personAttributeTypeToValue.get(PHONE_NUMBER) != null) { peopleByPhoneNumber
 * = personService.findByPhoneNumber(phoneNumber); }
 * 
 * if (!peopleByFirstName.isEmpty() && !peopleByLastName.isEmpty() &&
 * !peopleByPhoneNumber.isEmpty()) { List<Person> returnedPeopleFound =
 * returnPeopleFound(peopleByFirstName, peopleByLastName, peopleByPhoneNumber);
 * if (!returnedPeopleFound.isEmpty()) { persons = returnedPeopleFound; } else {
 * System.out.println("error 1"); } }
 * 
 * else if (!peopleByFirstName.isEmpty() && !peopleByLastName.isEmpty()) {
 * List<Person> returnedPeopleIfDuplicatedFirstName =
 * returnPeopleIfItIsDuplicated(peopleByFirstName, peopleByLastName); if
 * (!returnedPeopleIfDuplicatedFirstName.isEmpty()) { persons =
 * returnedPeopleIfDuplicatedFirstName; } else { if
 * (!peopleByFirstName.isEmpty() && !peopleByLastName.isEmpty()) { return new
 * ArrayList<>(); } else { System.out.println("error 2"); } } } else if
 * (!peopleByFirstName.isEmpty() && !peopleByPhoneNumber.isEmpty()) {
 * List<Person> returnedPeopleIfDuplicated =
 * returnPeopleIfItIsDuplicated(peopleByFirstName, peopleByPhoneNumber); if
 * (!returnedPeopleIfDuplicated.isEmpty()) { persons =
 * returnedPeopleIfDuplicated; } else { System.out.println("error 3"); }
 * 
 * } else if (!peopleByLastName.isEmpty() && !peopleByPhoneNumber.isEmpty()) {
 * List<Person> returnedPeopleIfDuplicated =
 * returnPeopleIfItIsDuplicated(peopleByLastName, peopleByPhoneNumber); if
 * (!returnedPeopleIfDuplicated.isEmpty()) { persons =
 * returnedPeopleIfDuplicated; } else { System.out.println("error 4"); } } else
 * { if (!peopleByFirstName.isEmpty()) { persons.addAll(peopleByFirstName); }
 * else if (!peopleByLastName.isEmpty()) { persons.addAll(peopleByLastName); }
 * else if (!peopleByPhoneNumber.isEmpty()) {
 * persons.addAll(peopleByPhoneNumber); }
 * 
 * }
 * 
 * return persons; }
 * 
 * private void putPersonAttributeTypeToValue(String firstName, HashMap<String,
 * String> personAttributeTypeToValue) { if (firstName != null &&
 * !firstName.isEmpty()) { personAttributeTypeToValue.put(FIRST_NAME,
 * firstName); } }
 * 
 * private List<Person> returnPeopleIfItIsDuplicated(List<Person>
 * peopleByFirstName, List<Person> peopleByLastName) { List<Person> people = new
 * ArrayList<>(); for (Person personByFirstName : peopleByFirstName) { for
 * (Person personByLastName : peopleByLastName) if
 * (personByLastName.getId().equals(personByFirstName.getId())) {
 * people.add(personByLastName); } } return people;
 * 
 * }
 * 
 * private List<Person> returnPeopleFound(List<Person> peopleByFirstName,
 * List<Person> peopleByLastName, List<Person> peopleByPhoneNumber) {
 * List<Person> people = new ArrayList<>(); for (Person personByFirstName :
 * peopleByFirstName) { for (Person personByLastName : peopleByLastName) for
 * (Person personByPhoneNumber : peopleByPhoneNumber) { if
 * (personByLastName.getId().equals(personByFirstName.getId())) { if
 * (personByLastName.getId().equals(personByPhoneNumber.getId())) {
 * people.add(personByLastName); } } } } return people; }
 * 
 */
