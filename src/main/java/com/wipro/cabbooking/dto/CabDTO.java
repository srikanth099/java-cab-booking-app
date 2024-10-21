package com.wipro.cabbooking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CabDTO {
    private int cabId;
    private String carType;
    private float perKmRate;
    private DriverDTO driver; 
}
