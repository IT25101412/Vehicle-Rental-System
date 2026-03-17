package com.example.vehicalrentalserviceplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class VehicalRentalServicePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicalRentalServicePlatformApplication.class, args);
    }

}
