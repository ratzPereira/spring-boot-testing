package com.ratz.springboottesting.service;

import com.ratz.springboottesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

  Employee saveEmployee(Employee employee);
  List<Employee> getAllEmployees();
  Optional<Employee> getEmployeeById(long id);
  Employee updateEmployee(long id, Employee employee);
  void deleteEmployee(long id);
}
