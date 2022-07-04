package com.ratz.springboottesting.service.impl;

import com.ratz.springboottesting.exception.ResourceNotFoundException;
import com.ratz.springboottesting.model.Employee;
import com.ratz.springboottesting.repository.EmployeeRepository;
import com.ratz.springboottesting.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Employee saveEmployee(Employee employee) {

    Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());

    if (savedEmployee.isPresent()) throw new ResourceNotFoundException("User already exist with given email");

    return employeeRepository.save(employee);
  }
}
