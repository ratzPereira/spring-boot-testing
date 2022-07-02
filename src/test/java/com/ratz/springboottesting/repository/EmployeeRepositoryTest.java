package com.ratz.springboottesting.repository;

import com.ratz.springboottesting.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}
