package com.thoughtworks.springbootemployee.Dto;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Validated
public class CompanyRequest {
    int companyID;
    @NotBlank
    @Size(min = 1, max = 20)
    String name;

    public CompanyRequest() {
    }

    public CompanyRequest(int companyID, @NotBlank @Size(min = 1, max = 20) String name) {
        this.companyID = companyID;
        this.name = name;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
