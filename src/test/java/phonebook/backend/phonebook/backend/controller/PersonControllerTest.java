package phonebook.backend.phonebook.backend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import SpringBootApplication.controller.PersonController;
import SpringBootApplication.model.Person;
import SpringBootApplication.service.PersonService;
@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {
	
	@InjectMocks
	PersonController personController;
	
	@Mock
	PersonService personService;
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = standaloneSetup(personController).build();
	}
	
	@Test
	public void show_the_list_of_persons() throws Exception {
		Person person = new Person("0","Sam","Smith","+00 00 000001");
		List<Person> persons = new ArrayList<>();
		persons.add(person);
		when(personService.findAll()).thenReturn(persons);
		
		mockMvc.perform(get("/persons"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].id", is(person.getId())))
		.andExpect(jsonPath("$[0].firstName", is(person.getFirstName())))
        .andExpect(jsonPath("$[0].lastName", is(person.getLastName())))
        .andExpect(jsonPath("$[0].phoneNumber", is(person.getPhoneNumber())));
	}
	
	@Test
	public void show_one_person_for_specific_id() throws Exception {
		Person person = new Person("0","Sam","Smith","+00 00 000001");
		when(personService.findById("0")).thenReturn(Optional.ofNullable(person));
		
		mockMvc.perform(get("/persons/0"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(person.getId())))
		.andExpect(jsonPath("$.firstName", is(person.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(person.getLastName())))
        .andExpect(jsonPath("$.phoneNumber", is(person.getPhoneNumber())));
	}
	
	@Test
	public void show_nothing_when_person_with_specific_id_does_not_exist() throws Exception {
		final String ID = "0";
		when(personService.findById(ID)).thenReturn(Optional.empty());
		
		mockMvc.perform(get("/persons/"+ID))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void search_person_by_first_name() throws Exception {
		Person person = new Person("0","Sam","Smith","+00 00 000001");
		List<Person> persons = new ArrayList<>();
		persons.add(person);
		when(personService.findByFirstName("Sam")).thenReturn(persons);
		
		mockMvc.perform(get("/persons/firstname:Sam"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].id", is(person.getId())))
		.andExpect(jsonPath("$[0].firstName", is(person.getFirstName())))
        .andExpect(jsonPath("$[0].lastName", is(person.getLastName())))
        .andExpect(jsonPath("$[0].phoneNumber", is(person.getPhoneNumber())));
	}
	
	
	
	
	

}
