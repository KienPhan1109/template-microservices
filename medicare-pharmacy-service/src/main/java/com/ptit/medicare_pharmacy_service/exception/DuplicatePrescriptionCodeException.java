package com.ptit.medicare_pharmacy_service.exception;

public class DuplicatePrescriptionCodeException extends DuplicateResourceException {
    public DuplicatePrescriptionCodeException(String prescriptionCode) {
        super("Mã đơn thuốc '" + prescriptionCode + "' đã tồn tại trên hệ thống");
    }
}
