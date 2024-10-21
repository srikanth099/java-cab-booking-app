package com.wipro.cabbooking.repositorytests;

import com.wipro.cabbooking.entity.Driver;
import com.wipro.cabbooking.entity.Cab;
import com.wipro.cabbooking.repository.DriverRepository;
import com.wipro.cabbooking.repository.CabRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CabRepository cabRepository;

    @BeforeEach
    void setUp() {
        // Clean the database before each test
        driverRepository.deleteAll();
        cabRepository.deleteAll();

        // Insert test data
        Cab cab1 = new Cab();
        cab1.setCarType("Sedan");
        cab1 = cabRepository.save(cab1); // Save Cab entity to ensure it's managed

        Cab cab2 = new Cab();
        cab2.setCarType("SUV");
        cab2 = cabRepository.save(cab2); // Save Cab entity to ensure it's managed

        // Make sure to set the back-reference in the Driver entity
        Driver driver1 = new Driver();
        driver1.setUsername("john_doe");
        driver1.setPassword("password1");
        driver1.setAddress("123 Elm Street");
        driver1.setEmail("john@example.com");
        driver1.setMobileNumber("555-1234");
        driver1.setLicenseNo("ABC123");
        driver1.setRating(4.5f);
        driver1.setCab(cab1); // Set the managed Cab
        cab1.setDriver(driver1); // Set the back-reference

        Driver driver2 = new Driver();
        driver2.setUsername("jane_doe");
        driver2.setPassword("password2");
        driver2.setAddress("456 Oak Avenue");
        driver2.setEmail("jane@example.com");
        driver2.setMobileNumber("555-5678");
        driver2.setLicenseNo("XYZ789");
        driver2.setRating(4.8f);
        driver2.setCab(cab2); // Set the managed Cab
        cab2.setDriver(driver2); // Set the back-reference

        driverRepository.save(driver1);
        driverRepository.save(driver2);
    }

    @Test
    void testFindByRatingGreaterThanEqual() {
        List<Driver> drivers = driverRepository.findByRatingGreaterThanEqual(4.6f);

        assertThat(drivers).hasSize(1);
        assertThat(drivers.get(0).getUsername()).isEqualTo("jane_doe");
    }

    @Test
    void testFindByUsername() {
        List<Driver> drivers = driverRepository.findByUsername("john_doe");

        assertThat(drivers).hasSize(1);
        assertThat(drivers.get(0).getUsername()).isEqualTo("john_doe");
    }

    @Test
    void testFindTop5ByOrderByRatingDesc() {
        List<Driver> drivers = driverRepository.findTop5ByOrderByRatingDesc();

        assertThat(drivers).hasSize(2);
        assertThat(drivers.get(0).getUsername()).isEqualTo("jane_doe");
        assertThat(drivers.get(1).getUsername()).isEqualTo("john_doe");
    }

    @Test
    void testFindTopDriversByRating() {
        List<Driver> drivers = driverRepository.findTopDriversByRating();

        assertThat(drivers).hasSize(2);
        assertThat(drivers.get(0).getUsername()).isEqualTo("jane_doe");
        assertThat(drivers.get(1).getUsername()).isEqualTo("john_doe");
    }

    @Test
    void testFindDriverByCabId() {
        // Retrieve the Cab after it's saved to get the actual cabId
        Cab cab = cabRepository.findAll().get(0); // Assuming cab1 is the first cab inserted
        int cabId = cab.getCabId();

        // Use the actual cabId in the query
        Driver driver = driverRepository.findDriverByCabId(cabId);

        assertThat(driver).isNotNull();
        if (driver != null) {
            assertThat(driver.getUsername()).isEqualTo("john_doe");
        }
    }


    @Test
    void testFindByCabCarType() {
        List<Driver> drivers = driverRepository.findByCabCarType("SUV");

        assertThat(drivers).hasSize(1);
        assertThat(drivers.get(0).getUsername()).isEqualTo("jane_doe");
    }

    @Test
    void testFindTop10ByOrderByRatingDesc() {
        List<Driver> drivers = driverRepository.findTop10ByOrderByRatingDesc();

        assertThat(drivers).hasSize(2);
        assertThat(drivers.get(0).getUsername()).isEqualTo("jane_doe");
        assertThat(drivers.get(1).getUsername()).isEqualTo("john_doe");
    }
}
