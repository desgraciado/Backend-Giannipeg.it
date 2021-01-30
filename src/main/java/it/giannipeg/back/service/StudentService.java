package it.giannipeg.back.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.giannipeg.back.entity.Student;
import it.giannipeg.back.repository.StudentRepository;


@Service("studentService")
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Student saveStudent(Student student) {
		Student response = studentRepository.save(student);
		return response;
	}

	@Transactional(readOnly = true)
	public Student getStudent(int id) {
		// Optional<Student> studentResponse = studentRepository.findById(id);
		Student student = entityManager.find(Student.class, id);
		return student;
	}

}
