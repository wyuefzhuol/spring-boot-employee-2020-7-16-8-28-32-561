package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Dto.CompanyRequest;
import com.thoughtworks.springbootemployee.Dto.CompanyResponse;
import com.thoughtworks.springbootemployee.Dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {

    List<CompanyResponse> getAllCompanies();

    CompanyResponse getSpecifyCompany(int id);

    List<EmployeeResponse> getAllEmployeesByCompanyId(int companyId);

    List<CompanyResponse> pagingQueryCompanies(Pageable pageable);

    CompanyResponse addCompany(CompanyRequest companyRequest);

    void deleteCompanyById(int companyId);

    CompanyResponse updateCompany2(int id, CompanyRequest companyRequest);
}
