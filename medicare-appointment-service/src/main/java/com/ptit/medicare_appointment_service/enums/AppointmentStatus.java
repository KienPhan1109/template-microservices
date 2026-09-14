package com.ptit.medicare_appointment_service.enums;

import lombok.Getter;

@Getter
public enum AppointmentStatus {
    SCHEDULED("Đã lên lịch"),
    CONFIRMED("Đã xác nhận"),
    CHECKED_IN("Đã tiếp đón"),
    IN_PROGRESS("Đang khám"),
    COMPLETED("Đã hoàn thành"),
    CANCELLED("Đã hủy"),
    NO_SHOW("Không đến");

    private final String description;

    AppointmentStatus(String description) {
        this.description = description;
    }

    public static boolean isValid(String value) {
        if (value == null) return false;
        for (AppointmentStatus status : values()) {
            if (status.name().equalsIgnoreCase(value.trim())) {
                return true;
            }
        }
        return false;
    }

    public static AppointmentStatus fromString(String value) {
        if (value == null) return null;
        for (AppointmentStatus status : values()) {
            if (status.name().equalsIgnoreCase(value.trim())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Trạng thái lịch hẹn không hợp lệ: " + value);
    }
}
