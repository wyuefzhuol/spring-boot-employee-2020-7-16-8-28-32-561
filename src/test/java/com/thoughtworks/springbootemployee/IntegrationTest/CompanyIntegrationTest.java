package com.thoughtworks.springbootemployee.IntegrationTest;

import com.jayway.jsonpath.JsonPath;
import com.thoughtworks.springbootemployee.Dto.CompanyResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        Company company = new Company("tw");
        companyRepository.save(company);
        mockMvc.perform(get("/companies/1")
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
}
