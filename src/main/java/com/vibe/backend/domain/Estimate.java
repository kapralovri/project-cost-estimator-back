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
@Table(name = "estimates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "client")
    private String client;

    @Column(name = "currency", nullable = false)
    private String currency = "USD";

    @Column(name = "total_cost", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCost = BigDecimal.ZERO;

    @Column(name = "quality_level")
    private String qualityLevel = "standard";

    @Column(name = "status")
    private String status = "Актуальный";

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "estimate", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstimateItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "estimate", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parameter> parameters = new ArrayList<>();

    @OneToMany(mappedBy = "estimate", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

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


