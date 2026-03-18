package com.example.vehicalrentalserviceplatform;

import com.example.vehicalrentalserviceplatform.controller.BookingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VehicalRentalServicePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicalRentalServicePlatformApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<BookingController> customerServletBean() {
        return new ServletRegistrationBean<>(new BookingController(),"/createBooking");
    }

}
