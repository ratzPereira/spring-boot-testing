package com.ratz.springboottesting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratz.springboottesting.model.Employee;
import com.ratz.springboottesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private Employee employee;
  private Employee employeeTwo;
  private Employee updatedEmployee;

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

    updatedEmployee = Employee.builder()
        .firstName("Updated")
        .lastName("Employee")
        .email("updated@email.com")
        .build();

    employeeRepository.deleteAll();
  }

  @Test
  @DisplayName("Integration Test for create Employee")
  public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

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
  @DisplayName("Integration Test for get all Employees")
  public void givenListOfEmployee_whenGetAllEmployee_thenReturnAllEmployees() throws Exception {

    //given - pre-condition or setup
    List<Employee> employeeList = new ArrayList<>();
    employeeList.add(employee);
    employeeList.add(employeeTwo);
    employeeRepository.saveAll(employeeList);


    //when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees"));


    //then - verify the output
    response.andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(employeeList.size())))
        .andDo(print());

  }

  @Test
  @DisplayName("Integration Test for get Employee by Id with success")
  public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() throws Exception {

    //given - pre-condition or setup
    employeeRepository.save(employeeTwo);

    //when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeTwo.getId()));

    //then - verify the output
    response.andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(employeeTwo.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(employeeTwo.getLastName())))
        .andExpect(jsonPath("$.email", is(employeeTwo.getEmail())))
        .andDo(print());
  }

  @Test
  @DisplayName("Integration Test for get Employee by Id with error")
  public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeWithError() throws Exception {

    mockMvc.perform(get("/api/employees/{id}", employee.getId())).andExpect(status().isNotFound());

  }

  @Test
  @DisplayName("Integration Test for update Employee with success")
  public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {

    //given - pre-condition or setup
    employeeRepository.save(employeeTwo);

    //when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(post("/api/employees/{id}", employeeTwo.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedEmployee)));

    //then - verify the output
    response.andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
        .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())))
        .andDo(print());
  }

  @Test
  @DisplayName("Integration Test for update Employee with error")
  public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeWithError() throws Exception {

    //when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(post("/api/employees/{id}", employee.getId())
        .contentType(MediaType.APPLICATION_JSON));
    //.content(objectMapper.writeValueAsString(updatedEmployee)));

    //then - verify the output
    response.andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  @DisplayName("Integration Test for delete Employee")
  public void givenEmployeeId_whenDeletingEmployee_thenDeleteEmployee() throws Exception {

    //given - pre-condition or setup
    employeeRepository.save(employeeTwo);


    //when - action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeTwo.getId()));

    //then - verify the output
    response.andExpect(status().isOk())
        .andDo(print());

  }
}
