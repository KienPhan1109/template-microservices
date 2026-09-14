package com.ptit.medicare_pharmacy_service.dto.request;

import com.ptit.medicare_pharmacy_service.validator.UniquePrescriptionCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionCreateRequest {

    @Size(max = 30, message = "Mã đơn thuốc không được dài quá 30 ký tự")
    @UniquePrescriptionCode
    private String prescriptionCode;

    @NotNull(message = "Mã bệnh án không được để trống")
    @Positive(message = "Mã bệnh án phải là số nguyên dương")
    private Long medicalId;

    @NotNull(message = "Mã bệnh nhân không được để trống")
    @Positive(message = "Mã bệnh nhân phải là số nguyên dương")
    private Long patientId;

    @NotNull(message = "Mã bác sĩ không được để trống")
    @Positive(message = "Mã bác sĩ phải là số nguyên dương")
    private Long doctorId;

    private String notes;

    @NotEmpty(message = "Đơn thuốc phải chứa ít nhất một loại thuốc")
    @Valid
    private List<PrescriptionItemRequest> items;
}
