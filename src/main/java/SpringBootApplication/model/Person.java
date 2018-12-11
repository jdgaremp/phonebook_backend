package SpringBootApplication.model;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

@Entity
public class Person {
	@Id
	private String id;

	private String firstName;
	private String lastName;
	private String phoneNumber;

	public Person() {

	}

	public Person(String firstName, String lastName, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}
	
	public Person(String id, String firstName, String lastName, String phoneNumber) {
		this.id=id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public String getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@Override
	public String toString() {
		return String.format("Person[id=%s, firstName='%s', lastName='%s', phone='%s']", id, firstName, lastName,
				phoneNumber);
	}

}
