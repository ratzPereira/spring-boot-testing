package com.ratz.springboottesting.repository;

import com.ratz.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

  Optional<Employee> findByEmail (String email);

  // define custom query using JPQL (java persistence query language) with index params
  @Query("select e from Employee e where e.firstName =?1 and e.lastName = ?2")
  Employee findByJPQL(String firstName, String lastName);
}
