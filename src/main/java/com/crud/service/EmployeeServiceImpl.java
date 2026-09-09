package com.crud.service;

import com.crud.database.entities.Employee;
import com.crud.database.repository.EmployeeRepository;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void update(Employee employee, Long id) {
        employeeRepository.update(employee, id);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.delete(id);
    }
}
