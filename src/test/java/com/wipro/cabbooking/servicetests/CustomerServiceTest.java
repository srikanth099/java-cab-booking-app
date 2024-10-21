package com.wipro.cabbooking.servicetests;

import com.wipro.cabbooking.dto.CustomerDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.dto.CabDTO;
import com.wipro.cabbooking.entity.Customer;
import com.wipro.cabbooking.entity.Driver;
import com.wipro.cabbooking.entity.TripBooking;
import com.wipro.cabbooking.entity.Cab;
import com.wipro.cabbooking.exception.CustomerException;
import com.wipro.cabbooking.repository.CustomerRepository;
import com.wipro.cabbooking.repository.DriverRepository;
import com.wipro.cabbooking.repository.TripBookingRepository;
import com.wipro.cabbooking.repository.CabRepository;
import com.wipro.cabbooking.serviceimpl.CustomerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TripBookingRepository tripBookingRepository;

    @Mock
    private CabRepository cabRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private Customer customer;
    private CustomerDTO customerDTO;
    private TripBooking tripBooking;
    private TripBookingDTO tripBookingDTO;
    private Cab cab;
    private CabDTO cabDTO;
    private Driver driver;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerId(1);
        customer.setUsername("JohnDoe");
        customer.setPassword("password");
        customer.setAddress("123 Street");
        customer.setMobileNumber("1234567890");
        customer.setEmail("john.doe@example.com");

        customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1);
        customerDTO.setUsername("JohnDoe");
        customerDTO.setPassword("password");
        customerDTO.setAddress("123 Street");
        customerDTO.setMobileNumber("1234567890");
        customerDTO.setEmail("john.doe@example.com");

        tripBooking = new TripBooking();
        tripBooking.setTripId(1);
        tripBooking.setCustomer(customer);
        tripBooking.setPickupLocation("Location A");
        tripBooking.setDropoffLocation("Location B");

        tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setTripId(1);
        tripBookingDTO.setCustomerId(1);
        tripBookingDTO.setPickupLocation("Location A");
        tripBookingDTO.setDropoffLocation("Location B");
        tripBookingDTO.setCabId(1);
        tripBookingDTO.setDriverId(1);

        cab = new Cab();
        cab.setCabId(1);
        cab.setCarType("Sedan");
        cab.setPerKmRate(10.0f);

        cabDTO = new CabDTO();
        cabDTO.setCabId(1);
        cabDTO.setCarType("Sedan");
        cabDTO.setPerKmRate(10.0f);

        driver = new Driver();
        driver.setDriverId(1);
        driver.setUsername("JaneDoe");
        driver.setLicenseNo("LIC123");
    }

    @Test
    void registerCustomer_shouldReturnCustomerDTO() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerServiceImpl.registerCustomer(customerDTO);

        assertNotNull(result);
        assertEquals(customerDTO.getCustomerId(), result.getCustomerId());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomerProfile_shouldReturnUpdatedCustomerDTO() {
        when(customerRepository.findById(customerDTO.getCustomerId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerServiceImpl.updateCustomerProfile(customerDTO);

        assertNotNull(result);
        assertEquals(customerDTO.getUsername(), result.getUsername());
        verify(customerRepository, times(1)).findById(customerDTO.getCustomerId());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void viewAvailableCabs_shouldReturnListOfCabDTOs() {
        when(cabRepository.findAll()).thenReturn(Arrays.asList(cab));

        List<CabDTO> result = customerServiceImpl.viewAvailableCabs();

        assertNotNull(result);
        assertEquals(1, result.size());
        CabDTO cabDTO = result.get(0);
        assertEquals(cab.getCabId(), cabDTO.getCabId());
        assertEquals(cab.getCarType(), cabDTO.getCarType());
        assertEquals(cab.getPerKmRate(), cabDTO.getPerKmRate());
        verify(cabRepository, times(1)).findAll();
    }

    @Test
    void deleteCustomer_shouldThrowExceptionIfCustomerNotFound() {
        when(customerRepository.existsById(customer.getCustomerId())).thenReturn(false);

        assertThrows(CustomerException.class, () -> customerServiceImpl.deleteCustomer(customer.getCustomerId()));

        verify(customerRepository, times(1)).existsById(customer.getCustomerId());
        verify(customerRepository, never()).deleteById(customer.getCustomerId());
    }

    @Test
    void deleteCustomer_shouldDeleteCustomerIfExists() {
        when(customerRepository.existsById(customer.getCustomerId())).thenReturn(true);

        customerServiceImpl.deleteCustomer(customer.getCustomerId());

        verify(customerRepository, times(1)).deleteById(customer.getCustomerId());
    }

//    @Test
//    void bookCab_shouldReturnTripBookingDTO() {
//        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
//        when(cabRepository.findById(tripBookingDTO.getCabId())).thenReturn(Optional.of(cab));
//        when(driverRepository.findById(tripBookingDTO.getDriverId())).thenReturn(Optional.of(driver));
//        when(tripBookingRepository.save(any(TripBooking.class))).thenReturn(tripBooking);
//
//        TripBookingDTO result = customerServiceImpl.bookCab(customer.getCustomerId(), tripBookingDTO);
//
//        assertNotNull(result);
//        assertEquals(tripBookingDTO.getTripId(), result.getTripId());
//        verify(customerRepository, times(1)).findById(customer.getCustomerId());
//        verify(cabRepository, times(1)).findById(tripBookingDTO.getCabId());
//        verify(driverRepository, times(1)).findById(tripBookingDTO.getDriverId());
//        verify(tripBookingRepository, times(1)).save(any(TripBooking.class));
//    }

    @Test
    void cancelBooking_shouldThrowExceptionIfTripNotFound() {
        when(tripBookingRepository.findById(tripBooking.getTripId())).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> customerServiceImpl.cancelBooking(tripBooking.getTripId()));

        verify(tripBookingRepository, times(1)).findById(tripBooking.getTripId());
        verify(tripBookingRepository, never()).delete(any(TripBooking.class));
    }

//    @Test
//    void viewBookingHistory_shouldReturnListOfTripBookingDTOs() {
//        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
//        when(tripBookingRepository.findByCustomerId(customer.getCustomerId())).thenReturn(Arrays.asList(tripBooking));
//
//        List<TripBookingDTO> result = customerServiceImpl.viewBookingHistory(customer.getCustomerId());
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(customerRepository, times(1)).findById(customer.getCustomerId());
//        verify(tripBookingRepository, times(1)).findByCustomerId(customer.getCustomerId());
//    }

    @Test
    void getCustomerById_shouldReturnCustomerDTO() {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        CustomerDTO result = customerServiceImpl.getCustomerById(customer.getCustomerId());

        assertNotNull(result);
        assertEquals(customerDTO.getCustomerId(), result.getCustomerId());
        verify(customerRepository, times(1)).findById(customer.getCustomerId());
    }

    @Test
    void getAllCustomers_shouldReturnListOfCustomerDTOs() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));

        List<CustomerDTO> result = customerServiceImpl.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void searchCustomersByUsername_shouldReturnListOfCustomerDTOs() {
        when(customerRepository.findByUsernameContaining(customer.getUsername()))
                .thenReturn(Arrays.asList(customer));

        List<CustomerDTO> result = customerServiceImpl.searchCustomersByUsername(customer.getUsername());

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(customerRepository, times(1)).findByUsernameContaining(customer.getUsername());
    }

    @Test
    void searchCustomersByUsername_shouldThrowExceptionIfNoCustomersFound() {
        when(customerRepository.findByUsernameContaining(customer.getUsername())).thenReturn(Arrays.asList());

        assertThrows(CustomerException.class, () -> customerServiceImpl.searchCustomersByUsername(customer.getUsername()));

        verify(customerRepository, times(1)).findByUsernameContaining(customer.getUsername());
    }
}
