package com.multidatasource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multidatasource.mapper.QueryMapper;
import com.multidatasource.model.City;
import com.multidatasource.model.Employee;
import com.multidatasource.model.WorkBook;

@Service
public class MultiDataSourceService {

	@Autowired
	private QueryMapper mapper;

	public WorkBook getWorkBookById(String workbookId) {
		return mapper.getWorkBookById(workbookId);
	}

	public Employee getEmployeeById(Integer employeeId) {
		return mapper.getEmployeeById(employeeId);
	}

	public City getCityById(Integer cityId) {
		return mapper.getCityById(cityId);
	}

}
