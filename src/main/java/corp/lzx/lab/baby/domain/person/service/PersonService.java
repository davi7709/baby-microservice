package corp.lzx.lab.baby.domain.person.service;

import corp.lzx.lab.baby.domain.person.Person;
import corp.lzx.lab.baby.domain.person.data.PersonQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PersonService {

  private final Logger logger = LoggerFactory.getLogger(PersonService.class);
  private final PersonQueries queries = new PersonQueries();

  public List<Person> persons() {
    return queries.findAll();
  }

  public Person person(String id) {
    // @TODO: sanitize request param first
    return person(Long.valueOf(id));
  }

  public Person person(Long id) {
    return queries.findById(id);
  }


  public Person createPerson(Person person) throws IllegalStateException {
    if(queries.findByName(person.name()) != null){
      throw new IllegalStateException("person already exists");
    }else{
      return queries.insertPerson(person);
    }
  }

  public Person createPerson(String id, Person person){
    return createPerson(person);
  }


  public Person updatePerson(Long id, Person person){
    if(person(id) != null){
      return queries.updatePerson(id, person);
    }else{
      return queries.insertPerson(person);
    }
  }

  public Person updatePerson(String id, Person person){
    return updatePerson(Long.valueOf(id), person);
  }

  public boolean deletePerson(String id){
    return deletePerson(Long.valueOf(id));
  }

  public boolean deletePerson(Long id){
    return queries.deletePerson(id);
  }

}

