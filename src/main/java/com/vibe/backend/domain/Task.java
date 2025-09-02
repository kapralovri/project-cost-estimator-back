package com.vibe.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "estimate_id", nullable = false)
    private Estimate estimate;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "stage_name")
    private String stageName;

    @Column(name = "category", nullable = false)
    private String category = "development"; // development, design, testing, deployment, etc.

    @Column(name = "complexity", nullable = false)
    private String complexity = "medium"; // low, medium, high, expert

    @Column(name = "estimated_hours", nullable = false, precision = 19, scale = 2)
    private BigDecimal estimatedHours = BigDecimal.ZERO;

    @Column(name = "actual_hours", precision = 19, scale = 2)
    private BigDecimal actualHours;

    @Column(name = "status", nullable = false)
    private String status = "planned"; // planned, in_progress, completed, cancelled

    @Column(name = "priority", nullable = false)
    private String priority = "medium"; // low, medium, high, critical

    @Column(name = "assigned_role")
    private String assignedRole;

    @Column(name = "dependencies", columnDefinition = "TEXT")
    private String dependencies; // JSON array of task IDs

    @Column(name = "start_date")
    private OffsetDateTime startDate;

    @Column(name = "due_date")
    private OffsetDateTime dueDate;

    @Column(name = "completed_date")
    private OffsetDateTime completedDate;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_risk", nullable = false)
    private Boolean isRisk = false;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEstimate> estimates = new ArrayList<>();

    @PrePersist
    void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
