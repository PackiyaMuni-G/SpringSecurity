package com.security.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {
	  private List<Student> students = new ArrayList<>(Arrays.asList(
		        new Student(1, "muni", 80),
		        new Student(2, "guru", 60),
		        new Student(3, "selvam", 70)
		    ));

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return students;
    }
    
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest req) {
      return	(CsrfToken) req.getAttribute("_csrf");
    }
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
    	students.add(student);
    	return student;
    }
}