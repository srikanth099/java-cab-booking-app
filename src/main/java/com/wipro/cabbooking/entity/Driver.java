package com.wipro.cabbooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver")
@Getter
@Setter
public class Driver extends AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private int driverId;

    @Column(name = "license_no")
    private String licenseNo;

    @Column(name = "rating")
    private float rating;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    @JoinColumn(name = "cab_id")
    private Cab cab;
}