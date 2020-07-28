package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();

    Company getCompany(int id);

    List<Employee> getAllEmployeesOfCompany(int id);

    List<Company> pagingQueryCompanies(int page, int pageSize);

    void addCompany(Company company);
}