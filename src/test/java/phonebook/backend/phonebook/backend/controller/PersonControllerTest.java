package phonebook.backend.phonebook.backend.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import SpringBootApplication.controller.PersonController;
import SpringBootApplication.service.PersonService;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {
	
	@InjectMocks
	PersonController personController;
	
	@Mock
	PersonService personService;
	
	@Test
	public void show_the_list_of_persons() {
		
	}
	

}
