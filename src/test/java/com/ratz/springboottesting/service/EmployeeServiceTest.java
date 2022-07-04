package com.ratz.springboottesting.service;

import com.ratz.springboottesting.exception.ResourceNotFoundException;
import com.ratz.springboottesting.model.Employee;
import com.ratz.springboottesting.repository.EmployeeRepository;
import com.ratz.springboottesting.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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

  //JUnit test for saveEmployee method with failure
  @Test
  @DisplayName("Test for saveEmployee with error!")
  public void givenBadEmployeeObject_whenSaveEmployee_thenThrowException() {

    //given - pre-condition or setup
    given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

    //when - action or the behaviour that we are going test
    org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));

    //then
    verify(employeeRepository, never()).save(any(Employee.class));
  }

  //JUnit test for get all employees
  @Test
  @DisplayName("Test for getAllEmployees")
  public void givenEmployeeList_whenGetAllEmployee_thenReturnEmployeeList() {

    //given - pre-condition or setup
    Employee employeeTwo = Employee.builder()
        .id(2L)
        .firstName("Ratzy")
        .lastName("Pereira")
        .email("some@email.com")
        .build();

    given(employeeRepository.findAll()).willReturn(List.of(employee,employeeTwo));

    //when - action or the behaviour that we are going test
    List<Employee> employeeList = employeeService.getAllEmployees();


    //then - verify the output
    assertThat(employeeList).isNotNull();
    assertThat(employeeList.size()).isEqualTo(2);

  }
}
