package com.ptit.medicare_pharmacy_service.exception;

public class PrescriptionNotFoundException extends ResourceNotFoundException {
    public PrescriptionNotFoundException(Long id) {
        super("Không tìm thấy đơn thuốc với ID: " + id);
    }

    public PrescriptionNotFoundException(String message) {
        super(message);
    }
}
