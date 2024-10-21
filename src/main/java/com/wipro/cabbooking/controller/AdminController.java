package com.wipro.cabbooking.controller;

import com.wipro.cabbooking.dto.AdminDTO;
import com.wipro.cabbooking.dto.CustomerDTO;
import com.wipro.cabbooking.dto.DriverDTO;
import com.wipro.cabbooking.dto.CabDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.exception.AdminException;
import com.wipro.cabbooking.exception.CustomerException;
import com.wipro.cabbooking.exception.DriverException;
import com.wipro.cabbooking.exception.CabException;
import com.wipro.cabbooking.exception.TripBookingException;
import com.wipro.cabbooking.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Admin endpoints

    @PostMapping("/admins")
    public ResponseEntity<AdminDTO> addAdmin(@RequestBody AdminDTO adminDTO) {
        return new ResponseEntity<>(adminService.saveAdmin(adminDTO), HttpStatus.CREATED);
    }

    @PutMapping("/admins")
    public ResponseEntity<AdminDTO> updateAdmin(@RequestBody AdminDTO adminDTO) {
        try {
            return new ResponseEntity<>(adminService.updateAdmin(adminDTO), HttpStatus.OK);
        } catch (AdminException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admins/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable int adminId) {
        try {
            adminService.deleteAdmin(adminId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (AdminException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admins/{adminId}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable int adminId) {
        try {
            return new ResponseEntity<>(adminService.getAdminById(adminId), HttpStatus.OK);
        } catch (AdminException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admins")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        return new ResponseEntity<>(adminService.getAllAdmins(), HttpStatus.OK);
    }

    // Customer endpoints

    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            return new ResponseEntity<>(adminService.addCustomer(customerDTO), HttpStatus.CREATED);
        } catch (CustomerException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/customers")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            return new ResponseEntity<>(adminService.updateCustomer(customerDTO), HttpStatus.OK);
        } catch (CustomerException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int customerId) {
        try {
            adminService.deleteCustomer(customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int customerId) {
        try {
            return new ResponseEntity<>(adminService.getCustomerById(customerId), HttpStatus.OK);
        } catch (CustomerException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(adminService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/customers/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomersByUsername(@RequestParam String username) {
        return new ResponseEntity<>(adminService.searchCustomersByUsername(username), HttpStatus.OK);
    }

    // Driver endpoints

    @PostMapping("/drivers")
    public ResponseEntity<DriverDTO> addDriver(@RequestBody DriverDTO driverDTO) {
        try {
            return new ResponseEntity<>(adminService.addDriver(driverDTO), HttpStatus.CREATED);
        } catch (DriverException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/drivers")
    public ResponseEntity<DriverDTO> updateDriver(@RequestBody DriverDTO driverDTO) {
        try {
            return new ResponseEntity<>(adminService.updateDriver(driverDTO), HttpStatus.OK);
        } catch (DriverException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/drivers/{driverId}")
    public ResponseEntity<Void> deleteDriver(@PathVariable int driverId) {
        try {
            adminService.deleteDriver(driverId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DriverException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/drivers/{driverId}")
    public ResponseEntity<DriverDTO> getDriverById(@PathVariable int driverId) {
        try {
            return new ResponseEntity<>(adminService.getDriverById(driverId), HttpStatus.OK);
        } catch (DriverException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/drivers")
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {
        return new ResponseEntity<>(adminService.getAllDrivers(), HttpStatus.OK);
    }

    @GetMapping("/drivers/best")
    public ResponseEntity<List<DriverDTO>> viewBestDrivers() {
        return new ResponseEntity<>(adminService.viewBestDrivers(), HttpStatus.OK);
    }

    // Cab endpoints

    @PostMapping("/cabs")
    public ResponseEntity<CabDTO> addCab(@RequestBody CabDTO cabDTO) {
        try {
            return new ResponseEntity<>(adminService.addCab(cabDTO), HttpStatus.CREATED);
        } catch (CabException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cabs")
    public ResponseEntity<CabDTO> updateCab(@RequestBody CabDTO cabDTO) {
        try {
            return new ResponseEntity<>(adminService.updateCab(cabDTO), HttpStatus.OK);
        } catch (CabException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cabs/{cabId}")
    public ResponseEntity<Void> deleteCab(@PathVariable int cabId) {
        try {
            adminService.deleteCab(cabId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CabException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cabs/{cabId}")
    public ResponseEntity<CabDTO> getCabById(@PathVariable int cabId) {
        try {
            return new ResponseEntity<>(adminService.getCabById(cabId), HttpStatus.OK);
        } catch (CabException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cabs")
    public ResponseEntity<List<CabDTO>> getAllCabs() {
        return new ResponseEntity<>(adminService.getAllCabs(), HttpStatus.OK);
    }

    // Trip Booking endpoints

    @PostMapping("/bookings/{customerId}")
    public ResponseEntity<TripBookingDTO> bookCab(@PathVariable int customerId, @RequestBody TripBookingDTO tripBookingDTO) {
        try {
            return new ResponseEntity<>(adminService.bookCab(customerId, tripBookingDTO), HttpStatus.CREATED);
        } catch (TripBookingException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/bookings/{tripId}/cancel")
    public ResponseEntity<Void> cancelTripBooking(@PathVariable int tripId) {
        try {
            adminService.cancelTripBooking(tripId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TripBookingException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bookings/{tripId}")
    public ResponseEntity<TripBookingDTO> getTripBookingById(@PathVariable int tripId) {
        try {
            return new ResponseEntity<>(adminService.getTripBookingById(tripId), HttpStatus.OK);
        } catch (TripBookingException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<TripBookingDTO>> getAllTripBookings() {
        return new ResponseEntity<>(adminService.getAllTripBookings(), HttpStatus.OK);
    }

    @GetMapping("/bookings/customer/{customerId}")
    public ResponseEntity<List<TripBookingDTO>> viewBookingHistory(@PathVariable int customerId) {
        try {
            return new ResponseEntity<>(adminService.viewBookingHistory(customerId), HttpStatus.OK);
        } catch (TripBookingException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
