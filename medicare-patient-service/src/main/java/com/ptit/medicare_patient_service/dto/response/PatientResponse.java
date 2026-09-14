package com.ptit.medicare_patient_service.dto.response;

import com.ptit.medicare_patient_service.enums.PatientBlood;
import com.ptit.medicare_patient_service.enums.PatientGender;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PatientResponse {
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private PatientGender gender;
    private String phone;
    private String email;
    private String address;
    private String insuranceId;
    private PatientBlood bloodType;
    private String emergencyContactName;
    private String emergencyContactPhone;
}
