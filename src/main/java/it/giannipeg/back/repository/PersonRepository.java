package it.giannipeg.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.giannipeg.back.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}