package com.ratz.springboottesting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratz.springboottesting.model.Employee;
import com.ratz.springboottesting.service.EmployeeService;
import static org.hamcrest.CoreMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest //it will only load the spring beans that are required to test the controller
public class EmployeeControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean //tells spring to create an instance and add to app context so that's injected into Employee Controller
  private EmployeeService employeeService;

  @Autowired
  ObjectMapper objectMapper;

  private Employee employee;
  private Employee employeeTwo;

  @BeforeEach
  public void setUp(){
    employee = Employee.builder()
        .id(1l)
        .firstName("Ratz")
        .lastName("Pereira")
        .email("some@email.com")
        .build();

    employeeTwo = Employee.builder()
        .firstName("Jesus")
        .lastName("Christ")
        .email("another@email.com")
        .build();
  }

  @Test
  @DisplayName("Test for create Employee")
  public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

    //given - pre-condition or setup
    given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
        .willAnswer((invocation) -> invocation.getArgument(0));

    //when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(post("/api/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(employee)));

    //then - verify the output
    response.andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
        .andExpect(jsonPath("$.email", is(employee.getEmail())))
        .andDo(print());

  }


  @Test
  @DisplayName("Test for get all Employees")
  public void givenListOfEmployee_whenGetAllEmployee_thenReturnAllEmployees() throws Exception {

    //given - pre-condition or setup
    List<Employee> employeeList = new ArrayList<>();
    employeeList.add(employee);
    employeeList.add(employeeTwo);

    given(employeeService.getAllEmployees()).willReturn(employeeList);

    //when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(employeeList)));


    //then - verify the output
    response.andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(employeeList.size())))
        .andDo(print());

  }


  @Test
  @DisplayName("Test for get Employee by Id with success")
  public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() throws Exception {

    //given - pre-condition or setup
    given(employeeService.getEmployeeBtId(employee.getId())).willReturn(Optional.of(employee));

    //when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(employee)));

    //then - verify the output
    response.andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
        .andExpect(jsonPath("$.email", is(employee.getEmail())))
        .andDo(print());
  }


  @Test
  @DisplayName("Test for get Employee by Id with error")
  public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeWithError() throws Exception {

    //given - pre-condition or setup
    given(employeeService.getEmployeeBtId(employee.getId())).willReturn(Optional.empty());

    //when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(employee)));

    //then - verify the output
    response.andExpect(status().isNotFound())
        .andDo(print());
  }
}
