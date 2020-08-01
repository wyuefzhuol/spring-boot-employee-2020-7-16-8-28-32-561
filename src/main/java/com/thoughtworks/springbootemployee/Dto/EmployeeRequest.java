package com.thoughtworks.springbootemployee.Dto;


import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Validated
public class EmployeeRequest {
    private int id;
    @NotBlank
    private String name;
    @Size(min = 1, max = 10)
    private String gender;
    private int age;
    private int companyId;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, String gender, int age, int companyId) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.companyId = companyId;
    }

    public EmployeeRequest(int id, String name, String gender, int age, int companyId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
