package com.crud.database.repository;

import com.crud.config.JPAUtil;
import com.crud.database.entities.Employee;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Override
    public void save(Employee employee) {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<Employee> findAll() {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return em.createQuery("SELECT e FROM Employee e ORDER BY e.id ASC  ", Employee.class).getResultList();
        }
    }

    @Override
    public Employee findById(Long id) {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return em.find(Employee.class, id);
        }
    }

    @Override
    public void update(Employee employee, Long id) {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();

            Employee employee1 = em.find(Employee.class, id);

            if (employee1 != null) {
                employee1.setName(employee.getName());
                employee1.setEmail(employee.getEmail());
                employee1.setSalary(employee.getSalary());
                employee1.setDepartment(employee.getDepartment());
            }

            em.getTransaction().commit();
        }
    }

    @Override
    public void delete(Long id) {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();

            Employee employee = em.find(Employee.class, id);

            if (employee != null) {
                em.remove(employee);
            }

            em.getTransaction().commit();
        }
    }
}
