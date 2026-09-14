package com.ptit.medicare_patient_service.exception;

public class PatientNotFoundException extends ResourceNotFoundException {
    public PatientNotFoundException(Long id) {
        super("Không tìm thấy bệnh nhân với ID: " + id);
    }

    public PatientNotFoundException(String message) {
        super(message);
    }
}
