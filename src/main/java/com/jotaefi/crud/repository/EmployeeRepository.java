package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.EmployeeEntity;
import com.jotaefi.crud.enums.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findByEmail(String email);
    List<EmployeeEntity> findByDepartmentIdAndStatusInOrderByNameAsc(Long departmentId, List<EmployeeStatus> statuses);
    List<EmployeeEntity> findByStatusAndStatusChangeBefore(EmployeeStatus status, LocalDateTime date);
    List<EmployeeEntity> findByStatusOrderByNameAsc(EmployeeStatus status);
    Page<EmployeeEntity> findByStatusInOrderByNameAsc(List<EmployeeStatus> statuses, Pageable pageable);

}
