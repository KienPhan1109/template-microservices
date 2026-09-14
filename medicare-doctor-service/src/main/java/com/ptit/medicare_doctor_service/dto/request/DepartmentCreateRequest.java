package com.ptit.medicare_doctor_service.dto.request;

import com.ptit.medicare_doctor_service.validator.UniqueDepartmentCode;
import com.ptit.medicare_doctor_service.validator.UniqueDepartmentLocation;
import com.ptit.medicare_doctor_service.validator.ValidDepartmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentCreateRequest {
    @NotBlank(message = "Mã chuyên khoa không được để trống")
    @Size(max = 50, message = "Mã chuyên khoa không được dài quá 50 ký tự")
    @UniqueDepartmentCode
    private String departmentCode;

    @NotBlank(message = "Tên chuyên khoa không được để trống")
    @Size(max = 100, message = "Tên chuyên khoa không được dài quá 100 ký tự")
    private String departmentName;

    @Size(max = 1000, message = "Mô tả không được dài quá 1000 ký tự")
    private String description;

    @NotBlank(message = "Vị trí chuyên khoa không được để trống")
    @Size(max = 100, message = "Vị trí không được dài quá 100 ký tự")
    @Pattern(
        regexp = "^(Floor\\s+[0-9]+|Basement)\\s*-\\s*Building\\s+[A-Z](\\s*-\\s*Room\\s+[0-9A-Za-z]+)?$",
        message = "Vị trí phải đúng cú pháp tiếng Anh (VD: 'Floor 3 - Building A', 'Basement - Building A' hoặc 'Floor 1 - Building B - Room 102')"
    )
    @UniqueDepartmentLocation
    private String location;

    @NotBlank(message = "Số điện thoại chuyên khoa không được để trống")
    @Pattern(
        regexp = "^(0[0-9]{9}|02[0-9]{9}|02[0-9]-[0-9]{4}-[0-9]{4})$",
        message = "Số điện thoại không hợp lệ (hỗ trợ số di động 10 số hoặc cố định VD: 024-3825-0001)"
    )
    private String phone;

    @ValidDepartmentStatus
    private String status;
}
