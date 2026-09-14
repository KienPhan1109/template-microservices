package com.ptit.medicare_doctor_service.service.impl;

import com.ptit.medicare_doctor_service.dto.request.DepartmentCreateRequest;
import com.ptit.medicare_doctor_service.dto.request.DepartmentUpdateRequest;
import com.ptit.medicare_doctor_service.dto.response.DepartmentResponse;
import com.ptit.medicare_doctor_service.entity.Department;
import com.ptit.medicare_doctor_service.enums.DepartmentStatus;
import com.ptit.medicare_doctor_service.exception.DepartmentNotFoundException;
import com.ptit.medicare_doctor_service.exception.DuplicateDepartmentException;
import com.ptit.medicare_doctor_service.mapper.DepartmentMapper;
import com.ptit.medicare_doctor_service.repository.DepartmentRepository;
import com.ptit.medicare_doctor_service.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAllByIsDeletedFalse()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getActiveDepartments() {
        return departmentRepository.findAllByStatusAndIsDeletedFalse(DepartmentStatus.ACTIVE)
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        return departmentMapper.toResponse(department);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentByCode(String code) {
        Department department = departmentRepository.findByDepartmentCodeAndIsDeletedFalse(code.trim().toUpperCase())
                .orElseThrow(() -> new DepartmentNotFoundException(code));
        return departmentMapper.toResponse(department);
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentCreateRequest request) {
        String code = request.getDepartmentCode().trim().toUpperCase();
        if (departmentRepository.existsByDepartmentCode(code)) {
            throw new DuplicateDepartmentException("Mã chuyên khoa '" + code + "' đã tồn tại trong hệ thống");
        }
        String name = request.getDepartmentName().trim();
        if (departmentRepository.existsByDepartmentName(name)) {
            throw new DuplicateDepartmentException("Tên chuyên khoa '" + name + "' đã tồn tại trong hệ thống");
        }

        String loc = request.getLocation().trim();
        if (departmentRepository.existsByLocation(loc)) {
            throw new DuplicateDepartmentException("Vị trí chuyên khoa '" + loc + "' đã được sử dụng bởi chuyên khoa khác");
        }

        Department department = departmentMapper.toEntity(request);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toResponse(saved);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentUpdateRequest request) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        if (request.getDepartmentName() != null && !request.getDepartmentName().trim().isEmpty()) {
            String newName = request.getDepartmentName().trim();
            if (departmentRepository.existsByDepartmentNameAndIdNot(newName, id)) {
                throw new DuplicateDepartmentException("Tên chuyên khoa '" + newName + "' đã được sử dụng bởi chuyên khoa khác");
            }
        }

        if (request.getLocation() != null && !request.getLocation().trim().isEmpty()) {
            String newLoc = request.getLocation().trim();
            if (departmentRepository.existsByLocationAndIdNot(newLoc, id)) {
                throw new DuplicateDepartmentException("Vị trí chuyên khoa '" + newLoc + "' đã được sử dụng bởi chuyên khoa khác");
            }
        }

        departmentMapper.updateEntityFromRequest(department, request);
        Department updated = departmentRepository.save(department);
        return departmentMapper.toResponse(updated);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        department.setDeleted(true);
        departmentRepository.save(department);
    }

    @Override
    public DepartmentResponse restoreDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        department.setDeleted(false);
        Department restored = departmentRepository.save(department);
        return departmentMapper.toResponse(restored);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getDeletedDepartments() {
        return departmentRepository.findAllByIsDeletedTrue()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }
}
