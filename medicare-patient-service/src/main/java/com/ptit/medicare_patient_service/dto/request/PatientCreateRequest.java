package com.ptit.medicare_patient_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptit.medicare_patient_service.validator.UniqueEmail;
import com.ptit.medicare_patient_service.validator.UniqueInsuranceId;
import com.ptit.medicare_patient_service.validator.UniquePhone;
import com.ptit.medicare_patient_service.validator.ValidBlood;
import com.ptit.medicare_patient_service.validator.ValidGender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientCreateRequest {
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(max = 100, message = "Họ và tên không được dài quá 100 ký tự")
    private String fullName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Ngày tháng năm sinh không được bỏ trống")
    @PastOrPresent(message = "Ngày tháng năm sinh không được là ngày tương lai")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Giới tính không được bỏ trống")
    @ValidGender(message = "Giới tính chỉ có thể là MALE, FEMALE, OTHER")
    private String gender;

    @UniquePhone
    @NotBlank(message = "Số điện thoại không được bỏ trống")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @UniqueEmail
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được dài quá 100 ký tự")
    private String email;

    @Size(max = 255, message = "Địa chỉ không được dài quá 255 ký tự")
    private String address;

    @UniqueInsuranceId
    @Pattern(regexp = "^BHYT-[0-9]{10}$", message = "Bảo hiểm y tế không đúng định dạng (BHYT-1234567890)")
    @Size(max = 30, message = "Bảo hiểm y tế không được dài quá 30 ký tự")
    private String insuranceId;

    @NotBlank(message = "Nhóm máu không được bỏ trống")
    private String bloodType;

    @Size(max = 100, message = "Họ và tên khẩn cấp không được dài quá 100 ký tự")
    private String emergencyContactName;

    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại khẩn cấp không hợp lệ")
    private String emergencyContactPhone;
}
