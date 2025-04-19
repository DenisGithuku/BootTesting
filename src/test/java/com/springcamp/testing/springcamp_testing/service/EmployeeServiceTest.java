package com.springcamp.testing.springcamp_testing.service;

import com.springcamp.testing.springcamp_testing.model.Employee;
import com.springcamp.testing.springcamp_testing.repository.EmployeeRepository;
import com.springcamp.testing.springcamp_testing.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    // Junit test for save employee
    @DisplayName("Junit test for save employee")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given - precondition for setup
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or behaviour to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify output
        assertThat(savedEmployee).isNotNull();
    }
}