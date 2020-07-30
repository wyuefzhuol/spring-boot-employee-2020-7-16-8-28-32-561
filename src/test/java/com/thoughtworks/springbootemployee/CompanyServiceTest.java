package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.impl.CompanyServiceImpl;
import javafx.beans.binding.When;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyServiceImpl companyService;

    @Test
    void should_return_2companies_when_get_companies_given_2companies(){
        //given
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            companies.add(new Company());
        }
        Mockito.when(companyRepository.findAll()).thenReturn(companies);

        //when
        List<Company> companiesResult = companyService.getCompanies();

        //then
        assertEquals(2,companiesResult.size());
    }

    @Test
    void should_return_0companies_when_get_companies_given_0companies() {
        //given
        List<Company> companies = new ArrayList<>();
        Mockito.when(companyRepository.findAll()).thenReturn(companies);

        //when
        List<Company> companiesResult = companyService.getCompanies();

        //then
        assertEquals(0, companiesResult.size());
    }

    @Test
    void should_return_0companies_when_get_company_by_id_given_company_id1_and_companies_do_not_have_company_with_id1() {
        //given
        int companyId = 1;
        Mockito.when(companyRepository.findAllById(Collections.singleton(companyId))).thenReturn(null);

        //when
        List<Company> companyResult = companyService.getCompanyById(companyId);

        //then
        assertNull(companyResult);
    }

    @Test
    void should_return_employees_when_get_employee_of_company_given_company_id1() {
        //given
        int companyId = 1;
        List<Company> companies = new ArrayList<>();
        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        companies.add(company);
        Mockito.when(companyRepository.findAllById(Collections.singleton(companyId))).thenReturn(companies);

        //when
        List<Employee> employees = companyService.getEmployeeOfCompany(companyId);

        //then
        assertNotNull(employees);
    }
}
