package com.wipro.cabbooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cab")
@Getter
@Setter
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cab_id")
    private int cabId;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "per_km_rate")
    private float perKmRate;

    @OneToOne
    @JoinColumn(name = "driver_id", unique = true)
    private Driver driver;
}