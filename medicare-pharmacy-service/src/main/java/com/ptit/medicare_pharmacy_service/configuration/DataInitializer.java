package com.ptit.medicare_pharmacy_service.configuration;

import com.ptit.medicare_pharmacy_service.entity.Medicine;
import com.ptit.medicare_pharmacy_service.entity.Prescription;
import com.ptit.medicare_pharmacy_service.entity.PrescriptionItem;
import com.ptit.medicare_pharmacy_service.enums.MedicineStatus;
import com.ptit.medicare_pharmacy_service.enums.PrescriptionStatus;
import com.ptit.medicare_pharmacy_service.repository.MedicineRepository;
import com.ptit.medicare_pharmacy_service.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MedicineRepository medicineRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (medicineRepository.count() == 0) {
            initMedicinesAndPrescriptions();
        }
    }

    private void initMedicinesAndPrescriptions() {
        // Khởi tạo danh mục 20 loại thuốc thực tế
        List<Medicine> medicines = List.of(
                Medicine.builder()
                        .medicineCode("MED-AML-05")
                        .registrationNumber("VN-18234-14")
                        .name("Amlor 5mg")
                        .activeIngredient("Amlodipine")
                        .category("Hạ huyết áp")
                        .dosageForm("Viên nang cứng")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("8500.00"))
                        .stockQuantity(500)
                        .expiryDate(LocalDate.of(2028, 12, 31))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-AUG-1000")
                        .registrationNumber("VN-21045-18")
                        .name("Augmentin 1g")
                        .activeIngredient("Amoxicillin 875mg + Acid clavulanic 125mg")
                        .category("Kháng sinh")
                        .dosageForm("Viên nén bao phim")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("18000.00"))
                        .stockQuantity(350)
                        .expiryDate(LocalDate.of(2028, 6, 30))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-PAN-500")
                        .registrationNumber("VD-24156-16")
                        .name("Panadol Extra")
                        .activeIngredient("Paracetamol 500mg + Caffeine 65mg")
                        .category("Giảm đau hạ sốt")
                        .dosageForm("Viên nén")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("2500.00"))
                        .stockQuantity(1000)
                        .expiryDate(LocalDate.of(2029, 1, 15))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-NEX-40")
                        .registrationNumber("VN-19876-16")
                        .name("Nexium Mups 40mg")
                        .activeIngredient("Esomeprazole")
                        .category("Tiêu hóa - Kháng acid")
                        .dosageForm("Viên nén kháng dịch dạ dày")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("24000.00"))
                        .stockQuantity(400)
                        .expiryDate(LocalDate.of(2028, 9, 20))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-GLU-850")
                        .registrationNumber("VN-17654-13")
                        .name("Glucophage 850mg")
                        .activeIngredient("Metformin hydrochloride")
                        .category("Đái tháo đường")
                        .dosageForm("Viên nén bao phim")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("5500.00"))
                        .stockQuantity(600)
                        .expiryDate(LocalDate.of(2028, 11, 10))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-ZIN-500")
                        .registrationNumber("VN-22110-19")
                        .name("Zinnat 500mg")
                        .activeIngredient("Cefuroxime axetil")
                        .category("Kháng sinh")
                        .dosageForm("Viên nén bao phim")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("26000.00"))
                        .stockQuantity(250)
                        .expiryDate(LocalDate.of(2028, 5, 18))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-CEL-200")
                        .registrationNumber("VN-20987-17")
                        .name("Celebrex 200mg")
                        .activeIngredient("Celecoxib")
                        .category("Kháng viêm giảm đau")
                        .dosageForm("Viên nang cứng")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("16500.00"))
                        .stockQuantity(300)
                        .expiryDate(LocalDate.of(2028, 8, 12))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-KLE-01")
                        .registrationNumber("VN-15432-12")
                        .name("Klenzit-MS")
                        .activeIngredient("Adapalene 0.1%")
                        .category("Da liễu")
                        .dosageForm("Gel bôi ngoài da")
                        .unit("Tuýp")
                        .unitPrice(new BigDecimal("110000.00"))
                        .stockQuantity(120)
                        .expiryDate(LocalDate.of(2027, 10, 30))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-VIA-1500")
                        .registrationNumber("VN-16789-13")
                        .name("Viartril-S 1500mg")
                        .activeIngredient("Glucosamine sulfate")
                        .category("Cơ xương khớp")
                        .dosageForm("Gói bột pha dung dịch")
                        .unit("Gói")
                        .unitPrice(new BigDecimal("17000.00"))
                        .stockQuantity(450)
                        .expiryDate(LocalDate.of(2028, 7, 25))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-SYS-10")
                        .registrationNumber("VN-18901-15")
                        .name("Systane Ultra 10ml")
                        .activeIngredient("Polyethylene glycol 400 + Propylene glycol")
                        .category("Thuốc nhỏ mắt")
                        .dosageForm("Dung dịch nhỏ mắt")
                        .unit("Lọ")
                        .unitPrice(new BigDecimal("85000.00"))
                        .stockQuantity(80)
                        .expiryDate(LocalDate.of(2027, 12, 15))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-MEP-16")
                        .registrationNumber("VN-19234-15")
                        .name("Medrol 16mg")
                        .activeIngredient("Methylprednisolone")
                        .category("Kháng viêm Corticoid")
                        .dosageForm("Viên nén")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("4200.00"))
                        .stockQuantity(500)
                        .expiryDate(LocalDate.of(2028, 4, 10))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-TEL-180")
                        .registrationNumber("VN-20123-16")
                        .name("Telfast HD 180mg")
                        .activeIngredient("Fexofenadine hydrochloride")
                        .category("Chống dị ứng")
                        .dosageForm("Viên nén bao phim")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("11500.00"))
                        .stockQuantity(300)
                        .expiryDate(LocalDate.of(2028, 10, 5))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-CON-25")
                        .registrationNumber("VN-18765-14")
                        .name("Concor 2.5mg")
                        .activeIngredient("Bisoprolol fumarate")
                        .category("Tim mạch")
                        .dosageForm("Viên nén bao phim")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("4800.00"))
                        .stockQuantity(400)
                        .expiryDate(LocalDate.of(2028, 3, 22))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-LIP-20")
                        .registrationNumber("VN-21345-18")
                        .name("Lipitor 20mg")
                        .activeIngredient("Atorvastatin calcium")
                        .category("Hạ mỡ máu")
                        .dosageForm("Viên nén bao phim")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("22000.00"))
                        .stockQuantity(350)
                        .expiryDate(LocalDate.of(2028, 9, 14))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-BER-500")
                        .registrationNumber("VD-25678-16")
                        .name("Berberin 500mg")
                        .activeIngredient("Berberin chloride")
                        .category("Tiêu hóa")
                        .dosageForm("Viên nén")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("1500.00"))
                        .stockQuantity(800)
                        .expiryDate(LocalDate.of(2029, 2, 28))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-EUC-100")
                        .registrationNumber("VD-28901-18")
                        .name("Eugica xanh")
                        .activeIngredient("Eucalyptol 100mg + Tinh dầu tràm")
                        .category("Hô hấp - Giảm ho")
                        .dosageForm("Viên nang mềm")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("1200.00"))
                        .stockQuantity(1200)
                        .expiryDate(LocalDate.of(2029, 5, 30))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-DOP-10")
                        .registrationNumber("VD-23456-15")
                        .name("Domperidone 10mg")
                        .activeIngredient("Domperidone maleate")
                        .category("Tiêu hóa - Chống nôn")
                        .dosageForm("Viên nén")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("2000.00"))
                        .stockQuantity(600)
                        .expiryDate(LocalDate.of(2028, 8, 8))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                Medicine.builder()
                        .medicineCode("MED-ASP-81")
                        .registrationNumber("VD-26789-17")
                        .name("Aspirin 81mg")
                        .activeIngredient("Acetylsalicylic acid")
                        .category("Tim mạch - Kháng kết tập tiểu cầu")
                        .dosageForm("Viên bao tan trong ruột")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("1800.00"))
                        .stockQuantity(700)
                        .expiryDate(LocalDate.of(2028, 11, 20))
                        .status(MedicineStatus.AVAILABLE)
                        .isDeleted(false)
                        .build(),

                // 19. Thuốc tạm hết hàng (stock = 0)
                Medicine.builder()
                        .medicineCode("MED-CLA-500")
                        .registrationNumber("VN-14567-12")
                        .name("Klacid 500mg")
                        .activeIngredient("Clarithromycin")
                        .category("Kháng sinh")
                        .dosageForm("Viên nén bao phim")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("32000.00"))
                        .stockQuantity(0)
                        .expiryDate(LocalDate.of(2028, 1, 10))
                        .status(MedicineStatus.OUT_OF_STOCK)
                        .isDeleted(false)
                        .build(),

                // 20. Thuốc ngừng lưu hành / đã xóa mềm
                Medicine.builder()
                        .medicineCode("MED-RAN-150")
                        .registrationNumber("VN-11223-10")
                        .name("Zantac 150mg")
                        .activeIngredient("Ranitidine hydrochloride")
                        .category("Tiêu hóa")
                        .dosageForm("Viên sủi")
                        .unit("Viên")
                        .unitPrice(new BigDecimal("6000.00"))
                        .stockQuantity(0)
                        .expiryDate(LocalDate.of(2025, 12, 31))
                        .status(MedicineStatus.DISCONTINUED)
                        .isDeleted(true)
                        .build()
        );

        List<Medicine> savedMedicines = medicineRepository.saveAll(medicines);

        // Khởi tạo các đơn thuốc mẫu chuẩn y khoa gắn kết với medical_id, patient_id, doctor_id
        Medicine medAmlor = savedMedicines.get(0);    // Amlor 5mg (8500)
        Medicine medAugmentin = savedMedicines.get(1);// Augmentin 1g (18000)
        Medicine medPanadol = savedMedicines.get(2);  // Panadol Extra (2500)
        Medicine medNexium = savedMedicines.get(3);   // Nexium 40mg (24000)
        Medicine medGlucophage = savedMedicines.get(4);// Glucophage 850mg (5500)
        Medicine medZinnat = savedMedicines.get(5);   // Zinnat 500mg (26000)
        Medicine medCelebrex = savedMedicines.get(6); // Celebrex 200mg (16500)
        Medicine medViartril = savedMedicines.get(8); // Viartril-S (17000)
        Medicine medTelfast = savedMedicines.get(11); // Telfast (11500)
        Medicine medConcor = savedMedicines.get(12);  // Concor (4800)
        Medicine medLipitor = savedMedicines.get(13); // Lipitor (22000)
        Medicine medAspirin = savedMedicines.get(17); // Aspirin 81mg (1800)

        List<Prescription> prescriptions = new ArrayList<>();

        // Đơn 1: Khám Tăng huyết áp (medical_id = 1, patient_id = 1, doctor_id = 1) -> PENDING
        Prescription rx1 = Prescription.builder()
                .prescriptionCode("RX202609010001")
                .medicalId(1L)
                .patientId(1L)
                .doctorId(1L)
                .notes("Uống thuốc đều đặn vào mỗi buổi sáng sau khi ăn.")
                .status(PrescriptionStatus.PENDING)
                .isDeleted(false)
                .items(new ArrayList<>())
                .build();
        rx1.addItem(PrescriptionItem.builder()
                .medicine(medAmlor)
                .quantity(30)
                .dosage("1 viên/lần/ngày")
                .instruction("Uống sáng sau ăn no")
                .unitPrice(medAmlor.getUnitPrice())
                .totalPrice(medAmlor.getUnitPrice().multiply(BigDecimal.valueOf(30)))
                .build());
        rx1.addItem(PrescriptionItem.builder()
                .medicine(medConcor)
                .quantity(30)
                .dosage("1 viên/lần/ngày")
                .instruction("Uống sáng cùng Amlor")
                .unitPrice(medConcor.getUnitPrice())
                .totalPrice(medConcor.getUnitPrice().multiply(BigDecimal.valueOf(30)))
                .build());
        prescriptions.add(rx1);

        // Đơn 2: Viêm dạ dày mạn tính (medical_id = 2, patient_id = 2, doctor_id = 2) -> PENDING
        Prescription rx2 = Prescription.builder()
                .prescriptionCode("RX202609010002")
                .medicalId(2L)
                .patientId(2L)
                .doctorId(2L)
                .notes("Kiêng chua cay, uống trước bữa ăn 30 phút.")
                .status(PrescriptionStatus.PENDING)
                .isDeleted(false)
                .items(new ArrayList<>())
                .build();
        rx2.addItem(PrescriptionItem.builder()
                .medicine(medNexium)
                .quantity(28)
                .dosage("1 viên/lần, ngày 2 lần")
                .instruction("Uống trước ăn sáng và ăn tối 30 phút")
                .unitPrice(medNexium.getUnitPrice())
                .totalPrice(medNexium.getUnitPrice().multiply(BigDecimal.valueOf(28)))
                .build());
        prescriptions.add(rx2);

        // Đơn 3: Viêm họng cấp (medical_id = 3, patient_id = 3, doctor_id = 3) -> DISPENSED (Đã phát thuốc)
        Prescription rx3 = Prescription.builder()
                .prescriptionCode("RX202609020001")
                .medicalId(3L)
                .patientId(3L)
                .doctorId(3L)
                .dispensedAt(LocalDateTime.of(2026, 9, 2, 10, 30, 0))
                .notes("Đã phát đủ thuốc tại quầy 1.")
                .status(PrescriptionStatus.DISPENSED)
                .isDeleted(false)
                .items(new ArrayList<>())
                .build();
        rx3.addItem(PrescriptionItem.builder()
                .medicine(medAugmentin)
                .quantity(14)
                .dosage("1 viên/lần, ngày 2 lần")
                .instruction("Uống ngay trước bữa ăn chính x 7 ngày")
                .unitPrice(medAugmentin.getUnitPrice())
                .totalPrice(medAugmentin.getUnitPrice().multiply(BigDecimal.valueOf(14)))
                .build());
        rx3.addItem(PrescriptionItem.builder()
                .medicine(medPanadol)
                .quantity(10)
                .dosage("1 viên khi sốt trên 38.5 độ")
                .instruction("Cách nhau tối thiểu 4-6 tiếng")
                .unitPrice(medPanadol.getUnitPrice())
                .totalPrice(medPanadol.getUnitPrice().multiply(BigDecimal.valueOf(10)))
                .build());
        prescriptions.add(rx3);

        // Đơn 4: Thoái hóa khớp gối (medical_id = 6, patient_id = 6, doctor_id = 6) -> PENDING
        Prescription rx4 = Prescription.builder()
                .prescriptionCode("RX202609030001")
                .medicalId(6L)
                .patientId(6L)
                .doctorId(6L)
                .notes("Dùng kéo dài 3 tháng, tập thể dục nhẹ nhàng.")
                .status(PrescriptionStatus.PENDING)
                .isDeleted(false)
                .items(new ArrayList<>())
                .build();
        rx4.addItem(PrescriptionItem.builder()
                .medicine(medCelebrex)
                .quantity(10)
                .dosage("1 viên/ngày")
                .instruction("Uống sau ăn no")
                .unitPrice(medCelebrex.getUnitPrice())
                .totalPrice(medCelebrex.getUnitPrice().multiply(BigDecimal.valueOf(10)))
                .build());
        rx4.addItem(PrescriptionItem.builder()
                .medicine(medViartril)
                .quantity(30)
                .dosage("1 gói/ngày")
                .instruction("Hòa tan với nửa ly nước ấm uống sau ăn")
                .unitPrice(medViartril.getUnitPrice())
                .totalPrice(medViartril.getUnitPrice().multiply(BigDecimal.valueOf(30)))
                .build());
        prescriptions.add(rx4);

        // Đơn 5: Đái tháo đường typ 2 (medical_id = 10, patient_id = 10, doctor_id = 10) -> PENDING
        Prescription rx5 = Prescription.builder()
                .prescriptionCode("RX202609050001")
                .medicalId(10L)
                .patientId(10L)
                .doctorId(10L)
                .notes("Hạn chế tinh bột, tái khám xét nghiệm lại đường huyết.")
                .status(PrescriptionStatus.PENDING)
                .isDeleted(false)
                .items(new ArrayList<>())
                .build();
        rx5.addItem(PrescriptionItem.builder()
                .medicine(medGlucophage)
                .quantity(60)
                .dosage("1 viên/lần, ngày 2 lần")
                .instruction("Uống trong hoặc ngay sau bữa ăn")
                .unitPrice(medGlucophage.getUnitPrice())
                .totalPrice(medGlucophage.getUnitPrice().multiply(BigDecimal.valueOf(60)))
                .build());
        prescriptions.add(rx5);

        // Đơn 6: Dị ứng thức ăn cấp tính (medical_id = 11, patient_id = 11, doctor_id = 11) -> DISPENSED
        Prescription rx6 = Prescription.builder()
                .prescriptionCode("RX202609060001")
                .medicalId(11L)
                .patientId(11L)
                .doctorId(11L)
                .dispensedAt(LocalDateTime.of(2026, 9, 6, 12, 0, 0))
                .notes("Đã cấp phát thuốc ngoại trú sau khi theo dõi ổn định.")
                .status(PrescriptionStatus.DISPENSED)
                .isDeleted(false)
                .items(new ArrayList<>())
                .build();
        rx6.addItem(PrescriptionItem.builder()
                .medicine(medTelfast)
                .quantity(10)
                .dosage("1 viên/ngày")
                .instruction("Uống vào buổi sáng sau ăn")
                .unitPrice(medTelfast.getUnitPrice())
                .totalPrice(medTelfast.getUnitPrice().multiply(BigDecimal.valueOf(10)))
                .build());
        prescriptions.add(rx6);

        // Đơn 7: Đau thắt ngực ổn định (medical_id = 14, patient_id = 14, doctor_id = 15) -> PENDING
        Prescription rx7 = Prescription.builder()
                .prescriptionCode("RX202609070001")
                .medicalId(14L)
                .patientId(14L)
                .doctorId(15L)
                .notes("Đơn thuốc dự phòng biến cố tim mạch.")
                .status(PrescriptionStatus.PENDING)
                .isDeleted(false)
                .items(new ArrayList<>())
                .build();
        rx7.addItem(PrescriptionItem.builder()
                .medicine(medAspirin)
                .quantity(30)
                .dosage("1 viên/ngày")
                .instruction("Uống sau ăn trưa")
                .unitPrice(medAspirin.getUnitPrice())
                .totalPrice(medAspirin.getUnitPrice().multiply(BigDecimal.valueOf(30)))
                .build());
        rx7.addItem(PrescriptionItem.builder()
                .medicine(medLipitor)
                .quantity(30)
                .dosage("1 viên/ngày")
                .instruction("Uống buổi tối trước khi đi ngủ")
                .unitPrice(medLipitor.getUnitPrice())
                .totalPrice(medLipitor.getUnitPrice().multiply(BigDecimal.valueOf(30)))
                .build());
        prescriptions.add(rx7);

        // Đơn 8: Đơn bị hủy (CANCELLED)
        Prescription rx8 = Prescription.builder()
                .prescriptionCode("RX202608280001")
                .medicalId(901L)
                .patientId(1L)
                .doctorId(1L)
                .cancelledReason("Bác sĩ đổi sang phác đồ điều trị khác")
                .status(PrescriptionStatus.CANCELLED)
                .isDeleted(false)
                .items(new ArrayList<>())
                .build();
        rx8.addItem(PrescriptionItem.builder()
                .medicine(medZinnat)
                .quantity(10)
                .dosage("1 viên/lần, ngày 2 lần")
                .instruction("Uống sau ăn")
                .unitPrice(medZinnat.getUnitPrice())
                .totalPrice(medZinnat.getUnitPrice().multiply(BigDecimal.valueOf(10)))
                .build());
        prescriptions.add(rx8);

        // Đơn 9: Đơn bị xóa mềm (isDeleted = true)
        Prescription rx9 = Prescription.builder()
                .prescriptionCode("RX202608250001")
                .medicalId(902L)
                .patientId(2L)
                .doctorId(2L)
                .notes("Kê nhầm mã bệnh án, xóa mềm lưu trữ")
                .status(PrescriptionStatus.CANCELLED)
                .isDeleted(true)
                .items(new ArrayList<>())
                .build();
        rx9.addItem(PrescriptionItem.builder()
                .medicine(medPanadol)
                .quantity(10)
                .dosage("1 viên khi đau")
                .instruction("Uống khi đau")
                .unitPrice(medPanadol.getUnitPrice())
                .totalPrice(medPanadol.getUnitPrice().multiply(BigDecimal.valueOf(10)))
                .build());
        prescriptions.add(rx9);

        // Tính toán lại tổng tiền cho các đơn thuốc trước khi lưu
        for (Prescription p : prescriptions) {
            p.recalculateTotalAmount();
        }

        prescriptionRepository.saveAll(prescriptions);
    }
}
