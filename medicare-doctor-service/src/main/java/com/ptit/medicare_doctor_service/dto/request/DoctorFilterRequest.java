package com.ptit.medicare_doctor_service.dto.request;

import com.ptit.medicare_doctor_service.enums.DoctorGender;
import com.ptit.medicare_doctor_service.enums.DoctorStatus;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DoctorFilterRequest {
    private Long departmentId;
    private String department;
    private DoctorStatus status;
    private DoctorGender gender;
    private String keyword;
    private Integer minExperience;
    private BigDecimal maxFee;
}
