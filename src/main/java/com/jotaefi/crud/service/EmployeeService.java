package com.jotaefi.crud.service;

import com.jotaefi.crud.dto.request.EmployeeCreateDTO;
import com.jotaefi.crud.dto.response.EmployeeResponseDTO;
import com.jotaefi.crud.dto.request.EmployeeUpdateDTO;
import com.jotaefi.crud.entity.DepartmentEntity;
import com.jotaefi.crud.entity.EmployeeEntity;
import com.jotaefi.crud.exception.BadRequestException;
import com.jotaefi.crud.exception.NotFoundException;
import com.jotaefi.crud.repository.DepartmentRepository;
import com.jotaefi.crud.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeCreateDTO employeeRequest) {
        DepartmentEntity departmentEntityId = departmentRepository.findById(employeeRequest.departmentId())
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        EmployeeEntity e = employeeRepository.findByEmail(employeeRequest.email())
                .orElse(null);

        if (e != null) {
            throw new BadRequestException("Já possui um funcionario(a) com este email");
        }

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .name(employeeRequest.name())
                .email(employeeRequest.email())
                .salary(employeeRequest.salary())
                .department(departmentEntityId)
                .build();

        EmployeeEntity salvo = employeeRepository.save(employeeEntity);

        return EmployeeResponseDTO.builder()
                .id(salvo.getId())
                .name(salvo.getName())
                .email(salvo.getEmail())
                .salary(salvo.getSalary())
                .departmentName(salvo.getDepartment().getName())
                .registrationDate(salvo.getRegistrationDate())
                .build();
    }

    public List<EmployeeResponseDTO> findAll() {
        return employeeRepository.findAll().stream()
                .map(e -> EmployeeResponseDTO.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .email(e.getEmail())
                        .salary(e.getSalary())
                        .departmentName(e.getDepartment().getName())
                        .registrationDate(e.getRegistrationDate())
                        .build()
                )
                .toList();
    }

    public EmployeeResponseDTO findEmployeeById(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .departmentName(employee.getDepartment().getName())
                .registrationDate(employee.getRegistrationDate())
                .build();
    }

    public List<EmployeeResponseDTO> findByDepartmentId(Long departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        return  employeeRepository.findByDepartmentId(departmentId).stream()
                .map(e -> new EmployeeResponseDTO(
                        e.getId(),
                        e.getName(),
                        e.getEmail(),
                        e.getSalary(),
                        e.getDepartment().getName(),
                        e.getRegistrationDate()
                ))
                .toList();

    }

    @Transactional
    public EmployeeResponseDTO updateEmployee(EmployeeUpdateDTO employeeUpdateDTO, Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Funcionpario não encontrado"));

        if (employeeUpdateDTO.name() != null) {
            employee.setName(employeeUpdateDTO.name());
        }

        if (employeeUpdateDTO.email() != null) {
            employee.setEmail(employeeUpdateDTO.email());
        }

        if (employeeUpdateDTO.salary() != null) {
            employee.setSalary(employeeUpdateDTO.salary());
        }

        employeeRepository.save(employee);

        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .departmentName(employee.getDepartment().getName())
                .registrationDate(employee.getRegistrationDate())
                .build();

    }

    @Transactional
    public void deleteEmployee(Long employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        employeeRepository.delete(employeeEntity);
    }
}
