package com.wipro.cabbooking.repository;

import com.wipro.cabbooking.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    List<Driver> findByRatingGreaterThanEqual(float rating);
    List<Driver> findByUsername(String username);
    List<Driver> findTop5ByOrderByRatingDesc();
    
    
    
    @Query("SELECT d FROM Driver d ORDER BY d.rating DESC")
    List<Driver> findTopDriversByRating();

    // Fetch a driver by their cab ID
    Driver findByCabCabId(int cabId);
    
    // Fetch drivers with a specific car type
    List<Driver> findByCabCarType(String carType);
    @Query("SELECT d FROM Driver d WHERE d.cab.cabId = :cabId")
    Driver findDriverByCabId(int cabId);
	List<Driver> findTop10ByOrderByRatingDesc();
}
