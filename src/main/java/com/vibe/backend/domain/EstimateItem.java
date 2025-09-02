package com.vibe.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "estimate_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstimateItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "estimate_id", nullable = false)
    private Estimate estimate;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "hours", nullable = false, precision = 19, scale = 2)
    private BigDecimal hours;

    @Column(name = "rate", nullable = false, precision = 19, scale = 2)
    private BigDecimal rate;

    @Column(name = "cost", nullable = false, precision = 19, scale = 2)
    private BigDecimal cost;
}


