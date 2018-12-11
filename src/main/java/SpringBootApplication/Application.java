package SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import SpringBootApplication.controller.PersonController;


@SpringBootApplication
@ComponentScan
public class Application implements CommandLineRunner {
	
	@Autowired
	private PersonController personController;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		personController.cleanRepository();
		personController.savePersons();
		/*
		// fetch all people
		System.out.println("People found with findAll(): ");
		System.out.println("-------------------------------");
		for (Person person : personRepository.findAll()) {
			System.out.println(person);
		}
		
		System.out.println();
		
		// fetch one person
		System.out.println("Person found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(personRepository.findByFirstName("Alice"));
		
		// fetch people with same last name
		System.out.println("People found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		System.out.println(personRepository.findByLastName("Smith")); */
	}

}