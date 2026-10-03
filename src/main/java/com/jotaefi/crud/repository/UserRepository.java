package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    boolean existsByEmail(String email);

}
