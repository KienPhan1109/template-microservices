package com.ptit.medicare_doctor_service.configuration;

import com.ptit.medicare_doctor_service.entity.Department;
import com.ptit.medicare_doctor_service.entity.Doctor;
import com.ptit.medicare_doctor_service.enums.DepartmentStatus;
import com.ptit.medicare_doctor_service.enums.DoctorGender;
import com.ptit.medicare_doctor_service.enums.DoctorStatus;
import com.ptit.medicare_doctor_service.repository.DepartmentRepository;
import com.ptit.medicare_doctor_service.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println(">>> [DATA INITIALIZER] Checking database: departments=" + departmentRepository.count() + ", doctors=" + doctorRepository.count());

        // 1. Nạp 30 chuyên khoa bệnh viện nếu chưa có
        if (departmentRepository.count() == 0) {
            System.out.println(">>> [DATA INITIALIZER] Seeding 30 departments...");
            List<Department> departments = List.of(
                Department.builder().departmentCode("CARDIOLOGY").departmentName("Khoa Tim mạch").location("Floor 3 - Building A").phone("024-3825-0001").description("Chuyên sâu tim mạch can thiệp, mạch vành, rối loạn nhịp").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("GASTROENTEROLOGY").departmentName("Khoa Nội tiêu hóa").location("Floor 4 - Building A").phone("024-3825-0002").description("Nội soi tiêu hóa can thiệp, bệnh lý gan mật tụy").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("PEDIATRICS").departmentName("Khoa Nhi").location("Floor 2 - Building B").phone("024-3825-0003").description("Khám chữa bệnh nội nhi, hô hấp, tiêm chủng trẻ em").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("OTOLARYNGOLOGY").departmentName("Khoa Tai Mũi Họng").location("Floor 5 - Building A").phone("024-3825-0004").description("Nội soi vi phẫu xoang, thanh quản, nạo VA, vá nhĩ").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("OPHTHALMOLOGY").departmentName("Khoa Mắt").location("Floor 5 - Building B").phone("024-3825-0005").description("Phẫu thuật Phaco, điều trị tật khúc xạ, võng mạc").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("ORTHOPEDICS").departmentName("Khoa Chấn thương chỉnh hình").location("Floor 3 - Building C").phone("024-3825-0006").description("Phẫu thuật thay khớp nhân tạo, nội soi khớp, kết hợp xương").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("DERMATOLOGY").departmentName("Khoa Da liễu").location("Floor 4 - Building B").phone("024-3825-0007").description("Da liễu lâm sàng, laser công nghệ cao và thẩm mỹ da").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("OBSTETRICS_GYNECOLOGY").departmentName("Khoa Sản phụ khoa").location("Floor 2 - Building C").phone("024-3825-0008").description("Chăm sóc thai kỳ, đỡ đẻ, phẫu thuật phụ khoa nội soi").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("NEUROSURGERY").departmentName("Khoa Ngoại thần kinh").location("Floor 6 - Building C").phone("024-3825-0009").description("Vi phẫu sọ não, mạch máu não và chấn thương cột sống").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("NEUROLOGY").departmentName("Khoa Thần kinh").location("Floor 6 - Building A").phone("024-3825-0010").description("Nội thần kinh, đột quỵ não, động kinh, mất ngủ").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("ENDOCRINOLOGY").departmentName("Khoa Nội tiết").location("Floor 7 - Building A").phone("024-3825-0011").description("Điều trị đái tháo đường, Basedow, rối loạn chuyển hóa").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("EMERGENCY").departmentName("Khoa Cấp cứu & Hồi sức tích cực").location("Floor 1 - Building C").phone("024-3825-0012").description("Tiếp nhận cấp cứu 24/7, hồi sức sốc, suy hô hấp cấp").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("ODONTO_STOMATOLOGY").departmentName("Khoa Răng Hàm Mặt").location("Floor 4 - Building C").phone("024-3825-0013").description("Cấy ghép Implant, chỉnh nha, phẫu thuật hàm mặt").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("ONCOLOGY").departmentName("Khoa Ung bướu").location("Floor 8 - Building C").phone("024-3825-0014").description("Hóa trị, xạ trị và phẫu thuật u bướu đa mô thức").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("DIAGNOSTIC_IMAGING").departmentName("Khoa Chẩn đoán hình ảnh").location("Floor 1 - Building A").phone("024-3825-0015").description("Chụp MRI 3.0T, CT Scanner 128 lát cắt, siêu âm màu Doppler").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("INTERNAL_MEDICINE").departmentName("Khoa Nội tổng hợp").location("Floor 7 - Building B").phone("024-3825-0016").description("Khám và điều trị toàn diện các bệnh lý nội khoa phức tạp").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("GENERAL_SURGERY").departmentName("Khoa Ngoại tổng quát").location("Floor 3 - Building B").phone("024-3825-0017").description("Phẫu thuật nội soi ổ bụng, gan mật tụy, thoát vị bẹn").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("PULMONOLOGY").departmentName("Khoa Hô hấp").location("Floor 8 - Building A").phone("024-3825-0018").description("Điều trị bệnh phổi tắc nghẽn mãn tính COPD, hen suyễn").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("HEMATOLOGY").departmentName("Khoa Huyết học - Truyền máu").location("Floor 9 - Building A").phone("024-3825-0019").description("Xét nghiệm huyết học chuyên sâu, ngân hàng máu an toàn").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("INFECTIOUS_DISEASES").departmentName("Khoa Truyền nhiễm").location("Floor 1 - Building D").phone("024-3825-0020").description("Điều trị các bệnh truyền nhiễm, sốt xuất huyết, cúm mùa").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("NEPHROLOGY_UROLOGY").departmentName("Khoa Thận - Tiết niệu").location("Floor 9 - Building B").phone("024-3825-0021").description("Chạy thận nhân tạo, tán sỏi tiết niệu qua da, u xơ tuyến tiền liệt").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("REHABILITATION").departmentName("Khoa Phục hồi chức năng").location("Floor 2 - Building A").phone("024-3825-0022").description("Vật lý trị liệu sau đột quỵ não, tai nạn chấn thương").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("PSYCHIATRY").departmentName("Khoa Sức khỏe tâm thần").location("Floor 10 - Building A").phone("024-3825-0023").description("Tư vấn tâm lý, điều trị trầm cảm, rối loạn lo âu, mất ngủ").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("ANESTHESIOLOGY").departmentName("Khoa Gây mê hồi sức").location("Floor 5 - Building C").phone("024-3825-0024").description("Gây mê phẫu thuật an toàn, kiểm soát đau đa mô thức").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("NUTRITION").departmentName("Khoa Dinh dưỡng lâm sàng").location("Floor 1 - Building B").phone("024-3825-0025").description("Tư vấn dinh dưỡng chuyên biệt cho từng mặt bệnh lý").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("ALLERGY_IMMUNOLOGY").departmentName("Khoa Dị ứng - Miễn dịch").location("Floor 7 - Building C").phone("024-3825-0026").description("Giải mẫn cảm đặc hiệu, dị ứng thuốc và bệnh tự miễn").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("GERIATRICS").departmentName("Khoa Lão khoa").location("Floor 10 - Building B").phone("024-3825-0027").description("Chăm sóc toàn diện đa bệnh lý ở người cao tuổi").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("TRADITIONAL_MEDICINE").departmentName("Khoa Y học cổ truyền").location("Floor 6 - Building B").phone("024-3825-0028").description("Châm cứu, cứu ngải, xoa bóp bấm huyệt kết hợp thảo dược").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("PLASTIC_SURGERY").departmentName("Khoa Phẫu thuật tạo hình").location("Floor 8 - Building B").phone("024-3825-0029").description("Phẫu thuật tái tạo dị tật bẩm sinh và vi phẫu thẩm mỹ").status(DepartmentStatus.ACTIVE).isDeleted(false).build(),
                Department.builder().departmentCode("PATHOLOGY").departmentName("Khoa Giải phẫu bệnh").location("Basement - Building A").phone("024-3825-0030").description("Xét nghiệm sinh thiết mô bệnh học tế bào chẩn đoán ung thư").status(DepartmentStatus.ACTIVE).isDeleted(false).build()
            );

            departmentRepository.saveAllAndFlush(departments);
            System.out.println(">>> [DATA INITIALIZER] Saved departments count: " + departments.size());
        }

        // 2. Nạp 20 bác sĩ mẫu liên kết với các chuyên khoa
        if (doctorRepository.count() == 0) {
            Map<String, Department> deptMap = new HashMap<>();
            departmentRepository.findAll().forEach(dept -> deptMap.put(dept.getDepartmentCode(), dept));

            List<Doctor> doctors = List.of(
                Doctor.builder()
                    .licenseNumber("CCHN-001001/BYT")
                    .fullName("PGS.TS. Trần Quốc Hùng")
                    .dateOfBirth(LocalDate.of(1970, 4, 15))
                    .gender(DoctorGender.MALE)
                    .phone("0912111001")
                    .email("hung.tran@medicare.vn")
                    .address("Số 15 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội")
                    .degree("PGS.TS")
                    .department(deptMap.get("CARDIOLOGY"))
                    .experienceYears(28)
                    .bio("Chuyên gia đầu ngành về can thiệp tim mạch và bệnh lý mạch vành, nguyên Trưởng khoa Tim mạch BV Bạch Mai.")
                    .consultationFee(new BigDecimal("500000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001002/BYT")
                    .fullName("TS.BS. Lê Thị Lan Phương")
                    .dateOfBirth(LocalDate.of(1978, 8, 20))
                    .gender(DoctorGender.FEMALE)
                    .phone("0982111002")
                    .email("phuong.le@medicare.vn")
                    .address("Số 24 Láng Hạ, Đống Đa, Hà Nội")
                    .degree("TS.BS")
                    .department(deptMap.get("GASTROENTEROLOGY"))
                    .experienceYears(20)
                    .bio("Tiến sĩ Y khoa Đại học Y Hà Nội, chuyên sâu nội soi tiêu hóa can thiệp và điều trị viêm gan virus.")
                    .consultationFee(new BigDecimal("400000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001003/BYT")
                    .fullName("BS.CKII. Nguyễn Văn Minh")
                    .dateOfBirth(LocalDate.of(1975, 11, 2))
                    .gender(DoctorGender.MALE)
                    .phone("0903111003")
                    .email("minh.nguyen@medicare.vn")
                    .address("Số 88 Trần Duy Hưng, Cầu Giấy, Hà Nội")
                    .degree("BS.CKII")
                    .department(deptMap.get("PEDIATRICS"))
                    .experienceYears(22)
                    .bio("Bác sĩ Chuyên khoa II Nhi, nguyên Phó Giám đốc BV Nhi Trung ương, giàu kinh nghiệm hô hấp nhi.")
                    .consultationFee(new BigDecimal("350000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001004/BYT")
                    .fullName("ThS.BS. Hoàng Thu Trang")
                    .dateOfBirth(LocalDate.of(1986, 6, 12))
                    .gender(DoctorGender.FEMALE)
                    .phone("0934111004")
                    .email("trang.hoang@medicare.vn")
                    .address("Số 56 Kim Mã, Ba Đình, Hà Nội")
                    .degree("ThS.BS")
                    .department(deptMap.get("OTOLARYNGOLOGY"))
                    .experienceYears(13)
                    .bio("Thạc sĩ Tai Mũi Họng, tu nghiệp tại CH Pháp, chuyên phẫu thuật nội soi xoang và thanh quản.")
                    .consultationFee(new BigDecimal("300000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001005/BYT")
                    .fullName("BS.CKI. Đặng Văn Nam")
                    .dateOfBirth(LocalDate.of(1984, 2, 28))
                    .gender(DoctorGender.MALE)
                    .phone("0978111005")
                    .email("nam.dang@medicare.vn")
                    .address("Số 102 Giải Phóng, Hai Bà Trưng, Hà Nội")
                    .degree("BS.CKI")
                    .department(deptMap.get("OPHTHALMOLOGY"))
                    .experienceYears(15)
                    .bio("Bác sĩ Chuyên khoa I Nhãn khoa, chuyên gia phẫu thuật Phaco điều trị đục thủy tinh thể và tật khúc xạ.")
                    .consultationFee(new BigDecimal("250000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001006/BYT")
                    .fullName("TS.BS. Vũ Quốc Huy")
                    .dateOfBirth(LocalDate.of(1976, 9, 18))
                    .gender(DoctorGender.MALE)
                    .phone("0945111006")
                    .email("huy.vu@medicare.vn")
                    .address("Số 45 Hoàng Hoa Thám, Ba Đình, Hà Nội")
                    .degree("TS.BS")
                    .department(deptMap.get("ORTHOPEDICS"))
                    .experienceYears(21)
                    .bio("Tiến sĩ Phẫu thuật Chỉnh hình, chuyên phẫu thuật thay khớp háng, khớp gối nhân tạo và nội soi khớp.")
                    .consultationFee(new BigDecimal("450000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001007/BYT")
                    .fullName("BS.CKII. Phạm Mai Hương")
                    .dateOfBirth(LocalDate.of(1980, 5, 25))
                    .gender(DoctorGender.FEMALE)
                    .phone("0967111007")
                    .email("huong.pham@medicare.vn")
                    .address("Số 33 Nguyễn Trãi, Thanh Xuân, Hà Nội")
                    .degree("BS.CKII")
                    .department(deptMap.get("DERMATOLOGY"))
                    .experienceYears(18)
                    .bio("Bác sĩ Da liễu thẩm mỹ, chuyên điều trị mụn trứng cá, nám, sẹo rỗ và các bệnh da tự miễn.")
                    .consultationFee(new BigDecimal("300000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001008/BYT")
                    .fullName("ThS.BS. Bùi Đình Tuấn")
                    .dateOfBirth(LocalDate.of(1987, 10, 10))
                    .gender(DoctorGender.MALE)
                    .phone("0918111008")
                    .email("tuan.bui@medicare.vn")
                    .address("Số 78 Cầu Giấy, Dịch Vọng, Cầu Giấy, Hà Nội")
                    .degree("ThS.BS")
                    .department(deptMap.get("OBSTETRICS_GYNECOLOGY"))
                    .experienceYears(11)
                    .bio("Thạc sĩ Sản phụ khoa BV Phụ sản Trung ương, chuyên siêu âm dị tật thai và chẩn đoán trước sinh.")
                    .consultationFee(new BigDecimal("280000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001009/BYT")
                    .fullName("TS.BS. Ngô Quang Hải")
                    .dateOfBirth(LocalDate.of(1973, 1, 30))
                    .gender(DoctorGender.MALE)
                    .phone("0983111009")
                    .email("hai.ngo@medicare.vn")
                    .address("Số 14 Lê Văn Lương, Nhân Chính, Thanh Xuân, Hà Nội")
                    .degree("TS.BS")
                    .department(deptMap.get("NEUROSURGERY"))
                    .experienceYears(25)
                    .bio("Chuyên gia phẫu thuật vi phẫu u não, phình mạch não và chấn thương sọ não, BV Việt Đức.")
                    .consultationFee(new BigDecimal("500000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001010/BYT")
                    .fullName("BS.CKI. Đỗ Ngọc Ánh")
                    .dateOfBirth(LocalDate.of(1989, 7, 14))
                    .gender(DoctorGender.FEMALE)
                    .phone("0936111010")
                    .email("anh.do@medicare.vn")
                    .address("Số 90 Nguyễn Tuân, Thanh Xuân Trung, Thanh Xuân, Hà Nội")
                    .degree("BS.CKI")
                    .department(deptMap.get("ENDOCRINOLOGY"))
                    .experienceYears(10)
                    .bio("Bác sĩ Nội tiết, chuyên sâu theo dõi điều trị đái tháo đường thai kỳ, suy giáp và Basedow.")
                    .consultationFee(new BigDecimal("200000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001011/BYT")
                    .fullName("BS. Trịnh Đình Khôi")
                    .dateOfBirth(LocalDate.of(1993, 3, 21))
                    .gender(DoctorGender.MALE)
                    .phone("0971111011")
                    .email("khoi.trinh@medicare.vn")
                    .address("Số 210 Xã Đàn, Đống Đa, Hà Nội")
                    .degree("BS")
                    .department(deptMap.get("EMERGENCY"))
                    .experienceYears(6)
                    .bio("Bác sĩ Cấp cứu và Hồi sức tích cực, chuyên xử trí sốc phản vệ, suy hô hấp cấp và ngộ độc cấp.")
                    .consultationFee(new BigDecimal("180000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001012/BYT")
                    .fullName("ThS.BS. Phan Thị Cẩm Nhung")
                    .dateOfBirth(LocalDate.of(1985, 12, 5))
                    .gender(DoctorGender.FEMALE)
                    .phone("0949111012")
                    .email("nhung.phan@medicare.vn")
                    .address("Số 67 Huỳnh Thúc Kháng, Đống Đa, Hà Nội")
                    .degree("ThS.BS")
                    .department(deptMap.get("ODONTO_STOMATOLOGY"))
                    .experienceYears(14)
                    .bio("Thạc sĩ Răng Hàm Mặt, chuyên cấy ghép Implant nha khoa công nghệ cao và chỉnh nha niềng răng trong suốt.")
                    .consultationFee(new BigDecimal("300000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001013/BYT")
                    .fullName("BS.CKII. Lâm Thế Bảo")
                    .dateOfBirth(LocalDate.of(1977, 8, 9))
                    .gender(DoctorGender.MALE)
                    .phone("0913111013")
                    .email("bao.lam@medicare.vn")
                    .address("Số 112 Chùa Láng, Láng Thượng, Đống Đa, Hà Nội")
                    .degree("BS.CKII")
                    .department(deptMap.get("ONCOLOGY"))
                    .experienceYears(22)
                    .bio("Chuyên gia Phẫu thuật Ung bướu và Hóa xạ trị bệnh lý đường tiêu hóa, gan mật tụy.")
                    .consultationFee(new BigDecimal("450000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001014/BYT")
                    .fullName("TS.BS. Dương Khánh Toàn")
                    .dateOfBirth(LocalDate.of(1981, 4, 19))
                    .gender(DoctorGender.MALE)
                    .phone("0984111014")
                    .email("toan.duong@medicare.vn")
                    .address("Số 35 Phố Huế, Hàng Bài, Hoàn Kiếm, Hà Nội")
                    .degree("TS.BS")
                    .department(deptMap.get("DIAGNOSTIC_IMAGING"))
                    .experienceYears(17)
                    .bio("Tiến sĩ Chẩn đoán hình ảnh, chuyên đọc phim MRI 3.0 Tesla và CT 128 lát cắt các bệnh lý sọ não - mạch máu.")
                    .consultationFee(new BigDecimal("350000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001015/BYT")
                    .fullName("BS.CKI. Võ Ngọc Bích")
                    .dateOfBirth(LocalDate.of(1990, 11, 15))
                    .gender(DoctorGender.FEMALE)
                    .phone("0908111015")
                    .email("bich.vo@medicare.vn")
                    .address("Số 18 Võ Chí Công, Tây Hồ, Hà Nội")
                    .degree("BS.CKI")
                    .department(deptMap.get("CARDIOLOGY"))
                    .experienceYears(8)
                    .bio("Bác sĩ Chuyên khoa I Nội Tim mạch, chuyên thăm dò điện sinh lý tim và siêu âm tim Doppler màu qua thành ngực.")
                    .consultationFee(new BigDecimal("220000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001016/BYT")
                    .fullName("BS. Mai Anh Vũ")
                    .dateOfBirth(LocalDate.of(1994, 7, 7))
                    .gender(DoctorGender.MALE)
                    .phone("0939111016")
                    .email("vu.mai@medicare.vn")
                    .address("Số 82 Hoàng Quốc Việt, Cầu Giấy, Hà Nội")
                    .degree("BS")
                    .department(deptMap.get("PEDIATRICS"))
                    .experienceYears(5)
                    .bio("Bác sĩ Nội Nhi, tốt nghiệp Thủ khoa Bác sĩ Đa khoa ĐH Y Dược, chuyên tư vấn dinh dưỡng và tiêm chủng.")
                    .consultationFee(new BigDecimal("150000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001017/BYT")
                    .fullName("ThS.BS. Đinh Quốc Thái")
                    .dateOfBirth(LocalDate.of(1988, 1, 23))
                    .gender(DoctorGender.MALE)
                    .phone("0975111017")
                    .email("thai.dinh@medicare.vn")
                    .address("Số 42 Bà Triệu, Tràng Tiền, Hoàn Kiếm, Hà Nội")
                    .degree("ThS.BS")
                    .department(deptMap.get("OTOLARYNGOLOGY"))
                    .experienceYears(11)
                    .bio("Thạc sĩ Tai Mũi Họng BV Tai Mũi Họng Trung ương, chuyên phẫu thuật vá nhĩ và nạo VA bằng Coblator.")
                    .consultationFee(new BigDecimal("250000.00"))
                    .status(DoctorStatus.ACTIVE)
                    .isDeleted(false)
                    .build(),

                // 3 bác sĩ đã bị xóa mềm (isDeleted = true)
                Doctor.builder()
                    .licenseNumber("CCHN-001018/BYT")
                    .fullName("BS.CKI. Nguyễn Thành Trung")
                    .dateOfBirth(LocalDate.of(1982, 5, 14))
                    .gender(DoctorGender.MALE)
                    .phone("0919111018")
                    .email("trung.nguyen82@medicare.vn")
                    .address("Số 68 Trường Chinh, Đống Đa, Hà Nội")
                    .degree("BS.CKI")
                    .department(deptMap.get("ORTHOPEDICS"))
                    .experienceYears(16)
                    .bio("Bác sĩ Chuyên khoa I Phục hồi chức năng và chấn thương thể thao.")
                    .consultationFee(new BigDecimal("200000.00"))
                    .status(DoctorStatus.RESIGNED)
                    .isDeleted(true)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001019/BYT")
                    .fullName("ThS.BS. Chu Hải Đăng")
                    .dateOfBirth(LocalDate.of(1989, 9, 30))
                    .gender(DoctorGender.MALE)
                    .phone("0987111019")
                    .email("dang.chu@medicare.vn")
                    .address("Số 99 Bạch Mai, Hai Bà Trưng, Hà Nội")
                    .degree("ThS.BS")
                    .department(deptMap.get("GASTROENTEROLOGY"))
                    .experienceYears(9)
                    .bio("Thạc sĩ Nội khoa tiêu hóa, đã tạm ngừng công tác để học nâng cao tại nước ngoài.")
                    .consultationFee(new BigDecimal("220000.00"))
                    .status(DoctorStatus.ON_LEAVE)
                    .isDeleted(true)
                    .build(),

                Doctor.builder()
                    .licenseNumber("CCHN-001020/BYT")
                    .fullName("BS. Lý Kim Oanh")
                    .dateOfBirth(LocalDate.of(1992, 10, 8))
                    .gender(DoctorGender.FEMALE)
                    .phone("0905111020")
                    .email("oanh.ly@medicare.vn")
                    .address("Số 25 Thụy Khuê, Tây Hồ, Hà Nội")
                    .degree("BS")
                    .department(deptMap.get("OPHTHALMOLOGY"))
                    .experienceYears(7)
                    .bio("Bác sĩ Đa khoa định hướng Nhãn khoa, đã chuyển công tác sang cơ sở y tế khác.")
                    .consultationFee(new BigDecimal("160000.00"))
                    .status(DoctorStatus.RESIGNED)
                    .isDeleted(true)
                    .build()
            );

            doctorRepository.saveAllAndFlush(doctors);
            System.out.println(">>> [DATA INITIALIZER] Saved doctors count: " + doctors.size());
        }
    }
}
