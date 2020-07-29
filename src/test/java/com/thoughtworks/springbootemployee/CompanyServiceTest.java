package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);

    @InjectMocks
    CompanyServiceImpl companyService;

    @Test
    void should_return_specify_company_when_get_company_given_company_id() {
        //given
        int companyID = 1;
        Company company = new Company();
        company.setId(1);
        Mockito.when(companyRepository.getCompany(companyID)).thenReturn(company);

        //when
        Company specifyCompany = companyService.getCompany(companyID);

        //then
        assertEquals(companyID, specifyCompany.getId());
    }


}
