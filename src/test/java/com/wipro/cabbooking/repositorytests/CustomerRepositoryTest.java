package com.wipro.cabbooking.repositorytests;

import com.wipro.cabbooking.entity.Customer;
import com.wipro.cabbooking.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional; // Import the correct Transactional annotation

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        // Clean the database before each test
        customerRepository.deleteAll();

        // Insert test data
        Customer customer1 = new Customer();
        customer1.setUsername("john_doe");
        customer1.setPassword("password1");
        customer1.setAddress("123 Elm Street");
        customer1.setEmail("john@example.com");
        customer1.setMobileNumber("555-1234");

        Customer customer2 = new Customer();
        customer2.setUsername("jane_doe");
        customer2.setPassword("password2");
        customer2.setAddress("456 Oak Avenue");
        customer2.setEmail("jane@example.com");
        customer2.setMobileNumber("555-5678");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    @Test
    void testFindByUsernameContaining() {
        // Test with a username that is partially contained in one of the customers
        List<Customer> customers = customerRepository.findByUsernameContaining("john");

        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getUsername()).isEqualTo("john_doe");
    }

    @Test
    void testFindByUsernameContaining_NoMatch() {
        // Test with a username that does not match any customer
        List<Customer> customers = customerRepository.findByUsernameContaining("nonexistent");

        assertThat(customers).isEmpty();
    }

    @Test
    void testFindByUsernameContaining_MultipleMatches() {
        // Test with a username that matches multiple customers
        List<Customer> customers = customerRepository.findByUsernameContaining("doe");

        assertThat(customers).hasSize(2);
        assertThat(customers).extracting("username").containsExactlyInAnyOrder("john_doe", "jane_doe");
    }
}
