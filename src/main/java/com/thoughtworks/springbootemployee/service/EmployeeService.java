package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    void addEmployee(Employee employee);

    Page<Employee> getEmployees(Pageable pageable);

    List<Employee> getEmployeeById(int id);

    void updateEmployee(Employee employee);

    void deleteEmployeeById(int id);

    List<Employee> getEmployeesByGender(String gender);
}
