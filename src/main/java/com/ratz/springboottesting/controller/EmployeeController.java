package com.ratz.springboottesting.controller;

import com.ratz.springboottesting.model.Employee;
import com.ratz.springboottesting.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Employee createEmployee(@RequestBody Employee employee){
    return employeeService.saveEmployee(employee);
  }

  @GetMapping
  public List<Employee> getAllEmployee(){
    return employeeService.getAllEmployees();
  }
}
