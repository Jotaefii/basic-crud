package com.crud;

import com.crud.database.repository.EmployeeRepository;
import com.crud.database.repository.EmployeeRepositoryImpl;
import com.crud.service.EmployeeService;
import com.crud.service.EmployeeServiceImpl;
import com.crud.view.EmployeeMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        EmployeeRepository repository = new EmployeeRepositoryImpl();

        EmployeeService service = new EmployeeServiceImpl(repository);

        EmployeeMenu.start(sc, service);
    }
}