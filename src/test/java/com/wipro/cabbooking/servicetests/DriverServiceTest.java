package com.wipro.cabbooking.servicetests;

import com.wipro.cabbooking.dto.CabDTO;
import com.wipro.cabbooking.dto.DriverDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.entity.Cab;
import com.wipro.cabbooking.entity.Driver;
import com.wipro.cabbooking.entity.TripBooking;
import com.wipro.cabbooking.entity.TripStatus;
import com.wipro.cabbooking.exception.DriverException;
import com.wipro.cabbooking.repository.CabRepository;
import com.wipro.cabbooking.repository.DriverRepository;
import com.wipro.cabbooking.repository.TripBookingRepository;
import com.wipro.cabbooking.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class DriverServiceTest {

    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private TripBookingRepository tripBookingRepository;

    @MockBean
    private CabRepository cabRepository;

    @Autowired
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        // Setup code, if needed
    }

    @Test
    void testSaveDriver() {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setUsername("driveruser");
        driverDTO.setPassword("driverpass");
        driverDTO.setAddress("Driver Address");
        driverDTO.setMobileNumber("0987654321");
        driverDTO.setEmail("driver@example.com");
        driverDTO.setLicenseNo("ABC123");
        driverDTO.setRating(4.8f);

        CabDTO cabDTO = new CabDTO();
        cabDTO.setCabId(1);
        cabDTO.setCarType("SUV");
        cabDTO.setPerKmRate(20.0f);
        driverDTO.setCab(cabDTO); // Set CabDTO

        Cab cab = new Cab();
        cab.setCabId(1);
        cab.setCarType("SUV");
        cab.setPerKmRate(20.0f);

        Driver driver = new Driver();
        driver.setDriverId(1);
        driver.setUsername("driveruser");
        driver.setPassword("driverpass");
        driver.setAddress("Driver Address");
        driver.setMobileNumber("0987654321");
        driver.setEmail("driver@example.com");
        driver.setLicenseNo("ABC123");
        driver.setRating(4.8f);
        driver.setCab(cab);

        when(cabRepository.findById(1)).thenReturn(Optional.of(cab));
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        DriverDTO savedDriver = driverService.saveDriver(driverDTO);
        assertNotNull(savedDriver);
        assertEquals("driveruser", savedDriver.getUsername());
        assertEquals(1, savedDriver.getCab().getCabId()); // Use CabDTO
    }

    @Test
    void testUpdateDriver() throws DriverException {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setDriverId(1);
        driverDTO.setUsername("updateddriver");
        driverDTO.setPassword("updatedpass");
        driverDTO.setAddress("Updated Address");
        driverDTO.setMobileNumber("1234567890");
        driverDTO.setEmail("updated@example.com");
        driverDTO.setLicenseNo("XYZ456");
        driverDTO.setRating(4.9f);

        CabDTO cabDTO = new CabDTO();
        cabDTO.setCabId(2);
        cabDTO.setCarType("Sedan");
        cabDTO.setPerKmRate(25.0f);
        driverDTO.setCab(cabDTO); // Set CabDTO

        Cab cab = new Cab();
        cab.setCabId(2);
        cab.setCarType("Sedan");
        cab.setPerKmRate(25.0f);

        Driver driver = new Driver();
        driver.setDriverId(1);
        driver.setUsername("updateddriver");
        driver.setPassword("updatedpass");
        driver.setAddress("Updated Address");
        driver.setMobileNumber("1234567890");
        driver.setEmail("updated@example.com");
        driver.setLicenseNo("XYZ456");
        driver.setRating(4.9f);
        driver.setCab(cab);

        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);
        when(cabRepository.findById(2)).thenReturn(Optional.of(cab));

        DriverDTO updatedDriver = driverService.updateDriver(driverDTO);
        assertNotNull(updatedDriver);
        assertEquals("updateddriver", updatedDriver.getUsername());
        assertEquals(2, updatedDriver.getCab().getCabId()); // Use CabDTO
    }

