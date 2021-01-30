package it.giannipeg.back.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.giannipeg.back.entity.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Serializable> {

}
