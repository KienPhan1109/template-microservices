package com.ptit.medicare_doctor_service.dto.response;

import com.ptit.medicare_doctor_service.enums.DepartmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentResponse {
    private Long id;
    private String departmentCode;
    private String departmentName;
    private String description;
    private String location;
    private String phone;
    private DepartmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
