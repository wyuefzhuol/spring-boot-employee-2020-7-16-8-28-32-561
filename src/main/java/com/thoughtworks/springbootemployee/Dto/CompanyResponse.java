package com.thoughtworks.springbootemployee.Dto;

public class CompanyResponse {
    int companyID;
    String name;

    public CompanyResponse() {
    }

    public CompanyResponse(int companyID, String name) {
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
