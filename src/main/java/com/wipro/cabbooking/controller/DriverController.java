package com.wipro.cabbooking.controller;

import com.wipro.cabbooking.dto.DriverDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.exception.DriverException;
import com.wipro.cabbooking.exception.TripBookingException;
import com.wipro.cabbooking.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/save")
    public ResponseEntity<DriverDTO> saveDriver(@RequestBody DriverDTO driverDTO) {
        try {
            DriverDTO savedDriver = driverService.saveDriver(driverDTO);
            return ResponseEntity.ok(savedDriver);
        } catch (DriverException e) {
            return ResponseEntity.status(400).body(null); // Bad Request or appropriate error response
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Internal Server Error for unexpected issues
        }
    }

    @PutMapping("/update")
    public ResponseEntity<DriverDTO> updateDriver(@RequestBody DriverDTO driverDTO) {
        try {
            DriverDTO updatedDriver = driverService.updateDriver(driverDTO);
            return ResponseEntity.ok(updatedDriver);
        } catch (DriverException e) {
            return ResponseEntity.status(404).body(null); // Not Found or appropriate error response
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Internal Server Error for unexpected issues
        }
    }

    @DeleteMapping("/delete/{driverId}")
    public ResponseEntity<Void> deleteDriver(@PathVariable int driverId) {
        try {
            driverService.deleteDriver(driverId);
            return ResponseEntity.ok().build();
        } catch (DriverException e) {
            return ResponseEntity.status(404).build(); // Not Found or appropriate error response
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Internal Server Error for unexpected issues
        }
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverDTO> getDriverById(@PathVariable int driverId) {
        try {
            DriverDTO driver = driverService.getDriverById(driverId);
            return ResponseEntity.ok(driver);
        } catch (DriverException e) {
            return ResponseEntity.status(404).build(); // Not Found or appropriate error response
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Internal Server Error for unexpected issues
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {
        try {
            List<DriverDTO> drivers = driverService.getAllDrivers();
            return ResponseEntity.ok(drivers);
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Internal Server Error for unexpected issues
        }
    }

    @GetMapping("/{driverId}/booking-history")
    public ResponseEntity<List<TripBookingDTO>> getBookingHistory(@PathVariable int driverId) {
        try {
            List<TripBookingDTO> bookingHistory = driverService.getBookingHistory(driverId);
            return ResponseEntity.ok(bookingHistory);
        } catch (DriverException e) {
            return ResponseEntity.status(404).build(); // Not Found or appropriate error response
        } catch (TripBookingException e) {
            return ResponseEntity.status(404).build(); // Not Found or appropriate error response
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Internal Server Error for unexpected issues
        }
    }

    @GetMapping("/best")
    public ResponseEntity<List<DriverDTO>> viewBestDrivers() {
        try {
            List<DriverDTO> bestDrivers = driverService.viewBestDrivers();
            return ResponseEntity.ok(bestDrivers);
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Internal Server Error for unexpected issues
        }
    }
}
