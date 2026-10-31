package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    List<DepartmentEntity> findAllByOrderByIdAsc();
}
