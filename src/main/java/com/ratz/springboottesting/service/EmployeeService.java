package com.ratz.springboottesting.service;

import com.ratz.springboottesting.model.Employee;

import java.util.List;

public interface EmployeeService {

  Employee saveEmployee(Employee employee);
  List<Employee> getAllEmployees();
  Employee getEmployeeBtId(long id);
}
