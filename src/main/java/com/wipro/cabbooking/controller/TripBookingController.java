package com.wipro.cabbooking.controller;

import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.exception.TripBookingException;
import com.wipro.cabbooking.service.TripBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip-bookings")
public class TripBookingController {

    @Autowired
    private TripBookingService tripBookingService;

    @PostMapping("/book/{customerId}")
    public ResponseEntity<TripBookingDTO> bookCab(@PathVariable int customerId, @RequestBody TripBookingDTO tripBookingDTO) {
        try {
            TripBookingDTO tripBooking = tripBookingService.bookCab(customerId, tripBookingDTO);
            return ResponseEntity.ok(tripBooking);
        } catch (TripBookingException e) {
            return ResponseEntity.status(404).body(null); // Not Found or appropriate error response
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null); // Bad Request or appropriate error response
        }
    }

    @PostMapping("/save")
    public ResponseEntity<TripBookingDTO> saveTripBooking(@RequestBody TripBookingDTO tripBookingDTO) {
        try {
            TripBookingDTO savedTripBooking = tripBookingService.saveTripBooking(tripBookingDTO);
            return ResponseEntity.ok(savedTripBooking);
        } catch (TripBookingException e) {
            return ResponseEntity.status(404).body(null); // Not Found or appropriate error response
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null); // Bad Request or appropriate error response
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TripBookingDTO> updateTripBooking(@RequestBody TripBookingDTO tripBookingDTO) {
        try {
            TripBookingDTO updatedTripBooking = tripBookingService.updateTripBooking(tripBookingDTO);
            return ResponseEntity.ok(updatedTripBooking);
        } catch (TripBookingException e) {
            return ResponseEntity.status(404).body(null); // Not Found or appropriate error response
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null); // Bad Request or appropriate error response
        }
    }

    @DeleteMapping("/cancel/{tripId}")
    public ResponseEntity<Void> cancelTripBooking(@PathVariable int tripId) {
        try {
            tripBookingService.cancelTripBooking(tripId);
            return ResponseEntity.ok().build();
        } catch (TripBookingException e) {
            return ResponseEntity.status(404).build(); // Not Found or appropriate error response
        }
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripBookingDTO> getTripBookingById(@PathVariable int tripId) {
        try {
            TripBookingDTO tripBooking = tripBookingService.getTripBookingById(tripId);
            return ResponseEntity.ok(tripBooking);
        } catch (TripBookingException e) {
            return ResponseEntity.status(404).build(); // Not Found or appropriate error response
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TripBookingDTO>> getAllTripBookings() {
        List<TripBookingDTO> tripBookings = tripBookingService.getAllTripBookings();
        return ResponseEntity.ok(tripBookings);
    }

    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<TripBookingDTO>> viewBookingHistory(@PathVariable int customerId) {
        try {
            List<TripBookingDTO> bookingHistory = tripBookingService.viewBookingHistory(customerId);
            return ResponseEntity.ok(bookingHistory);
        } catch (TripBookingException e) {
            return ResponseEntity.status(404).build(); // Not Found or appropriate error response
        }
    }

    @GetMapping("/driver-history/{driverId}")
    public ResponseEntity<List<TripBookingDTO>> viewDriverBookingHistory(@PathVariable int driverId) {
        try {
            List<TripBookingDTO> driverBookingHistory = tripBookingService.viewDriverBookingHistory(driverId);
            return ResponseEntity.ok(driverBookingHistory);
        } catch (TripBookingException e) {
            return ResponseEntity.status(404).build(); // Not Found or appropriate error response
        }
    }
}
