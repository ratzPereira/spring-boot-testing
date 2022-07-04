package com.ratz.springboottesting.service;

import com.ratz.springboottesting.model.Employee;
import com.ratz.springboottesting.repository.EmployeeRepository;
import com.ratz.springboottesting.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  private Employee employee;

  @BeforeEach
  public void setUp(){

    //employeeRepository = Mockito.mock(EmployeeRepository.class);
    //employeeService = new EmployeeServiceImpl(employeeRepository);

    employee = Employee.builder()
        .id(1L)
        .firstName("Ratz")
        .lastName("Pereira")
        .email("some@email.com")
        .build();
  }

  //JUnit test for saveEmployee method
  @Test
  @DisplayName("Test for saveEmployee with success!")
  public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

    //given - pre-condition or setup
    given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

    given(employeeRepository.save(employee)).willReturn(employee);


    //when - action or the behaviour that we are going test
    Employee savedEmployee = employeeService.saveEmployee(employee);


    //then - verify the output
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getId()).isEqualTo(employee.getId());

  }
}
