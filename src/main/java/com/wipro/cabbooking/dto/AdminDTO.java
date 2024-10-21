package com.wipro.cabbooking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDTO {
    private int adminId;
    private String username;
    private String password;
    private String address;
    private String mobileNumber;
    private String email;
}
