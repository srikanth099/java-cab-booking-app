package com.wipro.cabbooking.dto;

import com.wipro.cabbooking.entity.TripStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TripBookingDTO {
    private int tripId;
    private int customerId; // To link with Customer
    private int driverId; // To link with Driver
    private int cabId; // To link with Cab
    private String pickupLocation;
    private String dropoffLocation;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TripStatus status;
    private float distanceInKm; // Add this field
    private Float  bill; // Add this field
}
