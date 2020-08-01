package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.Dto.CompanyRequest;
import com.thoughtworks.springbootemployee.Dto.CompanyResponse;
import com.thoughtworks.springbootemployee.Dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyServiceImpl companyService;

    @Test
    void should_return_specify_company_when_get_company_given_company_id() {
        //given
        int companyID = 1;
        Company company = new Company();
        company.setCompanyID(companyID);
        Mockito.when(companyRepository.findById(companyID)).thenReturn(java.util.Optional.of(company));

        //when
        Company specifyCompany = companyService.getCompany(companyID);

        //then
        assertEquals(companyID, specifyCompany.getCompanyID());
    }

    @Test
    void should_return_companies_when_get_all_companies_given_a_company_repository() {
        //given
        List<Company> companies = new ArrayList<>();
        for(int index = 0; index < 2; index++){
            companies.add(new Company());
        }
        Mockito.when(companyRepository.findAll()).thenReturn(companies);

        //when
        List<Company> companiesReslut = companyService.getAllCompanies();

        //then
        assertEquals(companies, companiesReslut);

    }

    @Test
    void should_return_new_company_response_when_add_company_given_a_new_company() {
        //given
        Company company = new Company("tw");
        CompanyRequest companyRequest = new CompanyRequest(1,"tw");

        //when
        CompanyResponse companyResponse = companyService.addCompany(companyRequest);

        //then
        assertNotNull(companyResponse);
    }
}
