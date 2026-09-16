package com.jotaefi.crud.entity;

import com.jotaefi.crud.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false,precision = 10, scale = 2)
    private BigDecimal salary;

    @CreationTimestamp
    private LocalDateTime registrationDate;

    private LocalDateTime statusChange;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EmployeeStatus status = EmployeeStatus.ATIVO;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
}
