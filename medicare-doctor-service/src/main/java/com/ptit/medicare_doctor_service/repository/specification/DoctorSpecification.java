package com.ptit.medicare_doctor_service.repository.specification;

import com.ptit.medicare_doctor_service.dto.request.DoctorFilterRequest;
import com.ptit.medicare_doctor_service.entity.Doctor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DoctorSpecification {
    public static Specification<Doctor> filter(DoctorFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Luôn chỉ lấy bác sĩ chưa bị xóa mềm
            predicates.add(cb.isFalse(root.get("isDeleted")));

            if (filter == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            // 2. Lọc theo ID chuyên khoa
            if (filter.getDepartmentId() != null) {
                predicates.add(cb.equal(root.get("department").get("id"), filter.getDepartmentId()));
            }

            // 3. Lọc theo chuỗi chuyên khoa (Mã code hoặc Tên tiếng Việt)
            if (filter.getDepartment() != null && !filter.getDepartment().trim().isEmpty()) {
                String dept = filter.getDepartment().trim().toLowerCase();
                predicates.add(cb.or(
                    cb.equal(cb.lower(root.get("department").get("departmentCode")), dept),
                    cb.like(cb.lower(root.get("department").get("departmentName")), "%" + dept + "%")
                ));
            }

            // 4. Lọc theo trạng thái bác sĩ (ACTIVE, ON_LEAVE, RESIGNED)
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            // 5. Lọc theo giới tính
            if (filter.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), filter.getGender()));
            }

            // 6. Lọc theo số năm kinh nghiệm tối thiểu
            if (filter.getMinExperience() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("experienceYears"), filter.getMinExperience()));
            }

            // 7. Lọc theo giá khám tối đa
            if (filter.getMaxFee() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("consultationFee"), filter.getMaxFee()));
            }

            // 8. Tìm kiếm đa trường theo từ khóa (Keyword: Tên, CCHN, SĐT, Email)
            if (filter.getKeyword() != null && !filter.getKeyword().trim().isEmpty()) {
                String keyword = "%" + filter.getKeyword().trim().toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("fullName")), keyword),
                    cb.like(cb.lower(root.get("licenseNumber")), keyword),
                    cb.like(cb.lower(root.get("phone")), keyword),
                    cb.like(cb.lower(root.get("email")), keyword)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
