package com.ratz.springboottesting.repository;

import com.ratz.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

  Optional<Employee> findByEmail (String email);

  // define custom query using JPQL (java persistence query language) with index params
  @Query("select e from Employee e where e.firstName =?1 and e.lastName = ?2")
  Employee findByJPQL(String firstName, String lastName);

  // define custom query using JPQL (java persistence query language) with named params
  @Query("select e from Employee e where e.firstName =:elo and e.lastName =:oi")
  Employee findByJPQLNamedParams(@Param("elo")String firstName, @Param("oi")String lastName);

  // define custom query using native SQL with index params
  @Query(value = "select * from employees e where e.first_name =?1 and e.last_name =?2",nativeQuery = true)
  Employee findByNativeSQL(String firstName, String lastName);
}
