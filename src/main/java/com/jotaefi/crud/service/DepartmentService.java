package com.jotaefi.crud.service;

import com.jotaefi.crud.dto.request.DepartmentCreateDTO;
import com.jotaefi.crud.dto.request.DepartmentUpdateDTO;
import com.jotaefi.crud.dto.response.DepartmentResponseDTO;
import com.jotaefi.crud.entity.Department;
import com.jotaefi.crud.exception.NotFoundException;
import com.jotaefi.crud.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentCreateDTO departmentCreateDTO) {
        Department department = departmentRepository.save(Department.builder()
                .name(departmentCreateDTO.name())
                .build());

        return DepartmentResponseDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    public List<DepartmentResponseDTO> findAllDepartments() {
        return departmentRepository.findAllByOrderByIdAsc().stream()
                .map(d -> new DepartmentResponseDTO(
                        d.getId(),
                        d.getName()
                ))
                .toList();
    }

    @Transactional
    public DepartmentResponseDTO updateDepartment(DepartmentUpdateDTO departmentUpdateDTO,Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        department.setName(departmentUpdateDTO.name());

        return DepartmentResponseDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    @Transactional
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        departmentRepository.delete(department);
    }
}
