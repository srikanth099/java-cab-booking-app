package com.wipro.cabbooking.servicetests;

import com.wipro.cabbooking.entity.TripBooking;
import com.wipro.cabbooking.entity.Customer;
import com.wipro.cabbooking.entity.Driver;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.entity.Cab;
import com.wipro.cabbooking.entity.TripStatus;
import com.wipro.cabbooking.repository.TripBookingRepository;
import com.wipro.cabbooking.repository.CustomerRepository;
import com.wipro.cabbooking.repository.DriverRepository;
import com.wipro.cabbooking.repository.CabRepository;
import com.wipro.cabbooking.serviceimpl.TripBookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TripBookingServiceTest {

    @Mock
    private TripBookingRepository tripBookingRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private CabRepository cabRepository;

    @InjectMocks
    private TripBookingServiceImpl tripBookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTripBooking() {
        TripBookingDTO tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setPickupLocation("Pickup Location");
        tripBookingDTO.setDropoffLocation("Dropoff Location");
        tripBookingDTO.setStartDate(LocalDateTime.now());
        tripBookingDTO.setEndDate(LocalDateTime.now().plusHours(1));
        tripBookingDTO.setDistanceInKm(10f); // Use Float
        tripBookingDTO.setCabId(1);
        tripBookingDTO.setDriverId(1);
        tripBookingDTO.setCustomerId(1);
        tripBookingDTO.setStatus(TripStatus.PENDING);

        Cab cab = new Cab();
        cab.setCabId(1);
        cab.setPerKmRate(10f); // Use Float

        TripBooking tripBooking = new TripBooking();
        tripBooking.setPickupLocation("Pickup Location");
        tripBooking.setDropoffLocation("Dropoff Location");
        tripBooking.setStartDate(LocalDateTime.now());
        tripBooking.setEndDate(LocalDateTime.now().plusHours(1));
        tripBooking.setDistanceInKm(10f); // Use Float
        tripBooking.setBill(100f); // 10 * 10 rate
        tripBooking.setCab(cab);
        tripBooking.setDriver(new Driver());
        tripBooking.getDriver().setDriverId(1);
        tripBooking.setCustomer(new Customer());
        tripBooking.getCustomer().setCustomerId(1);
        tripBooking.setStatus(TripStatus.PENDING);

        when(cabRepository.findById(1)).thenReturn(Optional.of(cab));
        when(driverRepository.findById(1)).thenReturn(Optional.of(tripBooking.getDriver()));
        when(customerRepository.findById(1)).thenReturn(Optional.of(tripBooking.getCustomer()));
        when(tripBookingRepository.save(any(TripBooking.class))).thenReturn(tripBooking);

        TripBookingDTO savedTripBookingDTO = tripBookingService.saveTripBooking(tripBookingDTO);

        assertNotNull(savedTripBookingDTO);
        assertEquals(10f, savedTripBookingDTO.getDistanceInKm()); // Assert the distance
        assertEquals(100f, savedTripBookingDTO.getBill()); // Assert the bill
    }


    @Test
    void testBookCab() throws Exception {
        TripBookingDTO tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setPickupLocation("Pickup Location");
        tripBookingDTO.setDropoffLocation("Dropoff Location");
        tripBookingDTO.setStartDate(LocalDateTime.now());
        tripBookingDTO.setEndDate(LocalDateTime.now().plusHours(1));
        tripBookingDTO.setDistanceInKm(10f); // Use Float
        tripBookingDTO.setCabId(1);
        tripBookingDTO.setDriverId(1);
        tripBookingDTO.setCustomerId(1);

        Customer customer = new Customer();
        customer.setCustomerId(1);

        Driver driver = new Driver();
        driver.setDriverId(1);

        Cab cab = new Cab();
        cab.setCabId(1);
        cab.setPerKmRate(10f); // Use Float

        TripBooking tripBooking = new TripBooking();
        tripBooking.setPickupLocation("Pickup Location");
        tripBooking.setDropoffLocation("Dropoff Location");
        tripBooking.setStartDate(LocalDateTime.now());
        tripBooking.setEndDate(LocalDateTime.now().plusHours(1));
        tripBooking.setDistanceInKm(10f); // Use Float
        tripBooking.setBill(100f); // Use Float
        tripBooking.setCustomer(customer);
        tripBooking.setDriver(driver);
        tripBooking.setCab(cab);
        tripBooking.setStatus(TripStatus.PENDING);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
        when(cabRepository.findById(1)).thenReturn(Optional.of(cab));
        when(tripBookingRepository.save(any(TripBooking.class))).thenReturn(tripBooking);

        TripBookingDTO bookedTrip = tripBookingService.bookCab(1, tripBookingDTO);
        assertNotNull(bookedTrip);
        assertEquals("Pickup Location", bookedTrip.getPickupLocation());
    }
}
