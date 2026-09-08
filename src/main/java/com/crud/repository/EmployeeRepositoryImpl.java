package com.crud.repository;

import com.crud.entities.Department;
import com.crud.entities.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final Connection connection;

    public EmployeeRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Employee employee) {
        String sql = """
                INSERT INTO employees (name, email, salary, department_id)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setBigDecimal(3, employee.getSalary());
            ps.setLong(4, employee.getDepartment().getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error ao salvar funcionario(a): " + e);
        }
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();

        String sql = """
            SELECT 
            e.id, 
            e.name, 
            e.email, 
            e.salary, 
            d.id AS department_id,
            d.name AS department_name
            FROM employees e 
            JOIN departments d 
                ON e.department_id = d.id
            ORDER BY e.id ASC
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet result = ps.executeQuery()) {

                while (result.next()) {
                    Department department = new Department(
                            result.getLong("department_id"),
                            result.getString("department_name")
                    );

                    Employee employee = new Employee(
                            result.getLong("id"),
                            result.getString("name"),
                            result.getString("email"),
                            result.getBigDecimal("salary"),
                            department
                    );

                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error ao listar funcionarios: " + e);
        }
        return employees;
    }

    @Override
    public Employee findById(Long id) {
        String sql = """
                SELECT 
                e.id, 
                e.name, 
                e.email,
                e.salary, 
                d.id AS department_id,
                d.name AS department_name
                FROM employees e
                JOIN departments d
                    ON e.department_id = d.id
                WHERE e.id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet result = ps.executeQuery()) {

                if (result.next()) {
                    Department department = new Department(
                            result.getLong("department_id"),
                            result.getString("department_name")
                    );

                    return new Employee(
                            result.getLong("id"),
                            result.getString("name"),
                            result.getString("email"),
                            result.getBigDecimal("salary"),
                            department
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error ao buscar funcionario(a): " + e);
        }
        return null;
    }

    @Override
    public void update(Employee employee, Long id) {
        String sql = """
                UPDATE employees
                SET name = ?, email = ?, salary = ?, department_id = ?
                WHERE id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setBigDecimal(3, employee.getSalary());
            ps.setLong(4, employee.getDepartment().getId());
            ps.setLong(5, employee.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error ao editar funcionario: " + e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = """
                DELETE FROM employees
                WHERE id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error ao excluir funcionario(a): " + e);
        }
    }
}
