package com.jotaefi.crud.repository;

import com.jotaefi.crud.entity.UserStatusHistory;
import com.jotaefi.crud.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserStatusHistoryRepository extends JpaRepository<UserStatusHistory, Long> {

    Optional<UserStatusHistory> findTopByUserIdOrderByChangeAtDesc(Long userId);
    List<UserStatusHistory> findByNewStatusAndChangeAtBefore(UserStatus status, LocalDateTime limit);
}
