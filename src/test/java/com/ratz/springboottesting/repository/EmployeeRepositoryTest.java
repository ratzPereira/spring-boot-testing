package com.ratz.springboottesting.repository;

import com.ratz.springboottesting.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  //JUnit test for save employee operation
  @Test
  @DisplayName("Test for save Employee operation")
  public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

    //given - pre-condition or setup
    Employee employee = Employee.builder()
        .firstName("Ratz")
        .lastName("Pereira")
        .email("some@email.com")
        .build();


    //when - action or the behaviour that we are going test
    Employee savedEmployee = employeeRepository.save(employee);


    //then - verify the output
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getId()).isGreaterThan(0);

  }

  //JUnit test for
  @Test
  @DisplayName("Test for get all Employee list")
  public void givenEmployeeList_whenFindAll_thenReturnEmployeeList(){

    //given - pre-condition or setup
    Employee employeeOne = Employee.builder()
        .firstName("Ratz")
        .lastName("Pereira")
        .email("some@email.com")
        .build();

    Employee employeeTwo = Employee.builder()
        .firstName("Jonny")
        .lastName("Pereira")
        .email("some@email.com")
        .build();


    //when - action or the behaviour that we are going test
    employeeRepository.save(employeeOne);
    employeeRepository.save(employeeTwo);
    List<Employee> employeeList = employeeRepository.findAll();

    //then - verify the output
    assertThat(employeeList).isNotNull();
    assertThat(employeeList.size()).isEqualTo(2);
  }

  //JUnit test for get Employee by ID operation
  @Test
  @DisplayName("Test for get Employee by ID operation")
  public void givenEmployeeObject_whenFindByID_thenReturnEmployeeObject() {

    //given - pre-condition or setup
    Employee employeeOne = Employee.builder()
        .firstName("Ratz")
        .lastName("Pereira")
        .email("some@email.com")
        .build();


    //when - action or the behaviour that we are going test
    employeeRepository.save(employeeOne);
    Employee employee = employeeRepository.findById(employeeOne.getId()).get();


    //then - verify the output
    assertThat(employee).isNotNull();
    assertThat(employee.getId()).isEqualTo(employeeOne.getId());
  }

  //JUnit test for find Employee by email
  @Test
  @DisplayName("Test for find Employee by email")
  public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployee() {

    //given - pre-condition or setup
    Employee employeeOne = Employee.builder()
        .firstName("Ratz")
        .lastName("Pereira")
        .email("some@email.com")
        .build();


    //when - action or the behaviour that we are going test
    employeeRepository.save(employeeOne);
    Employee employee = employeeRepository.findByEmail(employeeOne.getEmail()).get();


    //then - verify the output
    assertThat(employee).isNotNull();
    assertThat(employee.getEmail()).isEqualTo(employeeOne.getEmail());
  }

  //JUnit test for update Employee
  @Test
  @DisplayName("Test for update Employee")
  public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

    //given - pre-condition or setup
    Employee employeeOne = Employee.builder()
        .firstName("Ratz")
        .lastName("Pereira")
        .email("some@email.com")
        .build();


    //when - action or the behaviour that we are going test
    employeeRepository.save(employeeOne);
    Employee employee = employeeRepository.findById(employeeOne.getId()).get();
    employee.setEmail("updated@email.com");
    employee.setFirstName("Updated Name");
    employeeRepository.save(employee);

    //then - verify the output
    assertThat(employee).isNotNull();
    assertThat(employee.getEmail()).isEqualTo("updated@email.com");
    assertThat(employee.getFirstName()).isEqualTo("Updated Name");
  }
}
