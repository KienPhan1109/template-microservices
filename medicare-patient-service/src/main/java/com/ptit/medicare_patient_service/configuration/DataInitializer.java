package com.ptit.medicare_patient_service.configuration;

import com.ptit.medicare_patient_service.entity.Patient;
import com.ptit.medicare_patient_service.enums.PatientBlood;
import com.ptit.medicare_patient_service.enums.PatientGender;
import com.ptit.medicare_patient_service.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final PatientRepository patientRepository;

    @Override
    public void run(String... args) throws Exception {
        if (patientRepository.count() == 0) {
            List<Patient> patients = List.of(
                Patient.builder()
                    .fullName("Nguyễn Văn An")
                    .dateOfBirth(LocalDate.of(1990, 3, 15))
                    .gender(PatientGender.MALE)
                    .phone("0912345001")
                    .email("nguyenvanan90@gmail.com")
                    .address("Số 12 Chùa Bộc, Đống Đa, Hà Nội")
                    .insuranceId("BHYT-1000000001")
                    .bloodType(PatientBlood.O)
                    .emergencyContactName("Nguyễn Văn Bình")
                    .emergencyContactPhone("0987650001")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Trần Thị Mai")
                    .dateOfBirth(LocalDate.of(1995, 7, 22))
                    .gender(PatientGender.FEMALE)
                    .phone("0982345002")
                    .email("tranmai95@gmail.com")
                    .address("Số 45 Nguyễn Trãi, Thanh Xuân, Hà Nội")
                    .insuranceId("BHYT-1000000002")
                    .bloodType(PatientBlood.A)
                    .emergencyContactName("Trần Văn Hùng")
                    .emergencyContactPhone("0976540002")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Lê Hoàng Nam")
                    .dateOfBirth(LocalDate.of(1985, 11, 5))
                    .gender(PatientGender.MALE)
                    .phone("0903456003")
                    .email("lehoangnam.hn@gmail.com")
                    .address("Số 102 Cầu Giấy, Quan Hoa, Cầu Giấy, Hà Nội")
                    .insuranceId("BHYT-1000000003")
                    .bloodType(PatientBlood.B)
                    .emergencyContactName("Lê Thị Lan")
                    .emergencyContactPhone("0915670003")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Phạm Thu Hà")
                    .dateOfBirth(LocalDate.of(1998, 1, 18))
                    .gender(PatientGender.FEMALE)
                    .phone("0934567004")
                    .email("phamthuha98@gmail.com")
                    .address("Số 88 Trần Duy Hưng, Trung Hòa, Cầu Giấy, Hà Nội")
                    .insuranceId("BHYT-1000000004")
                    .bloodType(PatientBlood.AB)
                    .emergencyContactName("Phạm Văn Tuấn")
                    .emergencyContactPhone("0989010004")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Hoàng Đức Minh")
                    .dateOfBirth(LocalDate.of(1978, 9, 30))
                    .gender(PatientGender.MALE)
                    .phone("0978901005")
                    .email("minh.hoang78@gmail.com")
                    .address("Số 56 Đại Cồ Việt, Hai Bà Trưng, Hà Nội")
                    .insuranceId("BHYT-1000000005")
                    .bloodType(PatientBlood.O)
                    .emergencyContactName("Đỗ Thị Ngọc")
                    .emergencyContactPhone("0904320005")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Đỗ Bích Ngọc")
                    .dateOfBirth(LocalDate.of(2001, 4, 12))
                    .gender(PatientGender.FEMALE)
                    .phone("0945678006")
                    .email("bngoc.do2001@gmail.com")
                    .address("Số 23 Quang Trung, Hà Đông, Hà Nội")
                    .insuranceId("BHYT-1000000006")
                    .bloodType(PatientBlood.A)
                    .emergencyContactName("Đỗ Văn Thắng")
                    .emergencyContactPhone("0912340006")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Vũ Quốc Bảo")
                    .dateOfBirth(LocalDate.of(1992, 12, 25))
                    .gender(PatientGender.MALE)
                    .phone("0967890007")
                    .email("quocbao.vu92@gmail.com")
                    .address("Số 15 Hàng Bài, Hoàn Kiếm, Hà Nội")
                    .insuranceId("BHYT-1000000007")
                    .bloodType(PatientBlood.B)
                    .emergencyContactName("Vũ Thị Hương")
                    .emergencyContactPhone("0978650007")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Bùi Thị Lan Anh")
                    .dateOfBirth(LocalDate.of(1996, 6, 8))
                    .gender(PatientGender.FEMALE)
                    .phone("0918765008")
                    .email("lananh.bui96@gmail.com")
                    .address("Số 78 Giải Phóng, Phương Mai, Đống Đa, Hà Nội")
                    .insuranceId("BHYT-1000000008")
                    .bloodType(PatientBlood.O)
                    .emergencyContactName("Bùi Quang Huy")
                    .emergencyContactPhone("0932100008")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Ngô Tiến Dũng")
                    .dateOfBirth(LocalDate.of(1982, 8, 14))
                    .gender(PatientGender.MALE)
                    .phone("0983456009")
                    .email("ngotiendung.ptit@gmail.com")
                    .address("Số 34 Kim Mã, Ba Đình, Hà Nội")
                    .insuranceId("BHYT-1000000009")
                    .bloodType(PatientBlood.AB)
                    .emergencyContactName("Nguyễn Thị Thủy")
                    .emergencyContactPhone("0909870009")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Đặng Thùy Linh")
                    .dateOfBirth(LocalDate.of(1999, 10, 20))
                    .gender(PatientGender.FEMALE)
                    .phone("0936789010")
                    .email("linhdang99@gmail.com")
                    .address("Số 92 Láng Hạ, Đống Đa, Hà Nội")
                    .insuranceId("BHYT-1000000010")
                    .bloodType(PatientBlood.A)
                    .emergencyContactName("Đặng Văn Sơn")
                    .emergencyContactPhone("0945120010")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Trịnh Công Minh")
                    .dateOfBirth(LocalDate.of(1994, 2, 28))
                    .gender(PatientGender.MALE)
                    .phone("0971234011")
                    .email("congminh.trinh@gmail.com")
                    .address("Số 14 Lê Văn Lương, Thanh Xuân, Hà Nội")
                    .insuranceId("BHYT-1000000011")
                    .bloodType(PatientBlood.O)
                    .emergencyContactName("Trịnh Thị Oanh")
                    .emergencyContactPhone("0981230011")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Nguyễn Khánh Ly")
                    .dateOfBirth(LocalDate.of(2003, 5, 19))
                    .gender(PatientGender.FEMALE)
                    .phone("0949876012")
                    .email("khanhly.nguyen03@gmail.com")
                    .address("Số 67 Nguyễn Khang, Yên Hòa, Cầu Giấy, Hà Nội")
                    .insuranceId("BHYT-1000000012")
                    .bloodType(PatientBlood.B)
                    .emergencyContactName("Nguyễn Văn Phong")
                    .emergencyContactPhone("0914560012")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Phan Tuấn Anh")
                    .dateOfBirth(LocalDate.of(1989, 3, 3))
                    .gender(PatientGender.MALE)
                    .phone("0913456013")
                    .email("phantuananh89@gmail.com")
                    .address("Số 210 Xã Đàn, Nam Đồng, Đống Đa, Hà Nội")
                    .insuranceId("BHYT-1000000013")
                    .bloodType(PatientBlood.O)
                    .emergencyContactName("Phan Thị Hải")
                    .emergencyContactPhone("0967890013")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Lâm Mỹ Duyên")
                    .dateOfBirth(LocalDate.of(1997, 9, 17))
                    .gender(PatientGender.FEMALE)
                    .phone("0984567014")
                    .email("myduyen.lam97@gmail.com")
                    .address("Số 18 Huỳnh Thúc Kháng, Láng Hạ, Đống Đa, Hà Nội")
                    .insuranceId("BHYT-1000000014")
                    .bloodType(PatientBlood.A)
                    .emergencyContactName("Lâm Văn Thành")
                    .emergencyContactPhone("0938900014")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Võ Nhật Nam")
                    .dateOfBirth(LocalDate.of(1991, 11, 23))
                    .gender(PatientGender.MALE)
                    .phone("0908765015")
                    .email("nhatnam.vo91@gmail.com")
                    .address("Số 52 Hoàng Hoa Thám, Ba Đình, Hà Nội")
                    .insuranceId("BHYT-1000000015")
                    .bloodType(PatientBlood.UNKNOWN)
                    .emergencyContactName("Võ Thị Hoa")
                    .emergencyContactPhone("0976120015")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Dương Hải Yến")
                    .dateOfBirth(LocalDate.of(2000, 8, 8))
                    .gender(PatientGender.FEMALE)
                    .phone("0939012016")
                    .email("haiyen.duong00@gmail.com")
                    .address("Số 31 Võ Chí Công, Xuân La, Tây Hồ, Hà Nội")
                    .insuranceId("BHYT-1000000016")
                    .bloodType(PatientBlood.B)
                    .emergencyContactName("Dương Văn Long")
                    .emergencyContactPhone("0943210016")
                    .isDeleted(false)
                    .build(),

                Patient.builder()
                    .fullName("Mai Trung Kiên")
                    .dateOfBirth(LocalDate.of(1993, 1, 7))
                    .gender(PatientGender.MALE)
                    .phone("0975678017")
                    .email("trungkien.mai93@gmail.com")
                    .address("Số 120 Hoàng Quốc Việt, Cổ Nhuế, Bắc Từ Liêm, Hà Nội")
                    .insuranceId("BHYT-1000000017")
                    .bloodType(PatientBlood.O)
                    .emergencyContactName("Mai Thị Loan")
                    .emergencyContactPhone("0917890017")
                    .isDeleted(false)
                    .build(),

                // 3 bệnh nhân đã bị xóa mềm (isDeleted = true) để kiểm thử chức năng danh sách xóa mềm và khôi phục
                Patient.builder()
                    .fullName("Đoàn Thanh Tùng")
                    .dateOfBirth(LocalDate.of(1987, 6, 16))
                    .gender(PatientGender.MALE)
                    .phone("0919876018")
                    .email("thanhtung.doan87@gmail.com")
                    .address("Số 48 Trường Chinh, Khương Mai, Thanh Xuân, Hà Nội")
                    .insuranceId("BHYT-1000000018")
                    .bloodType(PatientBlood.A)
                    .emergencyContactName("Đoàn Văn Hậu")
                    .emergencyContactPhone("0982340018")
                    .isDeleted(true)
                    .build(),

                Patient.builder()
                    .fullName("Lý Thanh Nga")
                    .dateOfBirth(LocalDate.of(1996, 12, 1))
                    .gender(PatientGender.FEMALE)
                    .phone("0987123019")
                    .email("thanhnga.ly96@gmail.com")
                    .address("Số 83 Bạch Mai, Cầu Dền, Hai Bà Trưng, Hà Nội")
                    .insuranceId("BHYT-1000000019")
                    .bloodType(PatientBlood.AB)
                    .emergencyContactName("Lý Văn Phúc")
                    .emergencyContactPhone("0935670019")
                    .isDeleted(true)
                    .build(),

                Patient.builder()
                    .fullName("Đinh Hữu Nghĩa")
                    .dateOfBirth(LocalDate.of(2002, 7, 29))
                    .gender(PatientGender.OTHER)
                    .phone("0905432020")
                    .email("huunghia.dinh02@gmail.com")
                    .address("Số 19 Nguyễn Khánh Toàn, Quan Hoa, Cầu Giấy, Hà Nội")
                    .insuranceId("BHYT-1000000020")
                    .bloodType(PatientBlood.O)
                    .emergencyContactName("Đinh Thị Lệ")
                    .emergencyContactPhone("0974560020")
                    .isDeleted(true)
                    .build()
            );

            patientRepository.saveAll(patients);
        }
    }
}
