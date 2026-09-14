package com.ptit.medicarediscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MedicareDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicareDiscoveryServerApplication.class, args);
    }

}
