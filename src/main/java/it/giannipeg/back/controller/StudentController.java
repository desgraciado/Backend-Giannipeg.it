package it.giannipeg.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.giannipeg.back.entity.Student;
import it.giannipeg.back.service.StudentService;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Student save(@RequestBody Student student) {
		Student studentResponse = (Student) studentService.saveStudent(student);
		return studentResponse;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Student getStudent(@PathVariable int id) {
		Student studentResponse = (Student) studentService.getStudent(id);
		return studentResponse;
	}

}
