package com.springcamp.testing.springcamp_testing.service;

import com.springcamp.testing.springcamp_testing.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> findAll();

    Optional<Employee> findById(long id);
}