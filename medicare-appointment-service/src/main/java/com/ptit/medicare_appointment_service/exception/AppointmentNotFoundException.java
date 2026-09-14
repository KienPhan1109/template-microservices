package com.ptit.medicare_appointment_service.exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }

    public AppointmentNotFoundException(Long id) {
        super("Không tìm thấy lịch hẹn với ID: " + id);
    }
}
