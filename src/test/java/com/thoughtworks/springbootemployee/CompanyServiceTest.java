package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.Dto.CompanyRequest;
import com.thoughtworks.springbootemployee.Dto.CompanyResponse;
import com.thoughtworks.springbootemployee.Dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.GlobalException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyServiceImpl companyService;

    @AfterEach
    public void tearDown(){
        companyRepository.deleteAll();
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

    @Test
    void should_return_specify_company_response_when_get_a_specify_company_given_a_company_id() {
        //given
        int companyID = 1;
        Company company = new Company("tw");
        Mockito.when(companyRepository.findById(companyID)).thenReturn(Optional.of(company));

        //when
        CompanyResponse companyResponse = companyService.getSpecifyCompany(companyID);

        //then
        assertEquals(companyResponse.getName(), Optional.of(company).get().getName());
    }

    @Test
    void should_return_success_when_delete_company_given_a_company_id() {
        //given
        int companyId = 1;
        Company company = new Company();
        Mockito.when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        //when
        companyService.deleteCompanyById(companyId);

        //then
        assertDoesNotThrow(() ->companyService.deleteCompanyById(companyId));
    }

    @Test
    void should_return_company_responses_when_get_all_companies() {
        //given
        Company company = new Company("tw");
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        Mockito.when(companyRepository.findAll()).thenReturn(companies);

        //when
        List<CompanyResponse> companyResponses = companyService.getAllCompanies();

        //then
        assertEquals(companies.size(),companyResponses.size());
    }

    @Test
    void should_return_a_new_company_response_when_update_company_given_a_new_company_request_and_old_company() {
        //given
        int companyId = 1;
        Company oldCompany = new Company("tw");
        CompanyRequest newCompanyRequest = new CompanyRequest(companyId,"oocl");
        Mockito.when(companyRepository.findById(companyId)).thenReturn(Optional.of(oldCompany));

        //when
        CompanyResponse newCompanyResponse = companyService.updateCompany2(companyId, newCompanyRequest);

        //then
        assertNotNull(newCompanyResponse);
        assertEquals("oocl", newCompanyResponse.getName());
    }
}
