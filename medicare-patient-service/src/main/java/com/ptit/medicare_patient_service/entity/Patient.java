package com.ptit.medicare_patient_service.entity;

import com.ptit.medicare_patient_service.enums.PatientBlood;
import com.ptit.medicare_patient_service.enums.PatientGender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(
    name = "patients",
    check = {
        @CheckConstraint(name = "chk_patient_gender", constraint = "gender IN ('MALE', 'FEMALE', 'OTHER')"),
        @CheckConstraint(name = "chk_patient_blood_type", constraint = "blood_type IN ('A', 'B', 'AB', 'O', 'UNKNOWN')")
    },
    indexes = {
        @Index(name = "idx_patient_full_name", columnList = "full_name"),
        @Index(name = "idx_patient_is_deleted", columnList = "is_deleted"),
        @Index(name = "idx_patient_blood_type", columnList = "blood_type"),
    }
)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10, nullable = false)
    private PatientGender gender = PatientGender.MALE;

    @Column(name = "phone", length = 15, nullable = false, unique = true)
    private String phone;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "insurance_id", length = 30, unique = true)
    private String insuranceId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", length = 10, nullable = false)
    private PatientBlood bloodType = PatientBlood.UNKNOWN;

    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone", length = 15)
    private String emergencyContactPhone;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
