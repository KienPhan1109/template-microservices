package com.ptit.medicare_pharmacy_service.exception;

public class InvalidPrescriptionStateException extends RuntimeException {
    public InvalidPrescriptionStateException(String message) {
        super(message);
    }
}
