package com.ratz.springboottesting.service;

import com.ratz.springboottesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

  Employee saveEmployee(Employee employee);
  List<Employee> getAllEmployees();
  Optional<Employee> getEmployeeBtId(long id);
  Employee updateEmployee(Employee employee);
  void deleteEmployee(long id);
}
