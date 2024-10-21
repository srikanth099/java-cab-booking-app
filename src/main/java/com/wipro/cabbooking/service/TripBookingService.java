package com.wipro.cabbooking.service;

import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.exception.TripBookingException;

import java.util.List;

public interface TripBookingService {

    TripBookingDTO saveTripBooking(TripBookingDTO tripBookingDTO) throws TripBookingException;

    TripBookingDTO updateTripBooking(TripBookingDTO tripBookingDTO) throws TripBookingException;

    void cancelTripBooking(int tripId) throws TripBookingException;

    TripBookingDTO getTripBookingById(int tripId) throws TripBookingException;

    List<TripBookingDTO> getAllTripBookings();

    List<TripBookingDTO> viewBookingHistory(int customerId) throws TripBookingException;

    TripBookingDTO bookCab(int customerId, TripBookingDTO tripBookingDTO) throws TripBookingException;

    List<TripBookingDTO> viewDriverBookingHistory(int driverId) throws TripBookingException;
}
