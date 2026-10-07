package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.EmployeeEntity;
import com.jotaefi.crud.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @EntityGraph(attributePaths = {"user", "card", "department"})
    Page<EmployeeEntity> findByStatusIn(Collection<UserStatus> statuses, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "card", "department"})
    List<EmployeeEntity> findByDepartmentIdAndStatusIn(Long departmentId, Collection<UserStatus> statuses, Sort sort);

    @EntityGraph(attributePaths = {"user", "card", "department"})
    List<EmployeeEntity> findByStatus(UserStatus status, Sort sort);

    @EntityGraph(attributePaths = {"user", "card", "department"})
    List<EmployeeEntity> findByUserNameContainingIgnoreCaseAndStatusIn(String name, Collection<UserStatus> statuses, Sort sort);

    @EntityGraph(attributePaths = {"user", "card", "department"})
    Optional<EmployeeEntity> findByUserId(Long userId);
}
