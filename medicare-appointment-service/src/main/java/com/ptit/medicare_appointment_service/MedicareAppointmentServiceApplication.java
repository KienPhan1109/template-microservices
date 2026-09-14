package com.ptit.medicare_appointment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MedicareAppointmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicareAppointmentServiceApplication.class, args);
    }

}
