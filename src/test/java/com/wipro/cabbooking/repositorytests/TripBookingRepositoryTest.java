package com.wipro.cabbooking.repositorytests;

import com.wipro.cabbooking.entity.*;
import com.wipro.cabbooking.repository.TripBookingRepository;
import com.wipro.cabbooking.repository.CustomerRepository;
import com.wipro.cabbooking.repository.DriverRepository;
import com.wipro.cabbooking.repository.CabRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class TripBookingRepositoryTest {

    @Autowired
    private TripBookingRepository tripBookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CabRepository cabRepository;

    private Customer customer;
    private Driver driver;
    private Cab cab;

    @BeforeEach
    void setUp() {
        // Clean the database before each test
        tripBookingRepository.deleteAll();
        customerRepository.deleteAll();
        driverRepository.deleteAll();
        cabRepository.deleteAll();

        // Create and save a Customer
        customer = new Customer();
        customer.setUsername("john_customer");
        customer.setPassword("password");
        customer.setAddress("789 Maple Street");
        customer.setEmail("customer@example.com");
        customer.setMobileNumber("555-7890");
        customer = customerRepository.save(customer);

        // Create and save a Cab
        cab = new Cab();
        cab.setCarType("Hatchback");
        cab = cabRepository.save(cab);

        // Create and save a Driver
        driver = new Driver();
        driver.setUsername("driver1");
        driver.setPassword("password");
        driver.setAddress("123 Pine Street");
        driver.setEmail("driver@example.com");
        driver.setMobileNumber("555-1111");
        driver.setLicenseNo("DRIVER123");
        driver.setRating(4.7f);
        driver.setCab(cab);
        cab.setDriver(driver); // Set the back-reference
        driver = driverRepository.save(driver);

        // Create and save a TripBooking
        TripBooking tripBooking = new TripBooking();
        tripBooking.setCustomer(customer);
        tripBooking.setDriver(driver);
        tripBooking.setCab(cab);
        tripBooking.setPickupLocation("Location A");
        tripBooking.setDropoffLocation("Location B");
        tripBooking.setStartDate(LocalDateTime.now().minusHours(1));
        tripBooking.setEndDate(LocalDateTime.now());
        tripBooking.setStatus(TripStatus.COMPLETED);
        tripBooking.setDistanceInKm(15.5f);
        tripBooking.setBill(200.0f);
        tripBookingRepository.save(tripBooking);
    }

    @Test
    void testFindByCustomerId() {
        List<TripBooking> tripBookings = tripBookingRepository.findByCustomerId(customer.getCustomerId());

        assertThat(tripBookings).hasSize(1);
        assertThat(tripBookings.get(0).getPickupLocation()).isEqualTo("Location A");
    }

    @Test
    void testFindByDriverId() {
        List<TripBooking> tripBookings = tripBookingRepository.findByDriverId(driver.getDriverId());

        assertThat(tripBookings).hasSize(1);
        assertThat(tripBookings.get(0).getDropoffLocation()).isEqualTo("Location B");
    }
}
