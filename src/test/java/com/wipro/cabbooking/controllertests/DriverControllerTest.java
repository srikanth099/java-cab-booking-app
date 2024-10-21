package com.wipro.cabbooking.controllertests;

import com.wipro.cabbooking.controller.DriverController;
import com.wipro.cabbooking.dto.DriverDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @InjectMocks
    private DriverController driverController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveDriver_Success() throws Exception {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setDriverId(1);
        driverDTO.setUsername("john_doe");
        driverDTO.setPassword("password123");
        driverDTO.setAddress("123 Main St");
        driverDTO.setMobileNumber("1234567890");
        driverDTO.setEmail("john@example.com");
        driverDTO.setLicenseNo("XYZ123");
        driverDTO.setRating(4.5f);

        when(driverService.saveDriver(any(DriverDTO.class))).thenReturn(driverDTO);

        mockMvc.perform(post("/drivers/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"driverId\":1,\"username\":\"john_doe\",\"password\":\"password123\",\"address\":\"123 Main St\",\"mobileNumber\":\"1234567890\",\"email\":\"john@example.com\",\"licenseNo\":\"XYZ123\",\"rating\":4.5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverId").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.mobileNumber").value("1234567890"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.licenseNo").value("XYZ123"))
                .andExpect(jsonPath("$.rating").value(4.5));
    }

    @Test
    public void testUpdateDriver_Success() throws Exception {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setDriverId(1);
        driverDTO.setUsername("john_doe");
        driverDTO.setPassword("password123");
        driverDTO.setAddress("123 Main St");
        driverDTO.setMobileNumber("1234567890");
        driverDTO.setEmail("john@example.com");
        driverDTO.setLicenseNo("XYZ123");
        driverDTO.setRating(4.5f);

        when(driverService.updateDriver(any(DriverDTO.class))).thenReturn(driverDTO);

        mockMvc.perform(put("/drivers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"driverId\":1,\"username\":\"john_doe\",\"password\":\"password123\",\"address\":\"123 Main St\",\"mobileNumber\":\"1234567890\",\"email\":\"john@example.com\",\"licenseNo\":\"XYZ123\",\"rating\":4.5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverId").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.mobileNumber").value("1234567890"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.licenseNo").value("XYZ123"))
                .andExpect(jsonPath("$.rating").value(4.5));
    }

    @Test
    public void testDeleteDriver_Success() throws Exception {
        doNothing().when(driverService).deleteDriver(anyInt());

        mockMvc.perform(delete("/drivers/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDriverById_Success() throws Exception {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setDriverId(1);
        driverDTO.setUsername("john_doe");
        driverDTO.setPassword("password123");
        driverDTO.setAddress("123 Main St");
        driverDTO.setMobileNumber("1234567890");
        driverDTO.setEmail("john@example.com");
        driverDTO.setLicenseNo("XYZ123");
        driverDTO.setRating(4.5f);

        when(driverService.getDriverById(anyInt())).thenReturn(driverDTO);

        mockMvc.perform(get("/drivers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverId").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.mobileNumber").value("1234567890"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.licenseNo").value("XYZ123"))
                .andExpect(jsonPath("$.rating").value(4.5));
    }
    @Test
    public void testGetAllDrivers_Success() throws Exception {
        DriverDTO driverDTO1 = new DriverDTO();
        driverDTO1.setDriverId(1);
        driverDTO1.setUsername("john_doe");

        DriverDTO driverDTO2 = new DriverDTO();
        driverDTO2.setDriverId(2);
        driverDTO2.setUsername("jane_doe");

        List<DriverDTO> drivers = new ArrayList<>();
        drivers.add(driverDTO1);
        drivers.add(driverDTO2);

        when(driverService.getAllDrivers()).thenReturn(drivers);

        mockMvc.perform(get("/drivers/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].driverId").value(1))
                .andExpect(jsonPath("$[0].username").value("john_doe"))
                .andExpect(jsonPath("$[1].driverId").value(2))
                .andExpect(jsonPath("$[1].username").value("jane_doe"));
    }

    @Test
    public void testGetBookingHistory_Success() throws Exception {
        TripBookingDTO tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setTripId(1);
        tripBookingDTO.setCustomerId(1);
        tripBookingDTO.setDriverId(1);

        List<TripBookingDTO> bookingHistory = new ArrayList<>();
        bookingHistory.add(tripBookingDTO);

        when(driverService.getBookingHistory(anyInt())).thenReturn(bookingHistory);

        mockMvc.perform(get("/drivers/1/booking-history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tripId").value(1))
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].driverId").value(1));
    }

    @Test
    public void testViewBestDrivers_Success() throws Exception {
        DriverDTO driverDTO1 = new DriverDTO();
        driverDTO1.setDriverId(1);
        driverDTO1.setUsername("john_doe");

        DriverDTO driverDTO2 = new DriverDTO();
        driverDTO2.setDriverId(2);
        driverDTO2.setUsername("jane_doe");

        List<DriverDTO> bestDrivers = new ArrayList<>();
        bestDrivers.add(driverDTO1);
        bestDrivers.add(driverDTO2);

        when(driverService.viewBestDrivers()).thenReturn(bestDrivers);

        mockMvc.perform(get("/drivers/best"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].driverId").value(1))
                .andExpect(jsonPath("$[0].username").value("john_doe"))
                .andExpect(jsonPath("$[1].driverId").value(2))
                .andExpect(jsonPath("$[1].username").value("jane_doe"));
    }

    // Add tests for error cases as needed, similar to the success tests
}
