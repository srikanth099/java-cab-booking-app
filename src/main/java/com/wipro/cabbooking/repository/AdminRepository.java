package com.wipro.cabbooking.repository;

import com.wipro.cabbooking.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    // Add custom queries if needed
}
