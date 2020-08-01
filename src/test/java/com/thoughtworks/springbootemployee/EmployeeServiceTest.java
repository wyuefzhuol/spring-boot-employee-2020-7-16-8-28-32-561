package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.Dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.Dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Test
    void should_return_employee_responses_when_get_employees_given_employees() {
        //given
        Employee employee = new Employee("Eric", "male", 18, null);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        //when
        List<EmployeeResponse> employeeResponses = employeeService.getEmployees();

        //then
        assertEquals(1, employeeResponses.size());
        assertEquals(employee, employeeResponses.get(0));
    }

    @Test
    void should_return_new_employee_when_add_employee_given_new_employee() {
        //given
        Company company = new Company("oocl");
        Employee employee = new Employee("Eric", "male", 20, company);
        EmployeeRequest employeeRequest = new EmployeeRequest("Eric", "male", 20, 1);
        Mockito.when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        Mockito.when(employeeRepository.save(any())).thenReturn(employee);

        //when
        EmployeeResponse employeeResponse = employeeService.addEmployees(employeeRequest);

        //then
        assertEquals(employee, employeeResponse);
    }

    @Test
    void should_return_company_not_found_exception_when_add_employee_given_new_employee_with_no_company() {
        //given
        Employee employee = new Employee("Eric", "male", 20, null);
        EmployeeRequest employeeRequest = new EmployeeRequest("Eric", "male", 20, 1);
        Mockito.when(companyRepository.findById(1)).thenReturn(Optional.empty());

        //when
        EmployeeResponse employeeResponse = employeeService.addEmployees(employeeRequest);

        //then
        assertNull(employeeResponse);
    }

    @Test
    void should_return_employee_response_when_paging_query_employees_given_pageable() {
        //given
        List<Employee> employees = new ArrayList<>();
        int page = 0, pageSize = 2;
        for (int i = 0; i < pageSize; i++) {
            employees.add(new Employee("Eric", "male", 18, null));
        }
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Employee> employeesPage = new PageImpl<>(employees, pageable, employees.size());
        Mockito.when(employeeRepository.findAll(pageable)).thenReturn(employeesPage);

        //when
        List<EmployeeResponse> employeeResponses = employeeService.pagingQueryEmployees(pageable);

        //then
        assertEquals(pageSize, employeeResponses.size());
    }
}
