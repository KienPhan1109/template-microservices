package com.ptit.medicare_appointment_service.exception;

public class AppointmentTimeConflictException extends RuntimeException {
    public AppointmentTimeConflictException(String message) {
        super(message);
    }
}
