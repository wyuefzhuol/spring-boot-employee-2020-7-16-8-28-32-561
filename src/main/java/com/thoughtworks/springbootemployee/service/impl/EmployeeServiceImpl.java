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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final CompanyRepository companyRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    public void deleteEmployees(int id) {
        employeeRepository.deleteById(id);
    }

    public List<EmployeeResponse> getEmployees() {
        return employeeRepository.findAll().stream().map(employee -> {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setId(employee.getId());
            employeeResponse.setName(employee.getName());
            employeeResponse.setGender(employee.getGender());
            if (employee.getCompany() != null) {
                employeeResponse.setCompanyName(employee.getCompany().getName());
            }
            return employeeResponse;
        }).collect(Collectors.toList());
    }

    public EmployeeResponse addEmployees(EmployeeRequest employeeRequest) {
        Company company = companyRepository.findById(employeeRequest.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        Employee employee = new Employee();
        employee.setName(employeeRequest.getName());
        employee.setGender(employeeRequest.getGender());
        employee.setAge(employeeRequest.getAge());
        employee.setCompany(company);
        Employee employeeResult = employeeRepository.save(employee);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employeeResult.getId());
        employeeResponse.setName(employeeResult.getName());
        employeeResponse.setGender(employeeResult.getGender());
        employeeResponse.setCompanyName(employeeResult.getCompany().getName());
        return employeeResponse;
    }

    public List<EmployeeResponse> pagingQueryEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable).getContent().stream().map(employee -> {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setId(employee.getId());
            employeeResponse.setName(employee.getName());
            employeeResponse.setGender(employee.getGender());
            if (employee.getCompany() != null) {
                employeeResponse.setCompanyName(employee.getCompany().getName());
            }
            return employeeResponse;
        }).collect(Collectors.toList());
    }

    public EmployeeResponse getSpecificEmployee(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employee.getId());
        employeeResponse.setName(employee.getName());
        employeeResponse.setGender(employee.getGender());
        if (employee.getCompany() != null) {
            employeeResponse.setCompanyName(employee.getCompany().getName());
        }
        return employeeResponse;
    }

    public List<EmployeeResponse> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender).stream().map(employee -> {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setId(employee.getId());
            employeeResponse.setName(employee.getName());
            employeeResponse.setGender(employee.getGender());
            if (employee.getCompany() != null) {
                employeeResponse.setCompanyName(employee.getCompany().getName());
            }
            return employeeResponse;
        }).collect(Collectors.toList());
    }

    public EmployeeResponse updateEmployee(EmployeeRequest employeeRequest) {
        Company company = companyRepository.findById(employeeRequest.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        Employee employee = new Employee();
        employee.setName(employeeRequest.getName());
        employee.setGender(employeeRequest.getGender());
        employee.setAge(employeeRequest.getAge());
        employee.setCompany(company);
        Employee employeeResult = employeeRepository.save(employee);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employeeResult.getId());
        employeeResponse.setName(employeeResult.getName());
        employeeResponse.setGender(employeeResult.getGender());
        employeeResponse.setCompanyName(employeeResult.getCompany().getName());
        return employeeResponse;
    }
}
