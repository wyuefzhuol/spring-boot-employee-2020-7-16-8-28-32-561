package com.thoughtworks.springbootemployee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.springbootemployee.Dto.EmployeeResponse;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String gender;
    private int age;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

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


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee() {
    }

    public Employee(String name, String gender, int age, Company company) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.company = company;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EmployeeResponse) {
            EmployeeResponse employee = (EmployeeResponse) obj;
            return employee.getId() == this.id;
        }
        return false;
    }
}
