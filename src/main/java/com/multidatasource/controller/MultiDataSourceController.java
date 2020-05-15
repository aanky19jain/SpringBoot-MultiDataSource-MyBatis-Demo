package com.multidatasource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.multidatasource.model.City;
import com.multidatasource.model.Employee;
import com.multidatasource.model.WorkBook;
import com.multidatasource.service.MultiDataSourceService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@RestController
public class MultiDataSourceController {

	@Autowired
	private MultiDataSourceService service;
	
	public static final String API_AUTH_TOKEN_NAME = "API-AUTH-TOKEN";

	@Parameter(name = API_AUTH_TOKEN_NAME, in = ParameterIn.HEADER, required = true)
	@GetMapping("/v1/workbooks/{workbookId}")
	public WorkBook getWorkBookById(@PathVariable String workbookId) {
		return service.getWorkBookById(workbookId);
	}

	@Parameter(name = API_AUTH_TOKEN_NAME, in = ParameterIn.HEADER, required = true)
	@GetMapping("/v1/employees/{employeeId}")
	public Employee getEmployeeById(@PathVariable Integer employeeId) {
		return service.getEmployeeById(employeeId);
	}
	
	@Parameter(name = API_AUTH_TOKEN_NAME, in = ParameterIn.HEADER, required = true)
	@GetMapping("/v1/cities/{cityId}")
	public City getCityById(@PathVariable Integer cityId) {
		return service.getCityById(cityId);
	}

}
