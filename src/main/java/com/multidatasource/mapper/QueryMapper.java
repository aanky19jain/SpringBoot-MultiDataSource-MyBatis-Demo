package com.multidatasource.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.multidatasource.model.City;
import com.multidatasource.model.Employee;
import com.multidatasource.model.WorkBook;

@Mapper
public interface QueryMapper {

	public WorkBook getWorkBookById(String workbookId);

	public Employee getEmployeeById(Integer employeeId);

	public City getCityById(Integer cityId);

}
