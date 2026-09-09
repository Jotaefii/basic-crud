package com.crud.database.repository;

import com.crud.database.entities.Employee;

import java.util.List;

public interface EmployeeRepository {

    void save(Employee employee);
    List<Employee> findAll();
    Employee findById(Long id);
    void update(Employee employee, Long id);
    void delete(Long id);
}
