package com.ptit.medicare_doctor_service.service.impl;

import com.ptit.medicare_doctor_service.dto.request.DoctorCreateRequest;
import com.ptit.medicare_doctor_service.dto.request.DoctorFilterRequest;
import com.ptit.medicare_doctor_service.dto.response.DoctorResponse;
import com.ptit.medicare_doctor_service.entity.Department;
import com.ptit.medicare_doctor_service.entity.Doctor;
import com.ptit.medicare_doctor_service.enums.DoctorStatus;
import com.ptit.medicare_doctor_service.exception.DepartmentNotFoundException;
import com.ptit.medicare_doctor_service.exception.DoctorNotFoundException;
import com.ptit.medicare_doctor_service.mapper.DoctorMapper;
import com.ptit.medicare_doctor_service.repository.DepartmentRepository;
import com.ptit.medicare_doctor_service.repository.DoctorRepository;
import com.ptit.medicare_doctor_service.repository.specification.DoctorSpecification;
import com.ptit.medicare_doctor_service.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorMapper doctorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getDoctors(DoctorFilterRequest filter) {
        return doctorRepository.findAll(DoctorSpecification.filter(filter))
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAllByIsDeletedFalse()
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));
        return doctorMapper.toResponse(doctor);
    }

    @Override
    public DoctorResponse createDoctor(DoctorCreateRequest request) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(request.getDepartmentId()));

        Doctor doctor = doctorMapper.toEntity(request, department);
        doctor.setDeleted(false);
        if (doctor.getStatus() == null) {
            doctor.setStatus(DoctorStatus.ACTIVE);
        }
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toResponse(savedDoctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));
        doctor.setDeleted(true);
        doctorRepository.save(doctor);
    }

    @Override
    public DoctorResponse restoreDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));
        doctor.setDeleted(false);
        Doctor restoredDoctor = doctorRepository.save(doctor);
        return doctorMapper.toResponse(restoredDoctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getDeletedDoctors() {
        return doctorRepository.findAllByIsDeletedTrue()
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getDoctorsByDepartmentId(Long departmentId) {
        return doctorRepository.findAllByDepartmentIdAndIsDeletedFalse(departmentId)
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }
}
