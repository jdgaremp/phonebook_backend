package SpringBootApplication.model;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;

@Entity
public class Person {
	@Id
	private String id;

	private String firstName;
	private String lastName;

	@Pattern(regexp = "\\+[0-9]+\\s[0-9]+\\s[0-9]{6,}")
	private String phoneNumber;

	public Person() {

	}

	public Person(String id) {
		this.id = id;
	}

	public Person(String firstName, String lastName, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public Person(String id, String firstName, String lastName, String phoneNumber) {
		this.id = id;
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

	public void setId(String id2) {
		this.id=id2;
		
	}

	public void setFirstName(String firstName) {
		this.firstName=firstName;
		
	}
	
	public void setLastName(String lastName) {
		this.lastName=lastName;
		
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber=phoneNumber;
		
	}

}
