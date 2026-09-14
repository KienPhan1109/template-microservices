package com.ptit.medicare_pharmacy_service.entity;

import com.ptit.medicare_pharmacy_service.enums.PrescriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "prescriptions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_prescription_code", columnNames = "prescription_code")
        },
        indexes = {
                @Index(name = "idx_rx_patient", columnList = "patient_id, is_deleted"),
                @Index(name = "idx_rx_doctor", columnList = "doctor_id, is_deleted"),
                @Index(name = "idx_rx_medical", columnList = "medical_id, is_deleted"),
                @Index(name = "idx_rx_status", columnList = "status"),
                @Index(name = "idx_rx_is_deleted", columnList = "is_deleted")
        }
)
@Check(constraints = "total_amount >= 0")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prescription_code", nullable = false, length = 30)
    private String prescriptionCode;

    @Column(name = "medical_id", nullable = false)
    private Long medicalId;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private PrescriptionStatus status = PrescriptionStatus.PENDING;

    @Column(name = "dispensed_at")
    private LocalDateTime dispensedAt;

    @Column(name = "cancelled_reason")
    private String cancelledReason;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PrescriptionItem> items = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void addItem(PrescriptionItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        item.setPrescription(this);
        recalculateTotalAmount();
    }

    public void removeItem(PrescriptionItem item) {
        if (items != null) {
            items.remove(item);
            item.setPrescription(null);
            recalculateTotalAmount();
        }
    }

    public void recalculateTotalAmount() {
        if (items == null || items.isEmpty()) {
            this.totalAmount = BigDecimal.ZERO;
            return;
        }
        this.totalAmount = items.stream()
                .map(item -> item.getTotalPrice() != null ? item.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.isDeleted == null) {
            this.isDeleted = false;
        }
        if (this.status == null) {
            this.status = PrescriptionStatus.PENDING;
        }
        if (this.totalAmount == null) {
            recalculateTotalAmount();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        recalculateTotalAmount();
    }
}
