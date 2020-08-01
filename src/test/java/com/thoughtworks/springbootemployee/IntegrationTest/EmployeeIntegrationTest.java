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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void should_return_male_employee_response_when_get_employee_by_gender_given_1male_1female_employees() throws Exception {
        int companyId = 1;
        String gender = "male";
        Company company = new Company("oocl");
        companyRepository.save(company);
        Company companyWhichEmployeeWillJoin = companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
        Employee maleEmployee = new Employee("Eric", "male", 18, companyWhichEmployeeWillJoin);
        Employee femaleEmployee = new Employee("Alice", "female", 18, companyWhichEmployeeWillJoin);
        employeeRepository.save(maleEmployee);
        employeeRepository.save(femaleEmployee);
        mockMvc.perform(get("/employees?gender="+gender)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].name").value("Eric"))
                .andExpect(jsonPath("[0].gender").value("male"));
    }

    @Test
    void should_return_new_employee_with_company_oocl_when_update_employee_given_old_employee_with_company_tw() throws Exception {
        int companyId = 1;
        Company twCompany = new Company("tw");
        companyRepository.save(twCompany);
        Company ooclCompany = new Company("oocl");
        companyRepository.save(ooclCompany);
        Company companyWhichEmployeeWillJoin = companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
        Employee Employee = new Employee("Eric", "male", 18, companyWhichEmployeeWillJoin);
        employeeRepository.save(Employee);
        mockMvc.perform(put("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ConstantInterface.NEW_EMPLOYEE_REQUEST_JSON_PAY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Eric"))
                .andExpect(jsonPath("companyName").value("oocl"));
    }
}
