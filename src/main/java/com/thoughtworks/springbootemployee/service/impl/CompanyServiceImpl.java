package com.thoughtworks.springbootemployee.service.impl;

import com.thoughtworks.springbootemployee.Dto.CompanyRequest;
import com.thoughtworks.springbootemployee.Dto.CompanyResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
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

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

//    @Override
//    public Company getCompany(int id) {
//        return companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
//    }

    @Override
    public List<Employee> getAllEmployeesOfCompany(int id) {
//        Company company = getCompany(id);
//        return company.getEmployeeList();
        Optional<Company> byId = companyRepository.findById(id);
        if(byId.isPresent()){
            return byId.get().getEmployeeList();
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Company> pagingQueryCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable).getContent();
    }

//    @Override
//    public void deleteTheCompanyAllInfo(int id) {
//        Company company = getSpecifyCompany(id);
//        employeeRepository.findAll().stream()
//                .filter(employee -> employee.getCompany().getCompanyID()==company.getCompanyID())
//                .peek(employee -> employee.setCompany(null))
//                .collect(Collectors.toList());
//        companyRepository.deleteById(id);
//    }

    @Override
    public void updateCompany(Company company) {
        companyRepository.save(company);
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

    public List<CompanyResponse> getAllCompanies2() {
        List<CompanyResponse> companyResponses = new ArrayList<>();
        CompanyMapper companyMapper = new CompanyMapper();
        List<Company> companies = companyRepository.findAll();
        for(Company companyEntity : companies){
            companyResponses.add(companyMapper.companyToResponse(companyEntity));
        }
        return companyResponses;
    }
}
