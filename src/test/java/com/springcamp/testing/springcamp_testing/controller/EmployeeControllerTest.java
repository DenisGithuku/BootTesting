package com.springcamp.testing.springcamp_testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcamp.testing.springcamp_testing.model.Employee;
import com.springcamp.testing.springcamp_testing.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    public void setup() {
        this.employee = Employee.builder().firstName("Denis").lastName("Githuku").email("denisgithuku@gmail.com").build();
    }

    // Junit test for create employee
    @DisplayName("Junit test for create employee")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given—precondition for the setup
        // Create saveEmployee stub
        given(employeeService.saveEmployee(any(Employee.class))).willAnswer((invocation -> invocation.getArgument(0)));

        // when—action or behaviour to test
        ResultActions response = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));

        // then—verify output
        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is(employee.getFirstName()))).andExpect(jsonPath("$.lastName", is(employee.getLastName()))).andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    // Junit test to get all the employees
    @DisplayName("Junit test for get all employees")
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        // given--precondition for setup
        List<Employee> employeeList = new ArrayList<>();
        Employee employee1 = Employee.builder().firstName("Peter").lastName("Odhiambo").email("peterodhiambo@gmail.com").build();
        employeeList.add(employee);
        employeeList.add(employee1);

        given(employeeService.findAll()).willReturn(employeeList);

        // when--action or behaviour to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then--verify output
        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(employeeList.size())));

    }

    // Junit test for get employee by id positive scenario
    @DisplayName("Junit test for get employee by id positive scenario")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given - precondition for setup
        given(employeeService.findById(employee.getId())).willReturn(Optional.of(employee));

        // when - action or behaviour to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        // then - verify output
        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.firstName", is(employee.getFirstName()))).andExpect(jsonPath("$.lastName", is(employee.getLastName()))).andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

}