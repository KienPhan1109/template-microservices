package com.ptit.medicare_doctor_service.entity;

import com.ptit.medicare_doctor_service.enums.DepartmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(
    name = "departments",
    indexes = {
        @Index(name = "idx_dept_code", columnList = "department_code"),
        @Index(name = "idx_dept_location", columnList = "location"),
        @Index(name = "idx_dept_status_deleted", columnList = "status, is_deleted")
    }
)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_code", length = 50, nullable = false, unique = true)
    private String departmentCode;

    @Column(name = "department_name", length = 100, nullable = false, unique = true)
    private String departmentName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "location", length = 100, nullable = false, unique = true)
    private String location;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private DepartmentStatus status = DepartmentStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
