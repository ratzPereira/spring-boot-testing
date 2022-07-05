package com.ratz.springboottesting.controller;

import com.ratz.springboottesting.model.Employee;
import com.ratz.springboottesting.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
    return employeeService.getEmployeeBtId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee){
    return ResponseEntity.ok().body(employeeService.updateEmployee(id, employee));
  }
}
