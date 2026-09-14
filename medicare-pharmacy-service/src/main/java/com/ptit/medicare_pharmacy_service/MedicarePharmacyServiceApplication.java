package com.ptit.medicare_pharmacy_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MedicarePharmacyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicarePharmacyServiceApplication.class, args);
    }

}
