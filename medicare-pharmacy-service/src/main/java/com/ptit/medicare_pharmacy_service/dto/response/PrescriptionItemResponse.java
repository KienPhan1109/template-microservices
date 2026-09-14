package com.ptit.medicare_pharmacy_service.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionItemResponse {
    private Long id;
    private Long medicineId;
    private String medicineCode;
    private String medicineName;
    private String unit;
    private Integer quantity;
    private String dosage;
    private String instruction;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
