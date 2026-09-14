package com.ptit.medicare_doctor_service.dto.response;

import com.ptit.medicare_doctor_service.enums.DoctorGender;
import com.ptit.medicare_doctor_service.enums.DoctorStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DoctorResponse {
    private Long id;
    private String licenseNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private DoctorGender gender;
    private String phone;
    private String email;
    private String address;
    private String degree;

    // Thông tin chuyên khoa liên kết
    private Long departmentId;
    private String departmentCode;
    private String departmentName;
    private String departmentLocation;

    private Integer experienceYears;
    private String bio;
    private BigDecimal consultationFee;
    private DoctorStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
