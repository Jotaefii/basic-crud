package com.crud.service;

import com.crud.database.entities.Employee;

import java.util.List;

public interface EmployeeService {

    void save(Employee employee);
    List<Employee> findAll();
    Employee findById(Long id);
    void update(Employee employee, Long id);
    void delete(Long id);
}
