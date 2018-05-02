package com.example.demo.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public void addEmployee(Employee e) {
		employeeRepository.save(e);
	}

	@Override
	public Employee findById(Integer id) {
		return employeeRepository.findById(id).get();
	}
}
