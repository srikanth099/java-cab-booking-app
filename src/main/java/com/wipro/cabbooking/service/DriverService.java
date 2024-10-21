package com.wipro.cabbooking.service;

import com.wipro.cabbooking.dto.DriverDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.exception.DriverException;
import com.wipro.cabbooking.exception.TripBookingException;

import java.util.List;

public interface DriverService {

    DriverDTO saveDriver(DriverDTO driverDTO) throws DriverException;

    DriverDTO updateDriver(DriverDTO driverDTO) throws DriverException;

    void deleteDriver(int driverId) throws DriverException;

    DriverDTO getDriverById(int driverId) throws DriverException;

    List<DriverDTO> getAllDrivers();

    List<TripBookingDTO> getBookingHistory(int driverId) throws TripBookingException;

    List<DriverDTO> viewBestDrivers();
}
