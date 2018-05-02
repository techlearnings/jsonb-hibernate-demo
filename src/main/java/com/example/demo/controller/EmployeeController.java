package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Employee;
import com.example.demo.hibernate.CustomClassType;
import com.example.demo.service.EmployeeService;

/**
 * @author shiv.n.dhiman
 *
 */
@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	/*
	 * Adds new employee to database
	 */
	@RequestMapping("/add")
	public String addEmployee() {
		
		System.out.println("Add method invoked");
		
		Employee e = new Employee();
		
		// Using map to store key-value pairs
		Map<String, String> information = new HashMap<>();
		information.put("name", "shiv");
		information.put("age","20");
		e.setInformation(information);
		
		// Using own custom object to store input json
		CustomClassType c = new CustomClassType();
		c.setA(10);
		c.setB("ABC");
		e.setData(c);
		employeeService.addEmployee(e);
		System.out.println("Added employee");
		return "index";
	}
	
	/*
	 *  This method gets the employee information on the basis of input id.
	 */
	@RequestMapping("/getInfo/{id}")
	public String getEmployeeInformation(@PathVariable Integer id) {
		Employee e = employeeService.findById(id);
		System.out.println("Information:"+e.getInformation());
		System.out.println("Data: "+e.getData());
		return e.getData().toString();
	}
	
}
