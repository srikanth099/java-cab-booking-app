package com.wipro.cabbooking.repository;

import com.wipro.cabbooking.entity.TripBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripBookingRepository extends JpaRepository<TripBooking, Integer> {

    // Method to find trip bookings by customer using customerId
    @Query("SELECT tb FROM TripBooking tb WHERE tb.customer.customerId = :customerId")
    List<TripBooking> findByCustomerId(int customerId);

    // Corrected method to find trip bookings by driver using driverId
    @Query("SELECT tb FROM TripBooking tb WHERE tb.driver.driverId = :driverId")
    List<TripBooking> findByDriverId(int driverId);
}
