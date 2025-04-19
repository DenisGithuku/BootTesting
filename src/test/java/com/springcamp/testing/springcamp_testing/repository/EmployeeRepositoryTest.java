package com.springcamp.testing.springcamp_testing.repository;

import com.springcamp.testing.springcamp_testing.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Junit test for save employee operation
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSaved_thenReturnSavedEmployee() {
        // given - precondition for setup
        Employee employee = Employee.builder()
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();

        // when - action or behaviour to test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify employee has been saved
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
        assertThat(savedEmployee.getEmail()).isSameAs(employee.getEmail());
    }

    // Junit test for get all employees
    @DisplayName("Junit test for get all employees")
    @Test
    public void givenEmployeeList_whenFindAll_returnAllEmployees() {
        // given - precondition for setup
        Employee employee1 = Employee.builder()
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Alvin")
                .lastName("Omondi")
                .email("alvinomondi@gmail.com")
                .build();

        employeeRepository.saveAll(List.of(employee1, employee2));

        // when - action or behaviour to test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // Junit test for getting employee by id
    @DisplayName("Junit test for getting employee by id")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        // given - precondition for setup
        Employee employee1 = Employee.builder()
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();

        employeeRepository.save(employee1);

        // when - action or behaviour to test
        Employee employeeInDb = employeeRepository.findById(employee1.getId()).get();

        // then - verify output
        assertThat(employeeInDb).isNotNull();
    }

    // Junit test for update employee
    @DisplayName("Junit test for update employee")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        // given - precondition for setup
        Employee employee = Employee.builder()
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when - action or behaviour to test
        Employee employeeInDb = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify output
        assertThat(employeeInDb).isNotNull();
    }

    // Junit test for updated employee
    @DisplayName("Junit test for updated employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {
        // given - precondition for setup
        Employee employee = Employee.builder()
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when - action or behaviour to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("githuku@gmail.com");

        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then - verify output
        assertThat(updatedEmployee.getEmail()).isEqualTo("githuku@gmail.com");
    }

    // Junit test for employee delete operation
    @DisplayName("Junit test for employee delete operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        // given - precondition for setup
        Employee employee = Employee.builder()
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when - action or behaviour to test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify output
        assertThat(employeeOptional).isEmpty();
    }

    // Junit test for custom query using JPQL with index
    @DisplayName("Junit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        // given - precondition for setup
        Employee employee = Employee.builder()
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();
        employeeRepository.save(employee);
        // when - action or behaviour to test
        Employee savedEmployee = employeeRepository.findByJPQLIndexParams(employee.getFirstName(), employee.getLastName());

        // then - verify output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for custom query using JPQL with named params
    @DisplayName("Junit test for custom query using JPQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        // given - precondition for setup
        Employee employee = Employee.builder()
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();
        employeeRepository.save(employee);
        // when - action or behaviour to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());

        // then - verify output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for custom query using native SQL with index params
    @DisplayName("Junit test for custom query using native SQL with index params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNativeIndexParams_thenReturnEmployeeObject() {
        // given - precondition for setup
        Employee employee = Employee.builder()
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();
        employeeRepository.save(employee);
        // when - action or behaviour to test
        Employee savedEmployee = employeeRepository.findByNativeSQLIndexParams(employee.getFirstName(), employee.getLastName());

        // then - verify output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for custom query using native SQL with named params
    @DisplayName("Junit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
        // given - precondition for setup
        Employee employee = Employee.builder()
                .firstName("Denis")
                .lastName("Githuku")
                .email("denisgithuku@gmail.com")
                .build();
        employeeRepository.save(employee);
        // when - action or behaviour to test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(), employee.getLastName());

        // then - verify output
        assertThat(savedEmployee).isNotNull();
    }

}