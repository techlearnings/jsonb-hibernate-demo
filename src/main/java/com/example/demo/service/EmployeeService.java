package com.example.demo.service;

import com.example.demo.domain.Employee;

public interface EmployeeService {

	public void addEmployee(Employee e);

	public Employee findById(Integer id);

}
