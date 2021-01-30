package it.giannipeg.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.giannipeg.back.entity.Person;
import it.giannipeg.back.repository.PersonRepository;



@CrossOrigin
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/persons")
    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }

    
    @PostMapping(value = "/persons")
    public Person addPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }
}
