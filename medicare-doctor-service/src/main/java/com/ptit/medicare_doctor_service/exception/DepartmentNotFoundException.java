package com.ptit.medicare_doctor_service.exception;

public class DepartmentNotFoundException extends ResourceNotFoundException {
    public DepartmentNotFoundException(Long id) {
        super("Không tìm thấy chuyên khoa với ID: " + id);
    }

    public DepartmentNotFoundException(String code) {
        super("Không tìm thấy chuyên khoa với mã: " + code);
    }
}