//    @Test
//    void testDeleteDriver() throws DriverException {
//        // Arrange
//        when(driverRepository.existsById(1)).thenReturn(true);
//        doNothing().when(driverRepository).deleteById(1);
//
//        // Act
//        driverService.deleteDriver(1);
//
//        // Assert
//        verify(driverRepository, times(1)).deleteById(1);
//    }

    @Test
    void testGetDriverById() throws DriverException {
        Driver driver = new Driver();
        driver.setDriverId(1);
        driver.setUsername("driveruser");

        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));

        DriverDTO foundDriver = driverService.getDriverById(1);
        assertNotNull(foundDriver);
        assertEquals("driveruser", foundDriver.getUsername());
    }

    @Test
    void testGetAllDrivers() {
        Driver driver = new Driver();
        driver.setDriverId(1);
        driver.setUsername("driveruser");
        List<Driver> drivers = new ArrayList<>();
        drivers.add(driver);

        when(driverRepository.findAll()).thenReturn(drivers);

        List<DriverDTO> allDrivers = driverService.getAllDrivers();
        assertNotNull(allDrivers);
        assertFalse(allDrivers.isEmpty());
        assertEquals(1, allDrivers.size());
        assertEquals("driveruser", allDrivers.get(0).getUsername());
    }

//    @Test
//    void testGetBookingHistory() {
//        TripBooking tripBooking = new TripBooking();
//        tripBooking.setTripId(1);
//        tripBooking.setPickupLocation("Pickup Location");
//        tripBooking.setDropoffLocation("Dropoff Location");
//        tripBooking.setStartDate(LocalDateTime.now().minusDays(1));
//        tripBooking.setEndDate(LocalDateTime.now());
//        tripBooking.setStatus(TripStatus.COMPLETED);
//        tripBooking.setDistanceInKm(10.0f);
//        tripBooking.setBill(100.0f);
//
//        Driver driver = new Driver();
//        driver.setDriverId(1);
//        tripBooking.setDriver(driver);
//
//        Cab cab = new Cab();
//        cab.setCabId(1);
//        tripBooking.setCab(cab);
//
//        List<TripBooking> bookings = new ArrayList<>();
//        bookings.add(tripBooking);
//
//        when(tripBookingRepository.findByDriverId(1)).thenReturn(bookings);
//
//        List<TripBookingDTO> history = driverService.getBookingHistory(1);
//
//        assertNotNull(history);
//        assertFalse(history.isEmpty());
//        assertEquals(1, history.size());
//
//        TripBookingDTO dto = history.get(0);
//        assertEquals(1, dto.getTripId());
//        assertEquals(1, dto.getDriverId());
//        assertEquals(1, dto.getCabId());
//        assertEquals("Pickup Location", dto.getPickupLocation());
//        assertEquals("Dropoff Location", dto.getDropoffLocation());
//        assertNotNull(dto.getStartDate());
//        assertNotNull(dto.getEndDate());
//        assertEquals(TripStatus.COMPLETED, dto.getStatus());
//        assertEquals(10.0f, dto.getDistanceInKm());
//        assertEquals(100.0f, dto.getBill());
//    }

//    @Test
//    void testViewBestDrivers() {
//        Driver driver = new Driver();
//        driver.setDriverId(1);
//        driver.setRating(4.6f);
//        List<Driver> bestDrivers = new ArrayList<>();
//        bestDrivers.add(driver);
//
//        when(driverRepository.findByRatingGreaterThanEqual(4.5f)).thenReturn(bestDrivers);
//
//        List<DriverDTO> bestDriversList = driverService.viewBestDrivers();
//        assertNotNull(bestDriversList);
//        assertFalse(bestDriversList.isEmpty());
//        assertEquals(1, bestDriversList.size());
//        assertEquals(4.6f, bestDriversList.get(0).getRating());
//    }
}
