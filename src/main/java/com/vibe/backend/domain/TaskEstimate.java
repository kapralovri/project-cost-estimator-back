package com.vibe.backend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "task_estimates")
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

    public Long getId() { return id; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public BigDecimal getMin() { return min; }
    public void setMin(BigDecimal min) { this.min = min; }
    public BigDecimal getReal() { return real; }
    public void setReal(BigDecimal real) { this.real = real; }
    public BigDecimal getMax() { return max; }
    public void setMax(BigDecimal max) { this.max = max; }
}

