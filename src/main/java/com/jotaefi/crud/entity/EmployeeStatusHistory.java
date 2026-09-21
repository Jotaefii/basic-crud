package com.jotaefi.crud.entity;

import com.jotaefi.crud.enums.EmployeeStatus;
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
public class EmployeeStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus newStatus;

    private LocalDateTime changeAt;

    @ManyToOne
    private EmployeeEntity employee;
}
