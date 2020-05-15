package com.multidatasource.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Employee {
	@Id
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
}
