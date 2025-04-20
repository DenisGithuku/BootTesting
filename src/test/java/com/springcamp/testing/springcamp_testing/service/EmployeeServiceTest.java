package com.springcamp.testing.springcamp_testing.service;

import com.springcamp.testing.springcamp_testing.exception.ResourceNotFoundException;
import com.springcamp.testing.springcamp_testing.model.Employee;
import com.springcamp.testing.springcamp_testing.repository.EmployeeRepository;
import com.springcamp.testing.springcamp_testing.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder().id(1L).firstName("Denis").lastName("Githuku").email("denisgithuku@gmail.com").build();
    }

    // Junit test for save employee
    @DisplayName("Junit test for save employee")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given - precondition for setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or behaviour to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for save employee throws exception
    @DisplayName("Junit test for save employee throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given - precondition for setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when - action or behaviour to test
        assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));

        // then - verify output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // Junit test for find all employees
    @DisplayName("Junit test for find all employees")
    @Test
    public void givenEmployeeList_whenFindAllEmployees_thenReturnEmployeeList() {
        // given - precondition for setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Odhiambo")
                .email("tony@gmail.com")
                .build();

        // when - action or behaviour to test
        // create find all stub
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // Junit test for find all employees
    @DisplayName("Junit test for empty find all employees returns empty list")
    @Test
    public void givenEmployeeList_whenEmptyFindAllEmployees_thenReturnEmptyEmployeeList() {
        // when - action or behaviour to test
        // create find all stub
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }
}