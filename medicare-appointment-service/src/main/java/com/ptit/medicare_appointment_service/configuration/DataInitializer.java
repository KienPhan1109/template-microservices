package com.ptit.medicare_appointment_service.configuration;

import com.ptit.medicare_appointment_service.entity.Appointment;
import com.ptit.medicare_appointment_service.enums.AppointmentStatus;
import com.ptit.medicare_appointment_service.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (appointmentRepository.count() > 0) {
            log.info("Appointment database already initialized. Current count: {}", appointmentRepository.count());
            return;
        }

        log.info("Initializing sample appointments data...");

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate yesterday = today.minusDays(1);

        List<Appointment> initialAppointments = List.of(
                Appointment.builder()
                        .appointmentCode("APT202609080001")
                        .patientId(1L)
                        .doctorId(1L)
                        .appointmentDate(yesterday)
                        .startTime(LocalTime.of(8, 30))
                        .endTime(LocalTime.of(9, 0))
                        .department("Khoa Tim mạch")
                        .roomNumber("P.201")
                        .reason("Tức ngực, hồi hộp khi vận động mạnh")
                        .status(AppointmentStatus.COMPLETED)
                        .notes("Bệnh nhân có tiền sử tăng huyết áp gia đình. Đã chuyển hồ sơ sang bệnh án.")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080002")
                        .patientId(2L)
                        .doctorId(2L)
                        .appointmentDate(today)
                        .startTime(LocalTime.of(9, 15))
                        .endTime(LocalTime.of(9, 45))
                        .department("Khoa Nội tiêu hóa")
                        .roomNumber("P.105")
                        .reason("Mệt mỏi kéo dài, đau vùng thượng vị sau ăn")
                        .status(AppointmentStatus.IN_PROGRESS)
                        .notes("Đang chờ kết quả xét nghiệm máu và nội soi dạ dày")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080003")
                        .patientId(3L)
                        .doctorId(4L)
                        .appointmentDate(today)
                        .startTime(LocalTime.of(10, 0))
                        .endTime(LocalTime.of(10, 30))
                        .department("Khoa Tai Mũi Họng")
                        .roomNumber("P.302")
                        .reason("Đau họng, rát cổ, khàn tiếng 3 ngày nay")
                        .status(AppointmentStatus.CHECKED_IN)
                        .notes("Bệnh nhân đã có mặt tại sảnh chờ tầng 3")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080004")
                        .patientId(4L)
                        .doctorId(3L)
                        .appointmentDate(tomorrow)
                        .startTime(LocalTime.of(8, 0))
                        .endTime(LocalTime.of(8, 30))
                        .department("Khoa Nhi")
                        .roomNumber("P.208")
                        .reason("Khám sức khỏe tổng quát định kỳ và tư vấn tiêm chủng cho trẻ")
                        .status(AppointmentStatus.CONFIRMED)
                        .notes("Gia đình yêu cầu bác sĩ trưởng khoa thăm khám")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080005")
                        .patientId(5L)
                        .doctorId(6L)
                        .appointmentDate(tomorrow)
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(9, 30))
                        .department("Khoa Chấn thương chỉnh hình")
                        .roomNumber("P.401")
                        .reason("Đau nhức khớp gối hai bên khi lên xuống cầu thang")
                        .status(AppointmentStatus.SCHEDULED)
                        .notes("Cần chỉ định chụp X-quang khớp gối tư thế đứng")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080006")
                        .patientId(6L)
                        .doctorId(7L)
                        .appointmentDate(tomorrow)
                        .startTime(LocalTime.of(10, 0))
                        .endTime(LocalTime.of(10, 30))
                        .department("Khoa Da liễu")
                        .roomNumber("P.205")
                        .reason("Nổi mẩn ngứa vùng cánh tay và lưng sau khi ăn hải sản")
                        .status(AppointmentStatus.SCHEDULED)
                        .notes("Nghi ngờ dị ứng thức ăn cấp tính")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080007")
                        .patientId(7L)
                        .doctorId(8L)
                        .appointmentDate(tomorrow)
                        .startTime(LocalTime.of(14, 0))
                        .endTime(LocalTime.of(14, 30))
                        .department("Khoa Sản phụ khoa")
                        .roomNumber("P.112")
                        .reason("Khám thai định kỳ tuần thứ 28 và siêu âm 4D hình thái thai")
                        .status(AppointmentStatus.CONFIRMED)
                        .notes("Đã đặt hẹn trước 1 tuần")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080008")
                        .patientId(8L)
                        .doctorId(10L)
                        .appointmentDate(yesterday)
                        .startTime(LocalTime.of(14, 30))
                        .endTime(LocalTime.of(15, 0))
                        .department("Khoa Thần kinh")
                        .roomNumber("P.310")
                        .reason("Đau nửa đầu Migraine kèm chóng mặt, mất ngủ kéo dài")
                        .status(AppointmentStatus.COMPLETED)
                        .notes("Đã kê đơn thuốc an thần và giãn mạch não")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080009")
                        .patientId(9L)
                        .doctorId(11L)
                        .appointmentDate(today)
                        .startTime(LocalTime.of(15, 0))
                        .endTime(LocalTime.of(15, 30))
                        .department("Khoa Nội tiết")
                        .roomNumber("P.215")
                        .reason("Tái khám theo dõi chỉ số đường huyết HbA1c và chỉnh liều Insulin")
                        .status(AppointmentStatus.SCHEDULED)
                        .notes("Nhắc bệnh nhân nhịn ăn sáng để xét nghiệm đường huyết đói")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080010")
                        .patientId(10L)
                        .doctorId(13L)
                        .appointmentDate(yesterday)
                        .startTime(LocalTime.of(16, 0))
                        .endTime(LocalTime.of(16, 30))
                        .department("Khoa Răng Hàm Mặt")
                        .roomNumber("P.108")
                        .reason("Đau nhức dữ dội răng hàm số 8 hàm dưới bên phải")
                        .status(AppointmentStatus.CANCELLED)
                        .notes("[ĐÃ HỦY] Lý do: Bệnh nhân bận công tác đột xuất, xin dời sang tuần sau")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080011")
                        .patientId(11L)
                        .doctorId(5L)
                        .appointmentDate(yesterday)
                        .startTime(LocalTime.of(10, 30))
                        .endTime(LocalTime.of(11, 0))
                        .department("Khoa Mắt")
                        .roomNumber("P.304")
                        .reason("Mắt nhìn mờ, khô rát mắt khi làm việc máy tính nhiều")
                        .status(AppointmentStatus.NO_SHOW)
                        .notes("Hệ thống đã tự động ghi nhận vắng mặt sau 30 phút")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080012")
                        .patientId(12L)
                        .doctorId(18L)
                        .appointmentDate(today)
                        .startTime(LocalTime.of(11, 0))
                        .endTime(LocalTime.of(11, 30))
                        .department("Khoa Hô hấp")
                        .roomNumber("P.220")
                        .reason("Ho khan kéo dài về đêm kèm khó thở nhẹ khi gắng sức")
                        .status(AppointmentStatus.SCHEDULED)
                        .notes("Đã hướng dẫn đo phế dung khí")
                        .isDeleted(false)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080013")
                        .patientId(13L)
                        .doctorId(19L)
                        .appointmentDate(yesterday)
                        .startTime(LocalTime.of(15, 30))
                        .endTime(LocalTime.of(16, 0))
                        .department("Khoa Huyết học")
                        .roomNumber("P.102")
                        .reason("Da xanh xao, niêm mạc nhợt nhạt, hay hoa mắt chóng mặt")
                        .status(AppointmentStatus.COMPLETED)
                        .notes("Chẩn đoán: Thiếu máu thiếu sắt mức độ vừa")
                        .isDeleted(false)
                        .build(),

                // 2 lịch hẹn xóa mềm mẫu để test chức năng /deleted và /restore
                Appointment.builder()
                        .appointmentCode("APT202609080014")
                        .patientId(14L)
                        .doctorId(1L)
                        .appointmentDate(yesterday.minusDays(5))
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(9, 30))
                        .department("Khoa Tim mạch")
                        .roomNumber("P.201")
                        .reason("Kiểm tra điện tâm đồ định kỳ")
                        .status(AppointmentStatus.CANCELLED)
                        .notes("Lịch hẹn tạo nhầm đã được xóa mềm")
                        .isDeleted(true)
                        .build(),

                Appointment.builder()
                        .appointmentCode("APT202609080015")
                        .patientId(15L)
                        .doctorId(2L)
                        .appointmentDate(yesterday.minusDays(4))
                        .startTime(LocalTime.of(14, 0))
                        .endTime(LocalTime.of(14, 30))
                        .department("Khoa Nội tiêu hóa")
                        .roomNumber("P.105")
                        .reason("Khám rối loạn tiêu hóa cấp")
                        .status(AppointmentStatus.CANCELLED)
                        .notes("Bệnh nhân hủy lịch và xóa mềm")
                        .isDeleted(true)
                        .build()
        );

        appointmentRepository.saveAll(initialAppointments);
        log.info("Successfully seeded {} sample appointments into database.", initialAppointments.size());
    }
}
