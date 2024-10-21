package com.wipro.cabbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.wipro.cabbooking.entity")
public class CabBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CabBookingApplication.class, args);
    }
}