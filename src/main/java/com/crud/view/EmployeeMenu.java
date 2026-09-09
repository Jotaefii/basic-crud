package com.crud.view;

import com.crud.database.entities.Department;
import com.crud.database.entities.Employee;
import com.crud.service.EmployeeService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {

    public static void start(Scanner sc, EmployeeService service) {

        while (true) {
            System.out.println("MENU");
            System.out.println("---------------------------------------------");
            System.out.println("1 - Cadastrar funcionário");
            System.out.println("2 - Listar funcionários");
            System.out.println("3 - Buscar funcionário");
            System.out.println("4 - Editar funcionário");
            System.out.println("5 - Excluir funcionário");
            System.out.println("0 - Sair");

            System.out.println("---------------------------------------------");
            System.out.print("Opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.println();
                    System.out.println("CADASTRO DE FUNCIONÁRIO");
                    System.out.println("---------------------------------------------");

                    System.out.print("Nome: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.next();
                    System.out.print("Salário: ");
                    BigDecimal salary = sc.nextBigDecimal();

                    System.out.println();
                    System.out.println("ESCOLHA O DEPARTAMENTO:");
                    System.out.println("---------------------------------------------");
                    System.out.println("1 - Tecnologia da Informação");
                    System.out.println("2 - Recursos Humanos");

                    System.out.print("Opção: ");
                    long departmentId = sc.nextLong();

                    Department department = new Department();
                    department.setId(departmentId);

                    Employee employee = new Employee(null, name, email, salary, department);

                    service.save(employee);
                    System.out.println("---------------------------------------------");
                    System.out.println("Funcionário(a) adicionado com sucesso!");
                    System.out.println();
                }

                case 2 -> {
                    System.out.println();
                    System.out.println("LISTA DE FUNCIONÁRIOS");
                    System.out.println("---------------------------------------------");

                    List<Employee> employees = service.findAll();
                    employees.forEach(
                            s -> System.out.println(
                                    s.getId()
                                            + " | " + s.getName()
                                            + " | " + s.getEmail()
                                            + " | " + s.getSalary()
                                            + " | " + s.getDepartment().getId()
                                            + " - " + s.getDepartment().getName()
                            )
                    );

                    System.out.println("---------------------------------------------");
                    System.out.println();
                }

                case 3 -> {
                    System.out.print("Busque funcionário(a) pelo id: ");
                    long id = sc.nextLong();

                    System.out.println();
                    System.out.println("FUNCIONÁRIO");
                    System.out.println("---------------------------------------------");

                    Employee employee = service.findById(id);

                    System.out.println(employee);
                    System.out.println("---------------------------------------------");
                    System.out.println();
                }

                case 4 -> {
                    System.out.print("Busque funcionário(a) pelo id: ");
                    long id = sc.nextLong();
                    sc.nextLine();

                    System.out.println();
                    System.out.println("FUNCIONÁRIO");
                    System.out.println("---------------------------------------------");

                    Employee employee = service.findById(id);

                    System.out.println(employee);
                    System.out.println("---------------------------------------------");

                    System.out.println("1 - Alterar valores");
                    System.out.println("2 - Alterar departamento");
                    System.out.println("0 - Voltar");
                    System.out.print("Opção: ");
                    int opcaoUpdate = sc.nextInt();
                    sc.nextLine();

                    if (opcaoUpdate == 0) {
                        break;
                    }

                    if (opcaoUpdate == 1) {
                        System.out.print("Novo nome: ");
                        employee.setName(sc.nextLine());
                        System.out.print("Novo email: ");
                        employee.setEmail(sc.next());
                        System.out.print("Novo salário: ");
                        employee.setSalary(sc.nextBigDecimal());

                        service.update(employee, id);

                        System.out.println("---------------------------------------------");
                        System.out.println("Funcionário editado com sucesso!");
                        System.out.println();
                    } else {
                        System.out.println();
                        System.out.println("ESCOLHER DEPARTAMENTO");
                        System.out.println("---------------------------------------------");
                        System.out.println("1 - Tecnologia da Informação");
                        System.out.println("2 - Recursos Humanos");
                        System.out.println("0 - Voltar");

                        System.out.print("Opção: ");
                        Long department = sc.nextLong();

                        if (department == 0) {
                            break;
                        }

                        if (department.equals(employee.getDepartment().getId())) {
                            System.out.println("O funcionário ja pertence a esse departamento!");
                        } else {
                            Department department1 = new Department();
                            department1.setId(department);

                            employee.setDepartment(department1);

                            service.update(employee, id);
                            System.out.println("---------------------------------------------");
                            System.out.println("Funcionário editado com sucesso!");
                            System.out.println();
                        }
                    }
                }

                case 5 -> {
                    System.out.print("Busque funcionário(a) pelo id: ");
                    long id = sc.nextLong();

                    System.out.println();
                    System.out.println("FUNCIONÁRIO");
                    System.out.println("---------------------------------------------");

                    Employee employee = service.findById(id);

                    System.out.println(employee);
                    System.out.println("---------------------------------------------");

                    System.out.print("Tem certeza que deseja excluir esse funcionário(a) - (S/N): ");
                    char escolha = sc.next().charAt(0);

                    if (escolha == 'N' || escolha == 'n') {
                        System.out.println("---------------------------------------------");
                        System.out.println("Exclusão cancelada!");
                        System.out.println();
                        break;
                    }

                    service.delete(id);
                    System.out.println("---------------------------------------------");
                    System.out.println("Funcionário(a) excluído com sucesso!");
                    System.out.println();
                }

                case 0 -> {
                    System.out.println("Finalizando...");
                    return;
                }

                default -> System.out.println("Opção inválida!");
            }
        }
    }
}
