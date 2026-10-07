package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.CardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardsEntity, Long> {
}
