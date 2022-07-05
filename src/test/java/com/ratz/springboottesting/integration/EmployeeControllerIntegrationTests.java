package com.ratz.springboottesting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratz.springboottesting.model.Employee;
import com.ratz.springboottesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    employeeRepository.deleteAll();

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
  }

  @Test
  @DisplayName("Test for create Employee")
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

}
