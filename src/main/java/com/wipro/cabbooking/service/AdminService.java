package com.wipro.cabbooking.service;

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

import java.util.List;

public interface AdminService {

    // Admin CRUD operations
    AdminDTO saveAdmin(AdminDTO adminDTO);

    AdminDTO updateAdmin(AdminDTO adminDTO) throws AdminException;

    void deleteAdmin(int adminId) throws AdminException;

    AdminDTO getAdminById(int adminId) throws AdminException;

    List<AdminDTO> getAllAdmins();

    // Customer management
    CustomerDTO addCustomer(CustomerDTO customerDTO) throws CustomerException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerException;

    void deleteCustomer(int customerId) throws CustomerException;

    CustomerDTO getCustomerById(int customerId) throws CustomerException;

    List<CustomerDTO> getAllCustomers();

    List<CustomerDTO> searchCustomersByUsername(String username);

    // Driver management
    DriverDTO addDriver(DriverDTO driverDTO) throws DriverException;

    DriverDTO updateDriver(DriverDTO driverDTO) throws DriverException;

    void deleteDriver(int driverId) throws DriverException;

    DriverDTO getDriverById(int driverId) throws DriverException;

    List<DriverDTO> getAllDrivers();

    List<DriverDTO> viewBestDrivers();

    // Cab management
    CabDTO addCab(CabDTO cabDTO) throws CabException;

    CabDTO updateCab(CabDTO cabDTO) throws CabException;

    void deleteCab(int cabId) throws CabException;

    CabDTO getCabById(int cabId) throws CabException;

    List<CabDTO> getAllCabs();

    // Trip Booking management
    TripBookingDTO bookCab(int customerId, TripBookingDTO tripBookingDTO) throws TripBookingException;

    void cancelTripBooking(int tripId) throws TripBookingException;

    TripBookingDTO getTripBookingById(int tripId) throws TripBookingException;

    List<TripBookingDTO> getAllTripBookings();

    List<TripBookingDTO> viewBookingHistory(int customerId) throws TripBookingException;

    List<TripBookingDTO> viewDriverBookingHistory(int driverId) throws TripBookingException;

    // Admin-specific functionalities
    void cancelAnyBooking(int tripId) throws TripBookingException;

    List<TripBookingDTO> viewAllBookings() throws TripBookingException;
}
