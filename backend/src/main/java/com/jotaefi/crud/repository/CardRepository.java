package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.CardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardsEntity, Long> {

    Optional<CardsEntity> findByEmployee_Id(Long employeeId);
}
