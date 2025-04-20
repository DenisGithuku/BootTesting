package com.springcamp.testing.springcamp_testing.service;

import com.springcamp.testing.springcamp_testing.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> findAll();
}