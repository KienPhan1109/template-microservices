package com.ptit.medicare_pharmacy_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptit.medicare_pharmacy_service.enums.PrescriptionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionResponse {
    private Long id;
    private String prescriptionCode;
    private Long medicalId;
    private Long patientId;
    private Long doctorId;
    private BigDecimal totalAmount;
    private PrescriptionStatus status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dispensedAt;

    private String cancelledReason;
    private String notes;
    private Boolean isDeleted;
    private List<PrescriptionItemResponse> items;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
