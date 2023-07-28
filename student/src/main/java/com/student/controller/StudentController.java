package com.student.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.student.repository.StudentRepository;
import com.student.entity.Student;
@RestController
@CrossOrigin
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.setVisibility(PropertyAccessor.FIELD,Visibility.ANY);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
	}
	
	/**
	 * payload for creating user
	 *{
	 *  "firstName":"QQQ",
	 *	"lastName":"QQQ",
	 *	"section":"A",
	 *	"dob":"2023-07-12",
	 *	"gender":"M",
	 *	"marks1":"QQQ",
	 *	"marks2":"QQQ",
	 *	"marks3":"QQQ",
	 *} 
	 * 
	 * */
	@PostMapping("/createStudent")
	private ResponseEntity<Object> createStudent(HttpServletRequest request, @RequestBody String payload){
		if(!StringUtils.isEmpty(payload)) {
			try {
				JsonObject object = new Gson().fromJson(payload, JsonObject.class);
				validator(object);
				Student student = mapper.readValue(payload, new TypeReference<Student>() {});
				Double total = student.getMarks1()+student.getMarks2()+student.getMarks3();
				student.setAverage((total)/3);
				student.setTotal(300.00);
				student.setResult((total/300.00)*100);
				studentRepository.save(student);
				return ResponseEntity.ok().body("Student Saved Successfully");
			} catch (Exception e) {
				LOG.error(ExceptionUtils.getStackTrace(e));
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.badRequest().body("Payload is Empty");
		}
	}
	
	/***
	 *payload for updating user
	 *{
  	 *	"id":"5",
  	 *	"marks1":"36",
  	 *	"marks2":"36",
  	 *	"marks3":"36"
  	 *	} 
	 * 
	 * */
	@PostMapping("/updateUser")
	private ResponseEntity<Object> updateUser(HttpServletRequest request, @RequestBody String payload){
		if(!StringUtils.isEmpty(payload)) {
			try {
				JsonObject object = new Gson().fromJson(payload, JsonObject.class);
				Student studentObject = new Student();
				if(object.has("id") && object.get("id")!=null) {
					Optional<Student> student = studentRepository.findById(object.get("id").getAsLong());
					if(student.isPresent()) {
						studentObject = student.get();
					} else {
						return ResponseEntity.ok().body("Student with id "+ object.get("id").getAsLong() +" is not available in payload");
					}
				} else {
					return ResponseEntity.ok().body("id is not available in payload");
				}
				updateStudentValidator(object);
				Double total = object.get("marks1").getAsDouble()+object.get("marks2").getAsDouble()+object.get("marks3").getAsDouble();
				studentObject.setAverage((total)/3);
				studentObject.setTotal(300.00);
				studentObject.setResult((total/300.00)*100);
				studentRepository.save(studentObject);
				return ResponseEntity.ok().body("Student Saved Successfully");
			} catch (Exception e) {
				LOG.error(ExceptionUtils.getStackTrace(e));
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.badRequest().body("Payload is Empty");
		}
	}

	private void updateStudentValidator(JsonObject object) throws Exception {
		if(!object.has("marks1") && !(object.get("marks1").getAsDouble() >35 && object.get("marks1").getAsDouble() <100)) {
			throw new Exception("Marks 1 Range Is Between O to 100");
		}
		
		if(!object.has("marks2") && !(object.get("marks2").getAsDouble() >35 && object.get("marks2").getAsDouble() <100)) {
			throw new Exception("Marks 2 Range Is Between O to 100");
		}
		
		if(!object.has("marks3") && !(object.get("marks3").getAsDouble() >35 && object.get("marks3").getAsDouble() <100)) {
			throw new Exception("Marks 3 Range Is Between O to 100");
		}
		
	}

	private void validator(JsonObject object) throws Exception {
		if(object.has("firstName")) {
			if(object.get("firstName").getAsString().length()>3) {
				throw new Exception("First Name Should Not Contain More Than 3 Character");
			}
		} else {
			throw new Exception("First Name Should Not Be Empty");
		}
		
		if(object.has("lastName")) {
			if(object.get("lastName").getAsString().length()>3) {
				throw new Exception("Last Name Should Not Contain More Than 3 Character");
			}
		} else {
			throw new Exception("Last Name Should Not Be Empty");
		}
		
		if(object.has("dob") && StringUtils.isEmpty(object.get("dob").getAsString())) {
			throw new Exception("Date Of Birth Is a mandatory Field");
		} else if(!object.has("dob")){
			throw new Exception("Date Of Birth Is a mandatory Field");
		}
		
		if(object.has("marks1") && !(object.get("marks1").getAsDouble() >35 && object.get("marks1").getAsDouble() <100)) {
			throw new Exception("Marks 1 Range Is Between O to 100");
		}
		
		if(object.has("marks2") && !(object.get("marks2").getAsDouble() >35 && object.get("marks2").getAsDouble() <100)) {
			throw new Exception("Marks 2 Range Is Between O to 100");
		}
		
		if(object.has("marks3") && !(object.get("marks3").getAsDouble() >35 && object.get("marks3").getAsDouble() <100)) {
			throw new Exception("Marks 3 Range Is Between O to 100");
		}
		List<String> sections = Arrays.asList("A,B,C".split(","));
		
		if(object.has("section") && !sections.contains(object.get("section").getAsString())) {
			throw new Exception("Valid values for section are A, B and C.");
		}
		List<String> gender = Arrays.asList("M,F".split(","));
		if(object.has("gender") && !gender.contains(object.get("gender").getAsString())) {
			throw new Exception("Valid values for Gender are M or F.");
		}
	}
	
}
