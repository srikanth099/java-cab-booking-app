package com.wipro.cabbooking.controller;

import com.wipro.cabbooking.dto.CustomerDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.dto.CabDTO;
import com.wipro.cabbooking.exception.CustomerException;
import com.wipro.cabbooking.exception.TripBookingException;
import com.wipro.cabbooking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO registeredCustomer = customerService.registerCustomer(customerDTO);
            return ResponseEntity.ok(registeredCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Bad Request or appropriate error response
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDTO> updateCustomerProfile(@RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO updatedCustomer = customerService.updateCustomerProfile(customerDTO);
            return ResponseEntity.ok(updatedCustomer);
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Not Found or appropriate error response
        }
    }

    @GetMapping("/available-cabs")
    public ResponseEntity<List<CabDTO>> viewAvailableCabs() {
        List<CabDTO> availableCabs = customerService.viewAvailableCabs();
        return ResponseEntity.ok(availableCabs);
    }

    @PostMapping("/{customerId}/book")
    public ResponseEntity<TripBookingDTO> bookCab(@PathVariable int customerId, @RequestBody TripBookingDTO tripBookingDTO) {
        try {
            TripBookingDTO bookedTrip = customerService.bookCab(customerId, tripBookingDTO);
            return ResponseEntity.ok(bookedTrip);
        } catch (CustomerException | TripBookingException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Not Found or appropriate error response
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Internal Server Error for unexpected issues
        }
    }

    @DeleteMapping("/cancel/{tripId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable int tripId) {
        try {
            customerService.cancelBooking(tripId);
            return ResponseEntity.ok().build();
        } catch (TripBookingException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found or appropriate error response
        }
    }

    @GetMapping("/{customerId}/booking-history")
    public ResponseEntity<List<TripBookingDTO>> viewBookingHistory(@PathVariable int customerId) {
        try {
            List<TripBookingDTO> bookingHistory = customerService.viewBookingHistory(customerId);
            return ResponseEntity.ok(bookingHistory);
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int customerId) {
        try {
            CustomerDTO customer = customerService.getCustomerById(customerId);
            return ResponseEntity.ok(customer);
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found or appropriate error response
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomersByUsername(@RequestParam String username) {
        try {
            List<CustomerDTO> customers = customerService.searchCustomersByUsername(username);
            return ResponseEntity.ok(customers);
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found or appropriate error response
        }
    }
}
