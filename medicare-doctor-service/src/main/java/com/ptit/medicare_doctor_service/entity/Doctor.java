package com.ptit.medicare_doctor_service.entity;

import com.ptit.medicare_doctor_service.enums.DoctorGender;
import com.ptit.medicare_doctor_service.enums.DoctorStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(
    name = "doctors",
    check = {
        @CheckConstraint(name = "chk_doctor_fee", constraint = "consultation_fee >= 0"),
        @CheckConstraint(name = "chk_doctor_gender", constraint = "gender IN ('MALE', 'FEMALE', 'OTHER')"),
        @CheckConstraint(name = "chk_doctor_status", constraint = "status IN ('ACTIVE', 'ON_LEAVE', 'RESIGNED')")
    },
    indexes = {
        @Index(name = "idx_doctor_full_name", columnList = "full_name"),
        @Index(name = "idx_doctor_dept_status", columnList = "department_id, status, is_deleted"),
        @Index(name = "idx_doctor_is_deleted", columnList = "is_deleted")
    }
)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_number", length = 50, nullable = false, unique = true)
    private String licenseNumber;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10, nullable = false)
    private DoctorGender gender = DoctorGender.MALE;

    @Column(name = "phone", length = 15, nullable = false, unique = true)
    private String phone;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "degree", length = 50, nullable = false)
    private String degree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Builder.Default
    @Column(name = "experience_years", nullable = false)
    private Integer experienceYears = 0;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Builder.Default
    @Column(name = "consultation_fee", precision = 12, scale = 2, nullable = false)
    private BigDecimal consultationFee = new BigDecimal("150000.00");

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private DoctorStatus status = DoctorStatus.ACTIVE;

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
