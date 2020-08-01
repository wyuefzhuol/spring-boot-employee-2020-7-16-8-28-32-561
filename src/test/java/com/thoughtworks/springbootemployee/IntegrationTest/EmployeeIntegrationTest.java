package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.util.ConstantInterface;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Rollback
public class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void should_return_employee_response_when_add_employees_given_employee() throws Exception {
        Company company = new Company("oocl");
        companyRepository.save(company);
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ConstantInterface.EMPLOYEE_REQUEST_JSON_PAY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Eric"));
    }

    @Test
    void should_return_employee_responses_when_get_employees_given_employees() throws Exception {
        int companyId = 1;
        Company company = new Company("oocl");
        companyRepository.save(company);
        Company companyWhichEmployeeWillJoin = companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
        Employee employee = new Employee("Eric", "male", 18, companyWhichEmployeeWillJoin);
        employeeRepository.save(employee);
        mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value("Eric"))
                .andExpect(jsonPath("[0].companyName").value("oocl"));
    }

    @Test
    void should_return_2employee_responses_when_paging_query_employees_given_3employees() throws Exception {
        int companyId = 1, page = 0, pageSize = 1;
        Company company = new Company("oocl");
        companyRepository.save(company);
        Company companyWhichEmployeeWillJoin = companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
        Employee employee1 = new Employee("Eric", "male", 18, companyWhichEmployeeWillJoin);
        Employee employee2 = new Employee("Eric", "male", 20, companyWhichEmployeeWillJoin);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        mockMvc.perform(get("/employees?page="+page+"&size="+pageSize)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
