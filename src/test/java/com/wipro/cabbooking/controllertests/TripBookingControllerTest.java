package com.wipro.cabbooking.controllertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wipro.cabbooking.controller.TripBookingController;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.exception.TripBookingException;
import com.wipro.cabbooking.service.TripBookingService;
import com.wipro.cabbooking.entity.TripStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TripBookingController.class)
public class TripBookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripBookingService tripBookingService;

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookCab_Success() throws Exception {
        TripBookingDTO tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setTripId(1);
        tripBookingDTO.setCustomerId(1);
        tripBookingDTO.setDriverId(1);
        tripBookingDTO.setCabId(1);
        tripBookingDTO.setPickupLocation("Pickup Location");
        tripBookingDTO.setDropoffLocation("Dropoff Location");
        tripBookingDTO.setStartDate(LocalDateTime.now().minusDays(1));
        tripBookingDTO.setEndDate(LocalDateTime.now());
        tripBookingDTO.setStatus(TripStatus.PENDING);
        tripBookingDTO.setDistanceInKm(10.0f);
        tripBookingDTO.setBill(100.0f);

        when(tripBookingService.bookCab(anyInt(), any(TripBookingDTO.class))).thenReturn(tripBookingDTO);

        mockMvc.perform(post("/trip-bookings/book/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(tripBookingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tripId").value(1))
                .andExpect(jsonPath("$.pickupLocation").value("Pickup Location"));
    }

    @Test
    void testBookCab_Failure() throws Exception {
        when(tripBookingService.bookCab(anyInt(), any(TripBookingDTO.class)))
                .thenThrow(new TripBookingException("TripBookingException"));

        mockMvc.perform(post("/trip-bookings/book/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new TripBookingDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveTripBooking() throws Exception {
        TripBookingDTO tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setTripId(1);
        tripBookingDTO.setCustomerId(1);
        tripBookingDTO.setDriverId(1);
        tripBookingDTO.setCabId(1);
        tripBookingDTO.setPickupLocation("Pickup Location");
        tripBookingDTO.setDropoffLocation("Dropoff Location");
        tripBookingDTO.setStartDate(LocalDateTime.now().minusDays(1));
        tripBookingDTO.setEndDate(LocalDateTime.now());
        tripBookingDTO.setStatus(TripStatus.PENDING);
        tripBookingDTO.setDistanceInKm(10.0f);
        tripBookingDTO.setBill(100.0f);

        when(tripBookingService.saveTripBooking(any(TripBookingDTO.class))).thenReturn(tripBookingDTO);

        mockMvc.perform(post("/trip-bookings/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(tripBookingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tripId").value(1))
                .andExpect(jsonPath("$.pickupLocation").value("Pickup Location"));
    }

    @Test
    void testUpdateTripBooking() throws Exception {
        TripBookingDTO tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setTripId(1);
        tripBookingDTO.setCustomerId(1);
        tripBookingDTO.setDriverId(1);
        tripBookingDTO.setCabId(1);
        tripBookingDTO.setPickupLocation("Pickup Location");
        tripBookingDTO.setDropoffLocation("Dropoff Location");
        tripBookingDTO.setStartDate(LocalDateTime.now().minusDays(1));
        tripBookingDTO.setEndDate(LocalDateTime.now());
        tripBookingDTO.setStatus(TripStatus.PENDING);
        tripBookingDTO.setDistanceInKm(10.0f);
        tripBookingDTO.setBill(100.0f);

        when(tripBookingService.updateTripBooking(any(TripBookingDTO.class))).thenReturn(tripBookingDTO);

        mockMvc.perform(put("/trip-bookings/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(tripBookingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tripId").value(1))
                .andExpect(jsonPath("$.pickupLocation").value("Pickup Location"));
    }

    @Test
    void testCancelTripBooking() throws Exception {
        doNothing().when(tripBookingService).cancelTripBooking(anyInt());

        mockMvc.perform(delete("/trip-bookings/cancel/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTripBookingById() throws Exception {
        TripBookingDTO tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setTripId(1);
        tripBookingDTO.setPickupLocation("Pickup Location");

        when(tripBookingService.getTripBookingById(anyInt())).thenReturn(tripBookingDTO);

        mockMvc.perform(get("/trip-bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tripId").value(1))
                .andExpect(jsonPath("$.pickupLocation").value("Pickup Location"));
    }

    @Test
    void testGetAllTripBookings() throws Exception {
        TripBookingDTO tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setTripId(1);
        tripBookingDTO.setPickupLocation("Pickup Location");

        when(tripBookingService.getAllTripBookings()).thenReturn(Collections.singletonList(tripBookingDTO));

        mockMvc.perform(get("/trip-bookings/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tripId").value(1))
                .andExpect(jsonPath("$[0].pickupLocation").value("Pickup Location"));
    }

    @Test
    void testViewBookingHistory() throws Exception {
        TripBookingDTO tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setTripId(1);
        tripBookingDTO.setPickupLocation("Pickup Location");

        when(tripBookingService.viewBookingHistory(anyInt())).thenReturn(Collections.singletonList(tripBookingDTO));

        mockMvc.perform(get("/trip-bookings/history/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tripId").value(1))
                .andExpect(jsonPath("$[0].pickupLocation").value("Pickup Location"));
    }

    @Test
    void testViewDriverBookingHistory() throws Exception {
        TripBookingDTO tripBookingDTO = new TripBookingDTO();
        tripBookingDTO.setTripId(1);
        tripBookingDTO.setPickupLocation("Pickup Location");

        when(tripBookingService.viewDriverBookingHistory(anyInt())).thenReturn(Collections.singletonList(tripBookingDTO));

        mockMvc.perform(get("/trip-bookings/driver-history/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tripId").value(1))
                .andExpect(jsonPath("$[0].pickupLocation").value("Pickup Location"));
    }

    // Helper method to convert objects to JSON strings
    private static String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
