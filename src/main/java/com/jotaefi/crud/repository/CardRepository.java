package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.CardsEntity;
import com.jotaefi.crud.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardsEntity, Long> {

    Optional<CardsEntity> findByUserId(Long userId);
}
