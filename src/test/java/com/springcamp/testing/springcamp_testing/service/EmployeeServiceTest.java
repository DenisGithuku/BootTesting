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
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

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
        Employee employee1 = Employee.builder().id(2L).firstName("Tony").lastName("Odhiambo").email("tony@gmail.com").build();

        // when - action or behaviour to test
        // create find all stub
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        List<Employee> employeeList = employeeService.findAll();

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

        List<Employee> employeeList = employeeService.findAll();

        // then - verify output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    // Junit test for get employee by id
    @DisplayName("Junit test for get employee by id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeId() {
        // given - precondition for setup
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        // when - action or behaviour to test
        Employee savedEmployee = employeeService.findById(employee.getId()).get();

        // then - verify output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isSameAs(employee.getId());
    }

    // Junit test for update employee
    @DisplayName("Junit test for update employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition for setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setLastName("Mwangi");
        employee.setEmail("denisgithuku@gmail.com");

        // when - action or behaviour to test
        Employee updatedEmployee = employeeService.update(employee);

        // then - verify output
        assertThat(updatedEmployee.getEmail()).isEqualTo("denisgithuku@gmail.com");
        assertThat(updatedEmployee.getLastName()).isEqualTo("Mwangi");
    }

    // Junit test for delete employee by id
    @DisplayName("Junit test for delete employee by id")
    @Test
    public void givenEmployeeId_whenDeleteEmployeeById_thenReturnNothing() {
        // given - precondition for setup
        willDoNothing().given(employeeRepository).deleteById(employee.getId());
        // when - action or behaviour to test
        employeeService.deleteById(employee.getId());

        // then - verify output
        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }
}