package com.thoughtworks.springbootemployee.service.impl;

import com.thoughtworks.springbootemployee.Dto.CompanyRequest;
import com.thoughtworks.springbootemployee.Dto.CompanyResponse;
import com.thoughtworks.springbootemployee.Dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeesMapper;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final EmployeeRepository employeeRepository;
//    @Autowired
//    private CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, EmployeeRepository employeeRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public CompanyResponse addCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest,company);
        companyRepository.save(company);
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        return companyResponse;
    }

    public CompanyResponse getSpecifyCompany(int id){
        CompanyResponse companyResponse = new CompanyResponse();
        Company company = companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
        BeanUtils.copyProperties(company, companyResponse);
        return companyResponse;
    }

    public void deleteCompanyById(int companyId) {
        Optional<Company> fetchedCompany = companyRepository.findById(companyId);
        if (!fetchedCompany.isPresent()){
            throw new CompanyNotFoundException();
        }
        companyRepository.delete(fetchedCompany.get());
    }

    public List<CompanyResponse> getAllCompanies() {
        List<CompanyResponse> companyResponses = new ArrayList<>();
        CompanyMapper companyMapper = new CompanyMapper();
        List<Company> companies = companyRepository.findAll();
        for(Company companyEntity : companies){
            companyResponses.add(companyMapper.companyToResponse(companyEntity));
        }
        return companyResponses;
    }

    public CompanyResponse updateCompany2(int companyId, CompanyRequest newCompanyRequest) {
        CompanyMapper companyMapper = new CompanyMapper();
        Optional<Company> company = companyRepository.findById(companyId);
        if(!company.isPresent()){
            throw new CompanyNotFoundException();
        }
        Company newCompany = companyMapper.requestToCompany(newCompanyRequest);
        companyRepository.save(newCompany);
        return companyMapper.companyToResponse(newCompany);
    }

    public List<EmployeeResponse> getAllEmployeesByCompanyId(int companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if(!company.isPresent()){
            throw new CompanyNotFoundException();
        }
        List<Employee> employees = company.get().getEmployeeList();
        return employees.stream().map(employee -> {
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

    public List<CompanyResponse> pagingQueryCompanies(Pageable pageable) {
        CompanyMapper companyMapper = new CompanyMapper();
        List<CompanyResponse> companyResponses = new ArrayList<>();
        List<Company> companies = companyRepository.findAll(pageable).getContent();
        for(Company company: companies){
            companyResponses.add(companyMapper.companyToResponse(company));
        }
        return companyResponses;
    }
}
