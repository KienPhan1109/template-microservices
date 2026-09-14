package com.ptit.medicare_doctor_service.exception;

public class DoctorNotFoundException extends ResourceNotFoundException {
    public DoctorNotFoundException(Long id) {
        super("Không tìm thấy bác sĩ với ID: " + id);
    }

    public DoctorNotFoundException(String message) {
        super(message);
    }
}
