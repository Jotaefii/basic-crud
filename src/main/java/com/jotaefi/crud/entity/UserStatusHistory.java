package com.jotaefi.crud.entity;

import com.jotaefi.crud.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "status_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserStatus oldStatus;
    @Enumerated(EnumType.STRING)
    private UserStatus newStatus;
    private LocalDateTime changeAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
}
