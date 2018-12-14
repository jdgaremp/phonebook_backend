package SpringBootApplication.Repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import SpringBootApplication.model.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, String>{
	
     public List<Person> search(List<HashMap<String, String>> personAttributeTypesToValues);

}
