package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.EmployeeStatusHistory;
import com.jotaefi.crud.enums.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeStatusHistoryRepository extends JpaRepository<EmployeeStatusHistory, Long> {

    Optional<EmployeeStatusHistory> findTopByEmployeeIdOrderByChangeAtDesc(Long employeeId);
    List<EmployeeStatusHistory> findByNewStatusAndChangeAtBefore(EmployeeStatus status, LocalDateTime limit);
}
