package com.vibe.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "parameters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "estimate_id", nullable = false)
    private Estimate estimate;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "param_value", nullable = false)
    private String value;

    @Column(name = "type", nullable = false)
    private String type = "string"; // string, number, boolean, date

    @Column(name = "description")
    private String description;

    @Column(name = "unit")
    private String unit;

    @Column(name = "min_value")
    private BigDecimal minValue;

    @Column(name = "max_value")
    private BigDecimal maxValue;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired = false;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;
}
