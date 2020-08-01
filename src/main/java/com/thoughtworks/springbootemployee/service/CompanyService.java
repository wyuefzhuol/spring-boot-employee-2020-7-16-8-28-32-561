package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Dto.CompanyRequest;
import com.thoughtworks.springbootemployee.Dto.CompanyResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();

    CompanyResponse getSpecifyCompany(int id);

    List<Employee> getAllEmployeesOfCompany(int id);

    List<Company> pagingQueryCompanies(Pageable pageable);

    CompanyResponse addCompany(CompanyRequest companyRequest);

//    void deleteTheCompanyAllInfo(int id);

    void updateCompany(Company company);
}
