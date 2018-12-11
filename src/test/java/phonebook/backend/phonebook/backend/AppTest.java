package phonebook.backend.phonebook.backend;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import SpringBootApplication.controller.PersonController;
import SpringBootApplication.model.Person;

/**
 * Unit test for simple App.
 */


@RunWith(SpringRunner.class)
@SpringBootTest()
@WebMvcTest(PersonController.class)
public class AppTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void getPersons_returnPersonsDetails() throws Exception {
		System.out.println("test");
		/*//arrange
		
		// act
		ResponseEntity<Person> response = restTemplate.getForEntity("/persons",Person.class);
		
		// assert
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);*/
	}
	
	

}

