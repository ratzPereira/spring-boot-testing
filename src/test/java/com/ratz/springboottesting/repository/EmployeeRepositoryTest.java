package com.ratz.springboottesting.repository;

import com.ratz.springboottesting.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  private Employee employee;

  @BeforeEach
  public void setUp(){
     employee = Employee.builder()
        .firstName("Ratz")
        .lastName("Pereira")
        .email("some@email.com")
        .build();
  }


  //JUnit test for save employee operation
  @Test
  @DisplayName("Test for save Employee operation")
  public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

    //given - pre-condition or setup
//    Employee employee = Employee.builder()
//        .firstName("Ratz")
//        .lastName("Pereira")
//        .email("some@email.com")
//        .build();


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
//    Employee employeeOne = Employee.builder()
//        .firstName("Ratz")
//        .lastName("Pereira")
//        .email("some@email.com")
//        .build();

    Employee employeeTwo = Employee.builder()
        .firstName("Jonny")
        .lastName("Pereira")
        .email("some@email.com")
        .build();


    //when - action or the behaviour that we are going test
    employeeRepository.save(employee);
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
//    Employee employeeOne = Employee.builder()
//        .firstName("Ratz")
//        .lastName("Pereira")
//        .email("some@email.com")
//        .build();


    //when - action or the behaviour that we are going test
    employeeRepository.save(employee);
    Employee employeeOne = employeeRepository.findById(employee.getId()).get();


    //then - verify the output
    assertThat(employeeOne).isNotNull();
    assertThat(employee.getId()).isEqualTo(employeeOne.getId());
  }

  //JUnit test for find Employee by email
  @Test
  @DisplayName("Test for find Employee by email")
  public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployee() {

    //given - pre-condition or setup
//    Employee employeeOne = Employee.builder()
//        .firstName("Ratz")
//        .lastName("Pereira")
//        .email("some@email.com")
//        .build();


    //when - action or the behaviour that we are going test
    employeeRepository.save(employee);
    Employee employeeOne = employeeRepository.findByEmail(employee.getEmail()).get();


    //then - verify the output
    assertThat(employee).isNotNull();
    assertThat(employee.getEmail()).isEqualTo(employeeOne.getEmail());
  }

  //JUnit test for update Employee
  @Test
  @DisplayName("Test for update Employee")
  public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

    //given - pre-condition or setup
//    Employee employeeOne = Employee.builder()
//        .firstName("Ratz")
//        .lastName("Pereira")
//        .email("some@email.com")
//        .build();


    //when - action or the behaviour that we are going test
    employeeRepository.save(employee);
    Employee employeeOne = employeeRepository.findById(employee.getId()).get();
    employeeOne.setEmail("updated@email.com");
    employeeOne.setFirstName("Updated Name");
    employeeRepository.save(employeeOne);

    //then - verify the output
    assertThat(employeeOne).isNotNull();
    assertThat(employeeOne.getEmail()).isEqualTo("updated@email.com");
    assertThat(employeeOne.getFirstName()).isEqualTo("Updated Name");
  }

  //JUnit test for delete employee
  @Test
  @DisplayName("Test for delete Employee")
  public void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmployee() {

    //given - pre-condition or setup
//    Employee employeeOne = Employee.builder()
//        .firstName("Ratz")
//        .lastName("Pereira")
//        .email("some@email.com")
//        .build();
    employeeRepository.save(employee);

    //when - action or the behaviour that we are going test
    employeeRepository.delete(employee);
    Optional<Employee> checkIfEmployeeExists = employeeRepository.findById(employee.getId());

    //then - verify the output
    assertThat(checkIfEmployeeExists).isEmpty();

  }

  //JUnit test for testing custom JPQL query
  @Test
  @DisplayName("Test for custom query(JPQL) (find by first and last name")
  public void givenFirstAndLastName_whenFindByJPQL_thenReturnEmployee() {

    //given - pre-condition or setup
//    Employee employee = Employee.builder()
//        .firstName("Ratz")
//        .lastName("Pereira")
//        .email("some@email.com")
//        .build();
    employeeRepository.save(employee);

    //when - action or the behaviour that we are going test
    Employee employeeOne = employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());

    //then - verify the output
    assertThat(employee).isNotNull();
    assertThat(employee.getFirstName()).isEqualTo(employeeOne.getFirstName());
    assertThat(employee.getLastName()).isEqualTo(employeeOne.getLastName());
  }

  //JUnit test for testing custom JPQL query with named params
  @Test
  @DisplayName("Test for custom query (JPQL) with named params (find by first and last name")
  public void givenFirstAndLastName_whenFindByJPQLNamedParams_thenReturnEmployee() {

    //given - pre-condition or setup
//    Employee employeeOne = Employee.builder()
//        .firstName("Ratz")
//        .lastName("Pereira")
//        .email("some@email.com")
//        .build();
    employeeRepository.save(employee);

    //when - action or the behaviour that we are going test
    Employee employeeOne = employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());

    //then - verify the output
    assertThat(employee).isNotNull();
    assertThat(employee.getFirstName()).isEqualTo(employeeOne.getFirstName());
    assertThat(employee.getLastName()).isEqualTo(employeeOne.getLastName());
  }

  //JUnit test for testing custom JPQL query
  @Test
  @DisplayName("Test for custom query (SQL) (find by first and last name")
  public void givenFirstAndLastName_whenFindByNativeSQL_thenReturnEmployee() {

    //given - pre-condition or setup
//    Employee employeeOne = Employee.builder()
//        .firstName("Ratz")
//        .lastName("Pereira")
//        .email("some@email.com")
//        .build();
    employeeRepository.save(employee);

    //when - action or the behaviour that we are going test
    Employee employeeOne = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

    //then - verify the output
    assertThat(employee).isNotNull();
    assertThat(employee.getFirstName()).isEqualTo(employeeOne.getFirstName());
    assertThat(employee.getLastName()).isEqualTo(employeeOne.getLastName());
  }

  //JUnit test for testing custom JPQL query
  @Test
  @DisplayName("Test for custom query (SQL) with named params (find by first and last name")
  public void givenFirstAndLastName_whenFindByNativeSQLWithNamedParams_thenReturnEmployee() {

    //given - pre-condition or setup
//    Employee employeeOne = Employee.builder()
//        .firstName("Ratz")
//        .lastName("Pereira")
//        .email("some@email.com")
//        .build();
    employeeRepository.save(employee);

    //when - action or the behaviour that we are going test
    Employee employeeOne = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(), employee.getLastName());

    //then - verify the output
    assertThat(employee).isNotNull();
    assertThat(employeeOne.getFirstName()).isEqualTo(employee.getFirstName());
    assertThat(employeeOne.getLastName()).isEqualTo(employee.getLastName());
  }
}
