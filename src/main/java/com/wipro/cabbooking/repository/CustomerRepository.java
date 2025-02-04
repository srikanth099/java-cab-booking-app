package com.wipro.cabbooking.repository;

import com.wipro.cabbooking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByUsernameContaining(String username);
}
