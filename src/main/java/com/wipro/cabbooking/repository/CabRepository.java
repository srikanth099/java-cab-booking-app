package com.wipro.cabbooking.repository;

import com.wipro.cabbooking.entity.Cab;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabRepository extends JpaRepository<Cab, Integer> {
    // Fetch all cabs by a specific car type
    List<Cab> findByCarType(String carType);
    
    // Fetch a cab by its associated driver ID
    Cab findByDriverDriverId(int driverId);
    
    // Fetch all cabs within a specific per km rate range
    List<Cab> findByPerKmRateBetween(float minRate, float maxRate);
}
