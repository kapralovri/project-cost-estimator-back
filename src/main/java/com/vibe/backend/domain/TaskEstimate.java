package com.vibe.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "task_estimates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEstimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "min_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal min = BigDecimal.ZERO;

    @Column(name = "real_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal real = BigDecimal.ZERO;

    @Column(name = "max_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal max = BigDecimal.ZERO;
}

