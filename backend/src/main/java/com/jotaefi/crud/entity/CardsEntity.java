package com.jotaefi.crud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CardsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Builder.Default
    @Column(name = "food", nullable = false, precision = 12, scale = 2)
    private BigDecimal foodAid = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "transport", nullable = false, precision = 12, scale = 2)
    private BigDecimal transportationAid = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "health", nullable = false, precision = 12, scale = 2)
    private BigDecimal healthAssistence = BigDecimal.ZERO;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
}
