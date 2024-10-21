package com.wipro.cabbooking.serviceimpl;

import com.wipro.cabbooking.dto.CabDTO;
import com.wipro.cabbooking.dto.DriverDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.entity.Cab;
import com.wipro.cabbooking.entity.Driver;
import com.wipro.cabbooking.entity.TripBooking;
import com.wipro.cabbooking.exception.DriverException;
import com.wipro.cabbooking.repository.CabRepository;
import com.wipro.cabbooking.repository.DriverRepository;
import com.wipro.cabbooking.repository.TripBookingRepository;
import com.wipro.cabbooking.service.DriverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private TripBookingRepository tripBookingRepository;

    @Override
    public DriverDTO saveDriver(DriverDTO driverDTO) {
        Driver driver = new Driver();
        driver.setUsername(driverDTO.getUsername());
        driver.setPassword(driverDTO.getPassword());
        driver.setAddress(driverDTO.getAddress());
        driver.setMobileNumber(driverDTO.getMobileNumber());
        driver.setEmail(driverDTO.getEmail());
        driver.setLicenseNo(driverDTO.getLicenseNo());
        driver.setRating(driverDTO.getRating());

        if (driverDTO.getCab() != null) {
            CabDTO cabDTO = driverDTO.getCab();
            Cab cab = cabRepository.findById(cabDTO.getCabId())
                    .orElseThrow(() -> new DriverException("Cab not found with ID: " + cabDTO.getCabId()));

            cab.setCarType(cabDTO.getCarType()); // Set carType from CabDTO
            cab.setPerKmRate(cabDTO.getPerKmRate()); // Set perKmRate from CabDTO
            cab.setDriver(driver);
            driver.setCab(cab);

            cabRepository.save(cab);  // Save the updated cab
        } else {
            Cab cab = new Cab();
            cab.setCarType(driverDTO.getCab().getCarType()); // Handle CabDTO properties
            cab.setPerKmRate(driverDTO.getCab().getPerKmRate()); // Handle CabDTO properties
            cab.setDriver(driver);
            driver.setCab(cab);
            cabRepository.save(cab);
        }

        Driver savedDriver = driverRepository.save(driver);
        return convertToDTO(savedDriver);
    }
    @Override
    public DriverDTO updateDriver(DriverDTO driverDTO) {
        Driver driver = driverRepository.findById(driverDTO.getDriverId())
                .orElseThrow(() -> new DriverException("Driver not found"));

        driver.setUsername(driverDTO.getUsername());
        driver.setPassword(driverDTO.getPassword());
        driver.setAddress(driverDTO.getAddress());
        driver.setMobileNumber(driverDTO.getMobileNumber());
        driver.setEmail(driverDTO.getEmail());
        driver.setLicenseNo(driverDTO.getLicenseNo());
        driver.setRating(driverDTO.getRating());

        if (driverDTO.getCab() != null) {
            CabDTO cabDTO = driverDTO.getCab();
            Cab cab = cabRepository.findById(cabDTO.getCabId())
                    .orElseThrow(() -> new DriverException("Cab not found with ID: " + cabDTO.getCabId()));

            // Update cab properties from CabDTO
            cab.setCarType(cabDTO.getCarType());
            cab.setPerKmRate(cabDTO.getPerKmRate());

            // Set cab to driver and vice versa
            cab.setDriver(driver);
            driver.setCab(cab);

            cabRepository.save(cab); // Save updated cab
        } else if (driver.getCab() != null) {
            // Remove cab association if not provided
            Cab cab = driver.getCab();
            cab.setDriver(null);
            driver.setCab(null);
            cabRepository.save(cab);
        }

        Driver updatedDriver = driverRepository.save(driver);
        return convertToDTO(updatedDriver);
    }

    @Override
    public void deleteDriver(int driverId) {
        if (!driverRepository.existsById(driverId)) {
            throw new DriverException("Driver not found");
        }

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverException("Driver not found"));

        if (driver.getCab() != null) {
            Cab cab = driver.getCab();
            cab.setDriver(null);
            driver.setCab(null);
            cabRepository.save(cab);
        }

        driverRepository.deleteById(driverId);
    }

    @Override
    public DriverDTO getDriverById(int driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverException("Driver not found"));
        return convertToDTO(driver);
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripBookingDTO> getBookingHistory(int driverId) {
        List<TripBooking> tripBookings = tripBookingRepository.findByDriverId(driverId);
        return tripBookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DriverDTO> viewBestDrivers() {
        // Retrieve top drivers by rating from the repository
        List<Driver> topDrivers = driverRepository.findTopDriversByRating();
        
        // Convert the list of Driver entities to DriverDTOs
        return topDrivers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DriverDTO convertToDTO(Driver driver) {
        DriverDTO dto = new DriverDTO();
        dto.setDriverId(driver.getDriverId());
        dto.setUsername(driver.getUsername());
        dto.setPassword(driver.getPassword());
        dto.setAddress(driver.getAddress());
        dto.setMobileNumber(driver.getMobileNumber());
        dto.setEmail(driver.getEmail());
        dto.setLicenseNo(driver.getLicenseNo());
        dto.setRating(driver.getRating());

        if (driver.getCab() != null) {
            CabDTO cabDTO = new CabDTO();
            cabDTO.setCabId(driver.getCab().getCabId());
            cabDTO.setCarType(driver.getCab().getCarType());
            cabDTO.setPerKmRate(driver.getCab().getPerKmRate());

            dto.setCab(cabDTO); // Set CabDTO instead of cabId
        }

        return dto;
    }
    private TripBookingDTO convertToDTO(TripBooking tripBooking) {
        TripBookingDTO dto = new TripBookingDTO();
        dto.setTripId(tripBooking.getTripId());
        dto.setCustomerId(tripBooking.getCustomer().getCustomerId());
        dto.setDriverId(tripBooking.getDriver().getDriverId());
        dto.setCabId(tripBooking.getCab().getCabId());
        dto.setPickupLocation(tripBooking.getPickupLocation());
        dto.setDropoffLocation(tripBooking.getDropoffLocation());
        dto.setStartDate(tripBooking.getStartDate());
        dto.setEndDate(tripBooking.getEndDate());
        dto.setStatus(tripBooking.getStatus());
        dto.setDistanceInKm(tripBooking.getDistanceInKm());
        dto.setBill(tripBooking.getBill());

        return dto;
    }
}
