package com.springcamp.testing.springcamp_testing.service.impl;

import com.springcamp.testing.springcamp_testing.exception.ResourceNotFoundException;
import com.springcamp.testing.springcamp_testing.model.Employee;
import com.springcamp.testing.springcamp_testing.repository.EmployeeRepository;
import com.springcamp.testing.springcamp_testing.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    // useful for initializing this service in unit tests
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        // Find out if an employee with that email is already saved
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee with email: " + employee.getEmail() + " found");
        }
        return employeeRepository.save(employee);
    }
}