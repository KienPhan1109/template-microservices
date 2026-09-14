package com.ptit.medicare_pharmacy_service.exception;

public class MedicineNotFoundException extends ResourceNotFoundException {
    public MedicineNotFoundException(Long id) {
        super("Không tìm thấy thuốc với ID: " + id);
    }

    public MedicineNotFoundException(String message) {
        super(message);
    }
}
