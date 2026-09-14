package com.ptit.medicare_pharmacy_service.entity;

import com.ptit.medicare_pharmacy_service.enums.MedicineStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "medicines",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_medicine_code", columnNames = "medicine_code")
        },
        indexes = {
                @Index(name = "idx_medicine_category", columnList = "category"),
                @Index(name = "idx_medicine_name", columnList = "name"),
                @Index(name = "idx_medicine_expiry", columnList = "expiry_date"),
                @Index(name = "idx_medicine_status_deleted", columnList = "status, is_deleted"),
                @Index(name = "idx_medicine_is_deleted", columnList = "is_deleted")
        }
)
@Check(constraints = "unit_price >= 0")
@Check(constraints = "stock_quantity >= 0")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medicine_code", nullable = false, length = 50)
    private String medicineCode;

    @Column(name = "registration_number", length = 50)
    private String registrationNumber;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "active_ingredient", length = 150)
    private String activeIngredient;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "dosage_form", length = 50)
    private String dosageForm;

    @Column(name = "unit", nullable = false, length = 30)
    private String unit;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(name = "stock_quantity", nullable = false)
    @Builder.Default
    private Integer stockQuantity = 0;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private MedicineStatus status = MedicineStatus.AVAILABLE;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Version
    @Column(name = "version", nullable = false)
    @Builder.Default
    private Long version = 0L;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.isDeleted == null) {
            this.isDeleted = false;
        }
        if (this.status == null) {
            this.status = (this.stockQuantity != null && this.stockQuantity > 0)
                    ? MedicineStatus.AVAILABLE
                    : MedicineStatus.OUT_OF_STOCK;
        }
        if (this.unitPrice == null) {
            this.unitPrice = BigDecimal.ZERO;
        }
        if (this.stockQuantity == null) {
            this.stockQuantity = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
