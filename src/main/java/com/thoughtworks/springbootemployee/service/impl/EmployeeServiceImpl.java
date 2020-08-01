package com.thoughtworks.springbootemployee.service.impl;

import com.thoughtworks.springbootemployee.Dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.Dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final CompanyRepository companyRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    private List<Employee> employees = new ArrayList<>();

    public Employee getSpecificEmployee(int id) {
        return employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    public void deleteEmployees(int id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getMaleEmployees(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public void updateEmployees(Employee employee) {
        employeeRepository.save(employee);
    }

    public Page<Employee> pagingQueryEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public List<EmployeeResponse> getEmployees() {
        return employeeRepository.findAll().stream().map(employee -> {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setId(employee.getId());
            employeeResponse.setName(employee.getName());
            employeeResponse.setGender(employee.getGender());
            return employeeResponse;
        }).collect(Collectors.toList());
    }

    public EmployeeResponse addEmployees(EmployeeRequest employeeRequest) {
        Optional<Company> company = companyRepository.findById(employeeRequest.getCompanyId());
        if (company.isPresent()) {
            Employee employee = new Employee();
            employee.setName(employeeRequest.getName());
            employee.setGender(employeeRequest.getGender());
            employee.setAge(employeeRequest.getAge());
            employee.setCompany(company.get());
            Employee employeeResult = employeeRepository.save(employee);
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setName(employeeResult.getName());
            employeeResponse.setGender(employeeResult.getGender());
            employeeResponse.setCompanyName(employeeResult.getCompany().getName());
            return employeeResponse;
        }
        return null;
    }

    public List<EmployeeResponse> pagingQueryEmployees2(Pageable pageable) {
        return null;
    }
}
