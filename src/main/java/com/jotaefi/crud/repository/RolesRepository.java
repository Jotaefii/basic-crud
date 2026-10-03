package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<RolesEntity, Long> {

    Optional<RolesEntity> findByName(String role);
}
