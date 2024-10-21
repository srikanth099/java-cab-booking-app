package com.wipro.cabbooking.service;

import com.wipro.cabbooking.dto.CustomerDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.exception.CustomerException;
import com.wipro.cabbooking.dto.CabDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO registerCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomerProfile(CustomerDTO customerDTO);

    List<CabDTO> viewAvailableCabs();
    
    void deleteCustomer(int customerId) throws CustomerException;

    TripBookingDTO bookCab(int customerId, TripBookingDTO tripBookingDTO);

    void cancelBooking(int tripId);

    List<TripBookingDTO> viewBookingHistory(int customerId);
    
    CustomerDTO getCustomerById(int customerId) throws CustomerException;

    List<CustomerDTO> getAllCustomers();
    
    List<CustomerDTO> searchCustomersByUsername(String username) throws CustomerException;
}
