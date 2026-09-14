package com.ptit.medicare_doctor_service.exception;

public class InvalidDepartmentException extends RuntimeException {
    public InvalidDepartmentException(String department) {
        super("Chuyên khoa '" + department + "' không hợp lệ. Vui lòng kiểm tra lại danh sách chuyên khoa hỗ trợ.");
    }
}
