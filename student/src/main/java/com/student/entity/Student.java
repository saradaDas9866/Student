package com.student.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="STUDENT")
public class Student {
	@Id
	@GeneratedValue(generator = "nativeGenerator")
	@GenericGenerator(name="nativeGenerator", strategy="native")
	@Column(name="Id")
	private Long rowId;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="SECTION")
	private String section;
	
	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="DOB")
	private Date dob;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="Marks1")
	private Double marks1;
	
	@Column(name="Marks2")
	private Double marks2;
	
	@Column(name="Marks3")
	private Double marks3;
	
	@Column(name="TOTAL")
	private Double total;
	
	@Column(name="AVERAGE")
	private Double average;
	
	@Column(name="RESULT")
	private Double result;

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Double getMarks1() {
		return marks1;
	}

	public void setMarks1(Double marks1) {
		this.marks1 = marks1;
	}

	public Double getMarks2() {
		return marks2;
	}

	public void setMarks2(Double marks2) {
		this.marks2 = marks2;
	}

	public Double getMarks3() {
		return marks3;
	}

	public void setMarks3(Double marks3) {
		this.marks3 = marks3;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}
	
	
}
