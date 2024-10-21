package com.wipro.cabbooking.controllertests;

import com.wipro.cabbooking.controller.CustomerController;
import com.wipro.cabbooking.dto.CustomerDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.dto.CabDTO;
import com.wipro.cabbooking.exception.CustomerException;
import com.wipro.cabbooking.exception.TripBookingException;
import com.wipro.cabbooking.service.CustomerService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testRegisterCustomer_Success() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1);
        customerDTO.setUsername("john_doe");
        customerDTO.setPassword("password123");
        customerDTO.setAddress("123 Main St");
        customerDTO.setMobileNumber("1234567890");
        customerDTO.setEmail("john@example.com");
        when(customerService.registerCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(post("/customers/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":1,\"username\":\"john_doe\",\"password\":\"password123\",\"address\":\"123 Main St\",\"mobileNumber\":\"1234567890\",\"email\":\"john@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.mobileNumber").value("1234567890"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testRegisterCustomer_BadRequest() throws Exception {
        when(customerService.registerCustomer(any(CustomerDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/customers/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":1,\"username\":\"john_doe\",\"password\":\"password123\",\"address\":\"123 Main St\",\"mobileNumber\":\"1234567890\",\"email\":\"john@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCustomerProfile_Success() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1);
        customerDTO.setUsername("john_doe_updated");
        customerDTO.setPassword("newpassword123");
        customerDTO.setAddress("456 Elm St");
        customerDTO.setMobileNumber("0987654321");
        customerDTO.setEmail("john_updated@example.com");
        when(customerService.updateCustomerProfile(any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(put("/customers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":1,\"username\":\"john_doe_updated\",\"password\":\"newpassword123\",\"address\":\"456 Elm St\",\"mobileNumber\":\"0987654321\",\"email\":\"john_updated@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.username").value("john_doe_updated"))
                .andExpect(jsonPath("$.password").value("newpassword123"))
                .andExpect(jsonPath("$.address").value("456 Elm St"))
                .andExpect(jsonPath("$.mobileNumber").value("0987654321"))
                .andExpect(jsonPath("$.email").value("john_updated@example.com"));
    }

    @Test
    public void testUpdateCustomerProfile_NotFound() throws Exception {
        when(customerService.updateCustomerProfile(any(CustomerDTO.class))).thenThrow(new CustomerException());

        mockMvc.perform(put("/customers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":1,\"username\":\"john_doe_updated\",\"password\":\"newpassword123\",\"address\":\"456 Elm St\",\"mobileNumber\":\"0987654321\",\"email\":\"john_updated@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testViewAvailableCabs_Success() throws Exception {
        CabDTO cabDTO = new CabDTO(); // Initialize with valid data
        List<CabDTO> cabs = Collections.singletonList(cabDTO);
        when(customerService.viewAvailableCabs()).thenReturn(cabs);

        mockMvc.perform(get("/customers/available-cabs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cabId").value(cabDTO.getCabId())) // Update as needed
                .andExpect(jsonPath("$[0].carType").value(cabDTO.getCarType())) // Update as needed
                .andExpect(jsonPath("$[0].perKmRate").value(cabDTO.getPerKmRate())); // Update as needed
    }

//    @Test
//    public void testBookCab_Success() throws Exception {
//        TripBookingDTO tripBookingDTO = new TripBookingDTO(); // Initialize with valid data
//        when(customerService.bookCab(anyInt(), any(TripBookingDTO.class))).thenReturn(tripBookingDTO);
//
//        mockMvc.perform(post("/customers/1/book")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"tripId\":1,\"customerId\":1,\"driverId\":1,\"cabId\":1,\"pickupLocation\":\"Location A\",\"dropoffLocation\":\"Location B\",\"startDate\":\"2024-01-01T10:00:00\",\"endDate\":\"2024-01-01T12:00:00\",\"status\":\"PENDING\",\"distanceInKm\":10.5,\"bill\":100.0}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.tripId").value(tripBookingDTO.getTripId())) // Update as needed
//                .andExpect(jsonPath("$.customerId").value(tripBookingDTO.getCustomerId())) // Update as needed
//                .andExpect(jsonPath("$.driverId").value(tripBookingDTO.getDriverId())) // Update as needed
//                .andExpect(jsonPath("$.cabId").value(tripBookingDTO.getCabId())) // Update as needed
//                .andExpect(jsonPath("$.pickupLocation").value(tripBookingDTO.getPickupLocation())) // Update as needed
//                .andExpect(jsonPath("$.dropoffLocation").value(tripBookingDTO.getDropoffLocation())) // Update as needed
//                .andExpect(jsonPath("$.startDate").value(tripBookingDTO.getStartDate().toString())) // Update as needed
//                .andExpect(jsonPath("$.endDate").value(tripBookingDTO.getEndDate().toString())) // Update as needed
//                .andExpect(jsonPath("$.status").value(tripBookingDTO.getStatus().name())) // Update as needed
//                .andExpect(jsonPath("$.distanceInKm").value(tripBookingDTO.getDistanceInKm())) // Update as needed
//                .andExpect(jsonPath("$.bill").value(tripBookingDTO.getBill())); // Update as needed
//    }

    @Test
    public void testBookCab_NotFound() throws Exception {
        when(customerService.bookCab(anyInt(), any(TripBookingDTO.class)))
                .thenThrow(new CustomerException());

        mockMvc.perform(post("/customers/1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tripId\":1,\"customerId\":1,\"driverId\":1,\"cabId\":1,\"pickupLocation\":\"Location A\",\"dropoffLocation\":\"Location B\",\"startDate\":\"2024-01-01T10:00:00\",\"endDate\":\"2024-01-01T12:00:00\",\"status\":\"PENDING\",\"distanceInKm\":10.5,\"bill\":100.0}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCancelBooking_Success() throws Exception {
        doNothing().when(customerService).cancelBooking(anyInt());

        mockMvc.perform(delete("/customers/cancel/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCancelBooking_NotFound() throws Exception {
        doThrow(new TripBookingException("Trip booking not found")).when(customerService).cancelBooking(anyInt());

        mockMvc.perform(delete("/customers/cancel/1"))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void testViewBookingHistory_Success() throws Exception {
//        TripBookingDTO tripBookingDTO = new TripBookingDTO(); // Initialize with valid data
//        List<TripBookingDTO> bookingHistory = Collections.singletonList(tripBookingDTO);
//        when(customerService.viewBookingHistory(anyInt())).thenReturn(bookingHistory);
//
//        mockMvc.perform(get("/customers/1/booking-history"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].tripId").value(tripBookingDTO.getTripId())) // Update as needed
//                .andExpect(jsonPath("$[0].customerId").value(tripBookingDTO.getCustomerId())) // Update as needed
//                .andExpect(jsonPath("$[0].driverId").value(tripBookingDTO.getDriverId())) // Update as needed
//                .andExpect(jsonPath("$[0].cabId").value(tripBookingDTO.getCabId())) // Update as needed
//                .andExpect(jsonPath("$[0].pickupLocation").value(tripBookingDTO.getPickupLocation())) // Update as needed
//                .andExpect(jsonPath("$[0].dropoffLocation").value(tripBookingDTO.getDropoffLocation())) // Update as needed
//                .andExpect(jsonPath("$[0].startDate").value(tripBookingDTO.getStartDate().toString())) // Update as needed
//                .andExpect(jsonPath("$[0].endDate").value(tripBookingDTO.getEndDate().toString())) // Update as needed
//                .andExpect(jsonPath("$[0].status").value(tripBookingDTO.getStatus().name())) // Update as needed
//                .andExpect(jsonPath("$[0].distanceInKm").value(tripBookingDTO.getDistanceInKm())) // Update as needed
//                .andExpect(jsonPath("$[0].bill").value(tripBookingDTO.getBill())); // Update as needed
//    }

    @Test
    public void testGetCustomerById_Success() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1);
        customerDTO.setUsername("john_doe");
        customerDTO.setPassword("password123");
        customerDTO.setAddress("123 Main St");
        customerDTO.setMobileNumber("1234567890");
        customerDTO.setEmail("john@example.com");
        when(customerService.getCustomerById(anyInt())).thenReturn(customerDTO);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.mobileNumber").value("1234567890"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testGetCustomerById_NotFound() throws Exception {
        when(customerService.getCustomerById(anyInt())).thenThrow(new CustomerException());

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllCustomers_Success() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1);
        customerDTO.setUsername("john_doe");
        customerDTO.setPassword("password123");
        customerDTO.setAddress("123 Main St");
        customerDTO.setMobileNumber("1234567890");
        customerDTO.setEmail("john@example.com");
        List<CustomerDTO> customers = Collections.singletonList(customerDTO);
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customers/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].username").value("john_doe"))
                .andExpect(jsonPath("$[0].password").value("password123"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].mobileNumber").value("1234567890"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    public void testSearchCustomersByUsername_Success() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1);
        customerDTO.setUsername("john_doe");
        customerDTO.setPassword("password123");
        customerDTO.setAddress("123 Main St");
        customerDTO.setMobileNumber("1234567890");
        customerDTO.setEmail("john@example.com");
        List<CustomerDTO> customers = Collections.singletonList(customerDTO);
        when(customerService.searchCustomersByUsername(anyString())).thenReturn(customers);

        mockMvc.perform(get("/customers/search?username=JohnDoe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].username").value("john_doe"))
                .andExpect(jsonPath("$[0].password").value("password123"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].mobileNumber").value("1234567890"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    public void testSearchCustomersByUsername_NotFound() throws Exception {
        when(customerService.searchCustomersByUsername(anyString())).thenThrow(new CustomerException());

        mockMvc.perform(get("/customers/search?username=JohnDoe"))
                .andExpect(status().isNotFound());
    }
}
