package com.wipro.cabbooking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDTO {
    private int driverId;
    private String username;
    private String password;
    private String address;
    private String mobileNumber;
    private String email;
    private String licenseNo;
    private float rating;
    private CabDTO cab;
}
