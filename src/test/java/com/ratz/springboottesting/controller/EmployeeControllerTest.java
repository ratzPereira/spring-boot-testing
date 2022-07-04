package com.ratz.springboottesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratz.springboottesting.model.Employee;
import com.ratz.springboottesting.service.EmployeeService;
import org.hamcrest.CoreMatchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest //it will only load the spring beans that are required to test the controller
public class EmployeeControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean //tells spring to create an instance and add to app context so that's injected into Employee Controller
  private EmployeeService employeeService;

  @Autowired
  ObjectMapper objectMapper;

  private Employee employee;

  @BeforeEach
  public void setUp(){
    employee = Employee.builder()
        .firstName("Ratz")
        .lastName("Pereira")
        .email("some@email.com")
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
    response.andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())))
        .andDo(MockMvcResultHandlers.print());

  }
}
