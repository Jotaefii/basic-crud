package com.crud;

import com.crud.database.ConnectionFactory;
import com.crud.repository.EmployeeRepository;
import com.crud.repository.EmployeeRepositoryImpl;
import com.crud.service.EmployeeService;
import com.crud.service.EmployeeServiceImpl;
import com.crud.view.EmployeeMenu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        Connection connection = ConnectionFactory.getConnection();

        EmployeeRepository repository = new EmployeeRepositoryImpl(connection);

        EmployeeService service = new EmployeeServiceImpl(repository);

        EmployeeMenu.start(sc, service);
    }
}