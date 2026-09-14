package com.ptit.medicare_doctor_service.dto.request;

import com.ptit.medicare_doctor_service.validator.ValidDepartmentStatus;
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
public class DepartmentUpdateRequest {
    @Size(max = 100, message = "Tên chuyên khoa không được dài quá 100 ký tự")
    private String departmentName;

    @Size(max = 1000, message = "Mô tả không được dài quá 1000 ký tự")
    private String description;

    @Size(max = 100, message = "Vị trí không được dài quá 100 ký tự")
    @Pattern(
        regexp = "^$|^(Floor\\s+[0-9]+|Basement)\\s*-\\s*Building\\s+[A-Z](\\s*-\\s*Room\\s+[0-9A-Za-z]+)?$",
        message = "Vị trí phải đúng cú pháp tiếng Anh (VD: 'Floor 3 - Building A', 'Basement - Building A' hoặc 'Floor 1 - Building B - Room 102')"
    )
    private String location;

    @Pattern(
        regexp = "^$|^(0[0-9]{9}|02[0-9]{9}|02[0-9]-[0-9]{4}-[0-9]{4})$",
        message = "Số điện thoại không hợp lệ (hỗ trợ số di động 10 số hoặc cố định VD: 024-3825-0001)"
    )
    private String phone;

    @ValidDepartmentStatus
    private String status;
}
