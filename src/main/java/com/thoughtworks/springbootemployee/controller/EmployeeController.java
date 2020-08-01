package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.Dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @GetMapping("/employees/{id}")
    public EmployeeResponse getSpecificEmployee(@PathVariable("id") int id) {
        return employeeServiceImpl.getSpecificEmployee(id);
    }

    @PostMapping("/employees")
    public EmployeeResponse addEmployees(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return employeeServiceImpl.addEmployees(employeeRequest);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployees(@PathVariable("id") int id) {
        employeeServiceImpl.deleteEmployees(id);
    }

    @PutMapping("/employees")
    public void updateEmployees(@RequestBody Employee employee) {
        employeeServiceImpl.updateEmployees(employee);
    }

    @GetMapping("/employees")
    public List<EmployeeResponse> getEmployees() {
        return employeeServiceImpl.getEmployees();
    }

    @GetMapping(value = "/employees", params = {"page","size"})
    public List<EmployeeResponse> pagingQueryEmployees(@PageableDefault(size = 2) Pageable pageable, @RequestParam(defaultValue = "false", required = false) boolean unpaged) {
        return employeeServiceImpl.pagingQueryEmployees(pageable);
    }

    @GetMapping(value = "/employees", params = "gender")
    public List<Employee> getMaleEmployees(@RequestParam("gender") String gender){
        return employeeServiceImpl.getMaleEmployees(gender);
    }
}

