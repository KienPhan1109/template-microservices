package com.ptit.medicare_doctor_service.enums;

import lombok.Getter;

@Getter
public enum DoctorDepartment {
    CARDIOLOGY("Khoa Tim mạch"),
    GASTROENTEROLOGY("Khoa Nội tiêu hóa"),
    PEDIATRICS("Khoa Nhi"),
    OTOLARYNGOLOGY("Khoa Tai Mũi Họng"),
    OPHTHALMOLOGY("Khoa Mắt"),
    ORTHOPEDICS("Khoa Chấn thương chỉnh hình"),
    DERMATOLOGY("Khoa Da liễu"),
    OBSTETRICS_GYNECOLOGY("Khoa Sản phụ khoa"),
    NEUROSURGERY("Khoa Ngoại thần kinh"),
    NEUROLOGY("Khoa Nội thần kinh"),
    ENDOCRINOLOGY("Khoa Nội tiết"),
    EMERGENCY("Khoa Cấp cứu & Hồi sức tích cực"),
    ODONTO_STOMATOLOGY("Khoa Răng Hàm Mặt"),
    ONCOLOGY("Khoa Ung bướu"),
    DIAGNOSTIC_IMAGING("Khoa Chẩn đoán hình ảnh"),
    INTERNAL_MEDICINE("Khoa Nội tổng quát"),
    GENERAL_SURGERY("Khoa Ngoại tổng quát"),
    PULMONOLOGY("Khoa Hô hấp"),
    HEMATOLOGY("Khoa Huyết học - Truyền máu"),
    INFECTIOUS_DISEASES("Khoa Truyền nhiễm"),
    NEPHROLOGY_UROLOGY("Khoa Thận - Tiết niệu"),
    REHABILITATION("Khoa Phục hồi chức năng"),
    PSYCHIATRY("Khoa Sức khỏe tâm thần"),
    ANESTHESIOLOGY("Khoa Gây mê hồi sức"),
    NUTRITION("Khoa Dinh dưỡng lâm sàng"),
    ALLERGY_IMMUNOLOGY("Khoa Dị ứng - Miễn dịch"),
    GERIATRICS("Khoa Lão khoa"),
    TRADITIONAL_MEDICINE("Khoa Y học cổ truyền"),
    PLASTIC_SURGERY("Khoa Phẫu thuật tạo hình - Thẩm mỹ"),
    PATHOLOGY("Khoa Giải phẫu bệnh");

    private final String displayName;

    DoctorDepartment(String displayName) {
        this.displayName = displayName;
    }

    public static DoctorDepartment fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        String clean = value.trim();
        for (DoctorDepartment dept : values()) {
            if (dept.name().equalsIgnoreCase(clean)) {
                return dept;
            }
        }
        for (DoctorDepartment dept : values()) {
            if (dept.getDisplayName().equalsIgnoreCase(clean)
                    || dept.getDisplayName().replace("Khoa ", "").equalsIgnoreCase(clean)
                    || dept.getDisplayName().toLowerCase().contains(clean.toLowerCase())) {
                return dept;
            }
        }
        return null;
    }
}
