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
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;

	@PostMapping("/courses")
	public ResponseEntity<Course> addCourse(@RequestBody Course course) {
		log.info("Adding course: {}", course);
		return ResponseEntity.ok(courseRepository.save(course)); // HTTP 200 Ok
	}

	@GetMapping("/courses/{courseId}")
	public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
		log.info("Retrieving course with id: {}", courseId);
		return courseRepository.findById(courseId)
				.map(ResponseEntity::ok) // HTTP 200 Ok
				.orElseGet(() -> ResponseEntity.notFound().build()); // HTTP 404 Not Found
	}

	@GetMapping("/courses")
	public ResponseEntity<List<Course>> getAllCourses() {
		log.info("Retrieving all courses");
		return ResponseEntity.ok(courseRepository.findAll()); // HTTP 200 Ok
	}

	@GetMapping("/courses/{courseId}/students")
	public List<Student> getStudentsOfCourse(@PathVariable Long courseId) {
		Course course = courseRepository.findById(courseId).orElseThrow();
		return course.getStudents();
	}
}
