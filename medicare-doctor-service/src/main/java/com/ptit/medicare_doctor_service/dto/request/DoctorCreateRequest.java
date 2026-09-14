package com.ptit.medicare_doctor_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptit.medicare_doctor_service.validator.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorCreateRequest {
    @NotBlank(message = "Số chứng chỉ hành nghề không được để trống")
    @Size(max = 50, message = "Số chứng chỉ hành nghề không được dài quá 50 ký tự")
    @UniqueLicense
    private String licenseNumber;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(max = 100, message = "Họ và tên không được dài quá 100 ký tự")
    private String fullName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Ngày tháng năm sinh không được bỏ trống")
    @PastOrPresent(message = "Ngày tháng năm sinh không được là ngày tương lai")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Giới tính không được bỏ trống")
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

    @NotBlank(message = "Học vị/học hàm không được để trống")
    @Size(max = 50, message = "Học vị/học hàm không được dài quá 50 ký tự")
    private String degree;

    @NotNull(message = "ID chuyên khoa không được bỏ trống")
    private Long departmentId;

    @NotNull(message = "Số năm kinh nghiệm không được bỏ trống")
    @Min(value = 0, message = "Số năm kinh nghiệm phải lớn hơn hoặc bằng 0")
    private Integer experienceYears;

    private String bio;

    @NotNull(message = "Phí khám bệnh không được bỏ trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Phí khám bệnh phải lớn hơn hoặc bằng 0")
    private BigDecimal consultationFee;

    @ValidStatus(message = "Trạng thái chỉ có thể là ACTIVE, ON_LEAVE, RESIGNED")
    private String status;
}
