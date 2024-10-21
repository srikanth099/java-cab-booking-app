package com.wipro.cabbooking.servicetests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.wipro.cabbooking.dto.AdminDTO;
import com.wipro.cabbooking.entity.Admin;
import com.wipro.cabbooking.exception.AdminException;
import com.wipro.cabbooking.repository.AdminRepository;
import com.wipro.cabbooking.serviceimpl.AdminServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminRepository adminRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAdmin() {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setUsername("adminuser");
        adminDTO.setPassword("password");
        adminDTO.setAddress("Admin Address");
        adminDTO.setMobileNumber("1234567890");
        adminDTO.setEmail("admin@example.com");

        Admin admin = new Admin();
        admin.setUsername("adminuser");
        admin.setPassword("password");
        admin.setAddress("Admin Address");
        admin.setMobileNumber("1234567890");
        admin.setEmail("admin@example.com");

        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        AdminDTO savedAdminDTO = adminService.saveAdmin(adminDTO);

        assertEquals(adminDTO.getUsername(), savedAdminDTO.getUsername());
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void testUpdateAdmin() throws AdminException {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminId(1);
        adminDTO.setUsername("updatedadminuser");
        adminDTO.setPassword("updatedpassword");
        adminDTO.setAddress("Updated Address");
        adminDTO.setMobileNumber("0987654321");
        adminDTO.setEmail("updatedadmin@example.com");

        Admin existingAdmin = new Admin();
        existingAdmin.setAdminId(1);
        existingAdmin.setUsername("adminuser");
        existingAdmin.setPassword("password");
        existingAdmin.setAddress("Admin Address");
        existingAdmin.setMobileNumber("1234567890");
        existingAdmin.setEmail("admin@example.com");

        Admin updatedAdmin = new Admin();
        updatedAdmin.setAdminId(1);
        updatedAdmin.setUsername("updatedadminuser");
        updatedAdmin.setPassword("updatedpassword");
        updatedAdmin.setAddress("Updated Address");
        updatedAdmin.setMobileNumber("0987654321");
        updatedAdmin.setEmail("updatedadmin@example.com");

        when(adminRepository.findById(1)).thenReturn(Optional.of(existingAdmin));
        when(adminRepository.save(any(Admin.class))).thenReturn(updatedAdmin);

        AdminDTO updatedAdminDTO = adminService.updateAdmin(adminDTO);

        assertEquals(adminDTO.getUsername(), updatedAdminDTO.getUsername());
        assertEquals(adminDTO.getAddress(), updatedAdminDTO.getAddress());
        verify(adminRepository, times(1)).findById(1);
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void testUpdateAdminNotFound() {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminId(1);

        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        AdminException thrown = assertThrows(AdminException.class, () -> adminService.updateAdmin(adminDTO));
        assertEquals("Admin not found", thrown.getMessage());
    }

    @Test
    void testDeleteAdmin() throws AdminException {
        when(adminRepository.existsById(1)).thenReturn(true);

        adminService.deleteAdmin(1);

        verify(adminRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteAdminNotFound() {
        when(adminRepository.existsById(1)).thenReturn(false);

        AdminException thrown = assertThrows(AdminException.class, () -> adminService.deleteAdmin(1));
        assertEquals("Admin not found", thrown.getMessage());
    }

    @Test
    void testGetAdminById() throws AdminException {
        Admin admin = new Admin();
        admin.setAdminId(1);
        admin.setUsername("adminuser");

        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        AdminDTO adminDTO = adminService.getAdminById(1);

        assertEquals(admin.getUsername(), adminDTO.getUsername());
        verify(adminRepository, times(1)).findById(1);
    }

    @Test
    void testGetAdminByIdNotFound() {
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        AdminException thrown = assertThrows(AdminException.class, () -> adminService.getAdminById(1));
        assertEquals("Admin not found", thrown.getMessage());
    }

    @Test
    void testGetAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        Admin admin = new Admin();
        admin.setAdminId(1);
        admin.setUsername("adminuser");
        admins.add(admin);

        when(adminRepository.findAll()).thenReturn(admins);

        List<AdminDTO> adminDTOs = adminService.getAllAdmins();

        assertEquals(1, adminDTOs.size());
        assertEquals(admin.getUsername(), adminDTOs.get(0).getUsername());
        verify(adminRepository, times(1)).findAll();
    }

    // Additional test cases for Customer, Driver, Cab, and TripBooking services
    
    // Test cases for CustomerService
    // Test cases for DriverService
    // Test cases for CabService
    // Test cases for TripBookingService
}
