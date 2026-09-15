package com.jotaefi.crud.service;

import com.jotaefi.crud.dto.request.DepartmentCreateDTO;
import com.jotaefi.crud.dto.request.DepartmentUpdateDTO;
import com.jotaefi.crud.dto.response.DepartmentResponseDTO;
import com.jotaefi.crud.entity.DepartmentEntity;
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
        DepartmentEntity departmentEntity = departmentRepository.save(DepartmentEntity.builder()
                .name(departmentCreateDTO.name())
                .build());

        return DepartmentResponseDTO.builder()
                .id(departmentEntity.getId())
                .name(departmentEntity.getName())
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
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        departmentEntity.setName(departmentUpdateDTO.name());

        return DepartmentResponseDTO.builder()
                .id(departmentEntity.getId())
                .name(departmentEntity.getName())
                .build();
    }

    @Transactional
    public void deleteDepartment(Long departmentId) {
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        departmentRepository.delete(departmentEntity);
    }
}
