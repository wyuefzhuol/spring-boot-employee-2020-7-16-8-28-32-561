package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import com.thoughtworks.springbootemployee.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping()
    public void addEmployee(@RequestBody Employee employee){
        employeeService.addEmployee(employee);
    }

    @GetMapping()
    public List<Employee> getEmployees(@PageableDefault(size = 2) Pageable pageable){
        return employeeService.getEmployees(pageable).getContent();
    }

    @GetMapping("/{id}")
    public List<Employee> getEmployeeById(@PathVariable int id){
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public void updateEmployeeById(@PathVariable int id, @RequestBody Employee employee){
        employeeService.updateEmployeeById(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable("id")int id){
        employeeService.deleteEmployeeById(id);
    }
}
