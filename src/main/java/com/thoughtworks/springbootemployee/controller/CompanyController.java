package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Dto.CompanyRequest;
import com.thoughtworks.springbootemployee.Dto.CompanyResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {

//    @Autowired
//    private CompanyServiceImpl companyService;
    private final CompanyServiceImpl companyService;

    public CompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies/{id}")
    private CompanyResponse getCompany(@PathVariable("id") int id) {
        return companyService.getSpecifyCompany(id);
    }

    @GetMapping("/companies/{id}/employees")
    private List<Employee> getAllEmployeesOfCompany(@PathVariable("id") int id) {
        return companyService.getAllEmployeesOfCompany(id);
    }

    @GetMapping("/companies")
    private List<Company> queryCompanies(@PageableDefault(size = 2) Pageable pageable,@RequestParam(defaultValue = "false",required = false) boolean unpaged) {
        if(unpaged){
            return companyService.getAllCompanies();
        }
        return companyService.pagingQueryCompanies(pageable);
    }

    @PostMapping("/companies")
    private CompanyResponse addCompany(@RequestBody() CompanyRequest companyRequest) {
        return companyService.addCompany(companyRequest);
    }

//    @DeleteMapping("/companies/{id}")
//    private void deleteTheCompanyAllInfo(@PathVariable("id") int id) {
//        companyService.deleteTheCompanyAllInfo(id);
//    }

    @PutMapping("/companies")
    public void updateCompany(@RequestBody Company company) {
        companyService.updateCompany(company);
    }
}
