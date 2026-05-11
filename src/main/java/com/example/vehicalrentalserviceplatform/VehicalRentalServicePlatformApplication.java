package com.example.vehicalrentalserviceplatform;

import com.example.vehicalrentalserviceplatform.controller.BookingController;
import com.example.vehicalrentalserviceplatform.controller.DeleteBookingController;
import com.example.vehicalrentalserviceplatform.controller.UpdateBookingController;
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

    @Bean
    public ServletRegistrationBean<DeleteBookingController> deleteServletBean() {
        return new ServletRegistrationBean<>(new DeleteBookingController(), "/deleteBooking");
    }

    @Bean
    public ServletRegistrationBean<UpdateBookingController> updateServletBean() {
        return new ServletRegistrationBean<>(new UpdateBookingController(),"/updateBooking");
    }
}
