package com.ratz.springboottesting.service.impl;

import com.ratz.springboottesting.exception.ResourceNotFoundException;
import com.ratz.springboottesting.model.Employee;
import com.ratz.springboottesting.repository.EmployeeRepository;
import com.ratz.springboottesting.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
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

  @Override
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  @Override
  public Optional<Employee> getEmployeeBtId(long id) {

    Optional<Employee> employee = employeeRepository.findById(id);

    if (!employee.isEmpty()) {

      return employee;

    } else {

      throw new ResourceNotFoundException("Employee with that ID not found!");
    }
  }

  @Override
  public Employee updateEmployee(Employee employee) {

    Employee employeeToUpdate = employeeRepository.findById(employee.getId()).orElseThrow(()->
        new ResourceNotFoundException("Employee with that ID not found!"));

    employeeToUpdate.setFirstName(employee.getFirstName());
    employeeToUpdate.setLastName(employee.getLastName());
    employeeToUpdate.setEmail(employee.getEmail());

    return employeeRepository.save(employeeToUpdate);
  }


  @Override
  public void deleteEmployee(long id) {

    Employee employeeToDelete = employeeRepository.findById(id).orElseThrow(()->
        new ResourceNotFoundException("Employee with that ID not found!"));

    employeeRepository.deleteById(employeeToDelete.getId());
  }
}
