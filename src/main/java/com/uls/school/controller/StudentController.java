package com.uls.school.controller;

import com.uls.school.entity.Course;
import com.uls.school.entity.Student;
import com.uls.school.repository.CourseRepository;
import com.uls.school.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@PostMapping("/students")
	public ResponseEntity<Student> addStudent(@RequestBody Student student) {
		log.info("Adding student: {}", student);
		return ResponseEntity.ok(studentRepository.save(student)); // HTTP 200 Ok
	}

	@GetMapping("/students/{studentId}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
		log.info("Retrieving author with id: {}", studentId);
		return studentRepository.findById(studentId)
				.map(ResponseEntity::ok) // HTTP 200 Ok
				.orElseGet(() -> ResponseEntity.notFound().build()); // HTTP 404 Not Found
	}

	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudents() {
		log.info("Retrieving all authors");
		return ResponseEntity.ok(studentRepository.findAll()); // HTTP 200 Ok
	}

	@PostMapping("/students/{studentId}/courses/{courseId}")
	public ResponseEntity<?> enrollStudentInCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
		log.info("Enrolling student with id: {} to course with id: {}", studentId, courseId);
		Student student = studentRepository.findById(studentId).orElseThrow();
		Course course = courseRepository.findById(courseId).orElseThrow();
		student.getCourses().add(course);
		return ResponseEntity.ok(studentRepository.save(student)); // HTTP 200 Ok
	}

	@GetMapping("/students/{studentId}/courses")
	public List<Course> getCoursesOfStudent(@PathVariable Long studentId) {
		Student student = studentRepository.findById(studentId).orElseThrow();
		return student.getCourses();
	}
}
