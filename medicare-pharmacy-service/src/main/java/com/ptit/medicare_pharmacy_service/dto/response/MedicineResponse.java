package com.ptit.medicare_pharmacy_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptit.medicare_pharmacy_service.enums.MedicineStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineResponse {
    private Long id;
    private String medicineCode;
    private String registrationNumber;
    private String name;
    private String activeIngredient;
    private String category;
    private String dosageForm;
    private String unit;
    private BigDecimal unitPrice;
    private Integer stockQuantity;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate expiryDate;

    private MedicineStatus status;
    private Boolean isDeleted;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
