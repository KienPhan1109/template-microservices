package com.ptit.medicare_appointment_service.repository;

import com.ptit.medicare_appointment_service.entity.Appointment;
import com.ptit.medicare_appointment_service.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {

    boolean existsByAppointmentCode(String appointmentCode);

    Optional<Appointment> findByIdAndIsDeletedFalse(Long id);

    Optional<Appointment> findByAppointmentCodeAndIsDeletedFalse(String appointmentCode);

    List<Appointment> findAllByIsDeletedFalse();

    List<Appointment> findAllByIsDeletedTrue();

    List<Appointment> findAllByPatientIdAndIsDeletedFalse(Long patientId);

    List<Appointment> findAllByDoctorIdAndIsDeletedFalse(Long doctorId);

    List<Appointment> findAllByAppointmentDateAndIsDeletedFalse(LocalDate appointmentDate);

    List<Appointment> findAllByStatusAndIsDeletedFalse(AppointmentStatus status);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a " +
           "WHERE a.doctorId = :doctorId " +
           "AND a.appointmentDate = :appointmentDate " +
           "AND a.isDeleted = false " +
           "AND a.status NOT IN (com.ptit.medicare_appointment_service.enums.AppointmentStatus.CANCELLED, com.ptit.medicare_appointment_service.enums.AppointmentStatus.NO_SHOW) " +
           "AND (:excludeId IS NULL OR a.id != :excludeId) " +
           "AND (:startTime < a.endTime AND :endTime > a.startTime)")
    boolean hasDoctorOverlap(
            @Param("doctorId") Long doctorId,
            @Param("appointmentDate") LocalDate appointmentDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("excludeId") Long excludeId
    );

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a " +
           "WHERE a.patientId = :patientId " +
           "AND a.appointmentDate = :appointmentDate " +
           "AND a.isDeleted = false " +
           "AND a.status NOT IN (com.ptit.medicare_appointment_service.enums.AppointmentStatus.CANCELLED, com.ptit.medicare_appointment_service.enums.AppointmentStatus.NO_SHOW) " +
           "AND (:excludeId IS NULL OR a.id != :excludeId) " +
           "AND (:startTime < a.endTime AND :endTime > a.startTime)")
    boolean hasPatientOverlap(
            @Param("patientId") Long patientId,
            @Param("appointmentDate") LocalDate appointmentDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("excludeId") Long excludeId
    );
}
