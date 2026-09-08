package com.crud.entities;

import java.math.BigDecimal;

public class Employee {

    private Long id;
    private String name;
    private String email;
    private BigDecimal salary;

    private Department department;

    public Employee() {
    }

    public Employee(Long id, String name, String email, BigDecimal salary, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Id: " + getId()
                + "\nNome: " + getName()
                + "\nEmail: " + getEmail()
                + "\nSalário: " + getSalary()
                + "\nDepartamento: " + getDepartment().getId() + " - " + getDepartment().getName();
    }
}
