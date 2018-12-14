package SpringBootApplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import SpringBootApplication.Repository.PersonRepository;
import SpringBootApplication.model.Person;

@Service
public class PersonService implements PersonRepository {

	@Autowired
	PersonRepository personRepository;

	@Override
	public <S extends Person> List<S> saveAll(Iterable<S> entities) {
		return personRepository.saveAll(entities);
	}

	@Override
	public List<Person> findAll() {
		return personRepository.findAll();
	}

	@Override
	public List<Person> findAll(Sort sort) {
		return personRepository.findAll(sort);
	}

	@Override
	public <S extends Person> S insert(S entity) {
		return personRepository.insert(entity);
	}

	@Override
	public <S extends Person> List<S> insert(Iterable<S> entities) {
		return personRepository.insert(entities);
	}

	@Override
	public <S extends Person> List<S> findAll(Example<S> example) {
		return personRepository.findAll(example);
	}

	@Override
	public <S extends Person> List<S> findAll(Example<S> example, Sort sort) {
		return personRepository.findAll(example, sort);
	}

	@Override
	public Page<Person> findAll(Pageable pageable) {
		return personRepository.findAll(pageable);
	}

	@Override
	public <S extends Person> S save(S entity) {
		return personRepository.save(entity);
	}

	@Override
	public Optional<Person> findById(String id) {
		return personRepository.findById(id);
	}

	@Override
	public boolean existsById(String id) {
		return personRepository.existsById(id);
	}

	@Override
	public Iterable<Person> findAllById(Iterable<String> ids) {
		return personRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return personRepository.count();
	}

	@Override
	public void deleteById(String id) {
		personRepository.deleteById(id);
	}

	@Override
	public void delete(Person entity) {
		personRepository.delete(entity);

	}

	@Override
	public void deleteAll(Iterable<? extends Person> entities) {
		personRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		personRepository.deleteAll();
	}

	@Override
	public <S extends Person> Optional<S> findOne(Example<S> example) {
		return personRepository.findOne(example);
	}

	@Override
	public <S extends Person> Page<S> findAll(Example<S> example, Pageable pageable) {
		return personRepository.findAll(example, pageable);
	}

	@Override
	public <S extends Person> long count(Example<S> example) {
		return personRepository.count(example);
	}

	@Override
	public <S extends Person> boolean exists(Example<S> example) {
		return personRepository.exists(example);
	}

	private List<Person> findByLastName(String lastName) {
		List<Person> persons = new ArrayList<>();
		for (Person person : findAll()) {
			if (!lastName.isEmpty() && person.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
				persons.add(person);
			}
		}
		return persons;
	}

	private List<Person> findByFirstName(String firstName) {
		List<Person> persons = new ArrayList<>();
		for (Person person : findAll()) {
			if (!firstName.isEmpty() && person.getFirstName().toLowerCase().contains(firstName.toLowerCase())) {
				persons.add(person);
			}
		}
		return persons;
	}

	private List<Person> findByPhoneNumber(String phoneNumber) {
		List<Person> persons = new ArrayList<>();
		for (Person person : findAll()) {
			if (!phoneNumber.isEmpty() && person.getPhoneNumber().toLowerCase().contains(phoneNumber.toLowerCase())) {
				persons.add(person);
			}
		}
		return persons;
	}

	@Override
	public List<Person> search(List<HashMap<String, String>> personAttributeTypesToValues) {

		List<Person> persons = new ArrayList<>();
		List<Person> personByFirstName = new ArrayList<>();
		List<Person> personByLastName = new ArrayList<>();
		List<Person> personByPhoneNumber = new ArrayList<>();
		for (HashMap<String, String> personAttributeTypeToValue : personAttributeTypesToValues) {
			personByFirstName = findByFirstName(personAttributeTypeToValue.get("firstName"));
			personByLastName = findByLastName(personAttributeTypeToValue.get("lastName"));
			personByPhoneNumber = findByPhoneNumber(personAttributeTypeToValue.get("phoneNumber"));
		}

		// if we found the first name searched, check that it is not also a last name
		searchHasName(persons, personByFirstName, personByLastName);

		// if we found the last name searched, check that it is not also a first name
		searchHasName(persons, personByLastName, personByFirstName);

		if (!personByPhoneNumber.isEmpty()) {
			for (Person person : personByPhoneNumber) {
				if (!persons.contains(person)) {
					persons.add(person);
				}
			}
		}
		return persons;
	}

	private void searchHasName(List<Person> persons, List<Person> personByFirstName, List<Person> personByLastName) {
		if (!personByFirstName.isEmpty()) {
			persons.addAll(personByFirstName);
			if (!personByLastName.isEmpty()) {
				for (Person person : personByLastName) {
					if (!personByFirstName.contains(person)) {
						persons.add(person);
					}
				}
			}
		}
	}

}
