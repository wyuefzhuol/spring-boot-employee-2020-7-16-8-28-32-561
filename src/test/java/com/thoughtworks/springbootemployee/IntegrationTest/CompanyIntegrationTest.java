package com.thoughtworks.springbootemployee.IntegrationTest;

import com.jayway.jsonpath.JsonPath;
import com.thoughtworks.springbootemployee.Dto.CompanyResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    public void teardown(){
        companyRepository.deleteAll();
    }

    @Test
    void should_return_company_responses_when_get_companies_given_companies() throws Exception {
        Company company = new Company("tw");
        companyRepository.save(company);
        mockMvc.perform(get("/companies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value("tw"));
    }

    @Test
    void should_return_new_company_when_add_company_given_new_company() throws Exception {
        //given
        String companyRequestJsonPay = "{\n" +
                "        \"name\": \"tw\"\n" +
                "    }";
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyRequestJsonPay))
                .andExpect(status().isOk());

        //when
        List<Company> companies = companyRepository.findAll();

        //then
        assertEquals(1, companies.size());

        assertEquals("tw", companies.get(0).getName());
    }

    @Test
    void should_return_a_specific_company_when_get_specific_company_given_a_company_id() throws Exception {
        //given
        Company company = companyRepository.save(new Company("tw"));
        mockMvc.perform(get(String.format("/companies/%d", company.getCompanyID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("tw"));
    }

    @Test
    void should_return_200_when_delete_a_company_given_companyid() throws Exception {
        //given
        Company company = new Company("tw");
        companyRepository.save(company);

        //when
        mockMvc.perform(delete("/companies/1"))
                .andExpect(status().is2xxSuccessful());

        //then
        List<Company> companies = companyRepository.findAll();
        assertEquals(0,companies.size());
    }

    @Test
    void should_return_employees_response_by_companyId_when_get_all_employees_by_company_id_given_a_company_id() throws Exception {
        //given
        Company company = companyRepository.save(new Company("tw"));
        Employee employee = new Employee("Alice", "female", 18, company);
        employeeRepository.save(employee);

        //when
        mockMvc.perform(get("/companies/"+ company.getCompanyID() +"/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
