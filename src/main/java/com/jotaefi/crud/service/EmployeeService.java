package com.jotaefi.crud.service;

import com.jotaefi.crud.dto.request.EmployeeCreateDTO;
import com.jotaefi.crud.dto.response.EmployeeResponseDTO;
import com.jotaefi.crud.dto.request.EmployeeUpdateDTO;
import com.jotaefi.crud.entity.DepartmentEntity;
import com.jotaefi.crud.entity.EmployeeEntity;
import com.jotaefi.crud.enums.EmployeeStatus;
import com.jotaefi.crud.exception.BadRequestException;
import com.jotaefi.crud.exception.EmployeeAlreadyTurnedOffException;
import com.jotaefi.crud.exception.NotFoundException;
import com.jotaefi.crud.repository.DepartmentRepository;
import com.jotaefi.crud.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
            throw new BadRequestException("Já possui um funcionário com este email");
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
                .status(salvo.getStatus())
                .registrationDate(salvo.getRegistrationDate())
                .build();
    }

    public Page<EmployeeResponseDTO> findAll(Pageable pageable) {
        List<EmployeeStatus> statuses = List.of(EmployeeStatus.ATIVO, EmployeeStatus.FERIAS);

        Page<EmployeeEntity> employees = employeeRepository.findByStatusInOrderByNameAsc(statuses, pageable);

        return employees
                .map(e -> EmployeeResponseDTO.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .email(e.getEmail())
                        .salary(e.getSalary())
                        .departmentName(e.getDepartment().getName())
                        .status(e.getStatus())
                        .registrationDate(e.getRegistrationDate())
                        .build()
                );
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
                .status(employee.getStatus())
                .registrationDate(employee.getRegistrationDate())
                .build();
    }

    public List<EmployeeResponseDTO> findByDepartmentId(Long departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        List<EmployeeStatus> statuses = List.of(EmployeeStatus.ATIVO, EmployeeStatus.FERIAS);

        List<EmployeeEntity> employees = employeeRepository.findByDepartmentIdAndStatusInOrderByNameAsc(departmentId, statuses);

        return employees.stream()
                .map(e -> new EmployeeResponseDTO(
                        e.getId(),
                        e.getName(),
                        e.getEmail(),
                        e.getSalary(),
                        e.getDepartment().getName(),
                        e.getStatus(),
                        e.getRegistrationDate()
                ))
                .toList();
    }

    @Transactional
    public EmployeeResponseDTO updateEmployee(EmployeeUpdateDTO employeeUpdateDTO, Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        if (employee.getStatus() == EmployeeStatus.DESLIGADO) {
            if (employeeUpdateDTO.status() == EmployeeStatus.ATIVO) {

                employee.setStatus(EmployeeStatus.ATIVO);
                employee.setStatusChange(null);

                employeeRepository.save(employee);

                return EmployeeResponseDTO.builder()
                        .id(employee.getId())
                        .name(employee.getName())
                        .email(employee.getEmail())
                        .salary(employee.getSalary())
                        .departmentName(employee.getDepartment().getName())
                        .status(employee.getStatus())
                        .registrationDate(employee.getRegistrationDate())
                        .build();

            } else {
                throw new EmployeeAlreadyTurnedOffException("Você não pode alterar valores de um funcionário desligado até ele ser ativo novamente.");
            }
        }

        if (employeeUpdateDTO.name() != null) {
            employee.setName(employeeUpdateDTO.name());
        }

        if (employeeUpdateDTO.email() != null) {
            employee.setEmail(employeeUpdateDTO.email());
        }

        if (employeeUpdateDTO.salary() != null) {
            employee.setSalary(employeeUpdateDTO.salary());
        }

        if (employeeUpdateDTO.status() != null && employeeUpdateDTO.status() != employee.getStatus()) {
            EmployeeStatus novoStatus = employeeUpdateDTO.status();

            employee.setStatus(novoStatus);

            if (novoStatus == EmployeeStatus.FERIAS || novoStatus == EmployeeStatus.DESLIGADO) {
                employee.setStatusChange(LocalDateTime.now());
            } else if (novoStatus == EmployeeStatus.ATIVO) {
                employee.setStatusChange(null);
            }
        }

        employeeRepository.save(employee);

        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .departmentName(employee.getDepartment().getName())
                .status(employee.getStatus())
                .registrationDate(employee.getRegistrationDate())
                .build();
    }

    public List<EmployeeResponseDTO> findAllByStatus (EmployeeStatus status) {
        List<EmployeeEntity> employees = employeeRepository.findByStatusOrderByNameAsc(status);

        return employees.stream()
                .map(employee -> new EmployeeResponseDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getSalary(),
                        employee.getDepartment().getName(),
                        employee.getStatus(),
                        employee.getRegistrationDate()
                ))
                .toList();
    }
}
