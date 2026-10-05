package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    boolean existsByEmail(String email);
    Optional<UsersEntity> findByEmail(String email);

}
