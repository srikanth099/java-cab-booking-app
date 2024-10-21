package com.wipro.cabbooking.serviceimpl;

import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.entity.TripBooking;
import com.wipro.cabbooking.entity.Customer;
import com.wipro.cabbooking.entity.Driver;
import com.wipro.cabbooking.entity.Cab;
import com.wipro.cabbooking.entity.TripStatus;
import com.wipro.cabbooking.exception.TripBookingException;
import com.wipro.cabbooking.repository.TripBookingRepository;
import com.wipro.cabbooking.repository.CustomerRepository;
import com.wipro.cabbooking.repository.DriverRepository;
import com.wipro.cabbooking.repository.CabRepository;
import com.wipro.cabbooking.service.TripBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripBookingServiceImpl implements TripBookingService {

    @Autowired
    private TripBookingRepository tripBookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CabRepository cabRepository;

    @Override
    public TripBookingDTO saveTripBooking(TripBookingDTO tripBookingDTO) throws TripBookingException {
        TripBooking tripBooking = new TripBooking();
        tripBooking.setCustomer(customerRepository.findById(tripBookingDTO.getCustomerId())
                .orElseThrow(() -> new TripBookingException("Customer not found with ID: " + tripBookingDTO.getCustomerId())));
        tripBooking.setDriver(driverRepository.findById(tripBookingDTO.getDriverId())
                .orElseThrow(() -> new TripBookingException("Driver not found with ID: " + tripBookingDTO.getDriverId())));
        
        if (tripBookingDTO.getCabId() > 0) {
            Cab cab = cabRepository.findById(tripBookingDTO.getCabId())
                    .orElseThrow(() -> new TripBookingException("Cab not found with ID: " + tripBookingDTO.getCabId()));
            tripBooking.setCab(cab);
        }

        tripBooking.setPickupLocation(tripBookingDTO.getPickupLocation());
        tripBooking.setDropoffLocation(tripBookingDTO.getDropoffLocation());
        tripBooking.setStartDate(tripBookingDTO.getStartDate());
        tripBooking.setEndDate(tripBookingDTO.getEndDate());
        tripBooking.setStatus(tripBookingDTO.getStatus());
        tripBooking.setDistanceInKm(tripBookingDTO.getDistanceInKm());
        tripBooking.setBill(tripBookingDTO.getBill());

        TripBooking savedTripBooking = tripBookingRepository.save(tripBooking);
        return convertToDTO(savedTripBooking);
    }
    @Override
    public TripBookingDTO updateTripBooking(TripBookingDTO tripBookingDTO) {
        // Check if the TripBooking with the given ID exists
        if (!tripBookingRepository.existsById(tripBookingDTO.getTripId())) {
            throw new TripBookingException("Trip booking not found");
        }
        
        // Convert DTO to Entity
        TripBooking tripBooking = convertToEntity(tripBookingDTO);
        
        // Ensure distanceInKm and other primitive fields are not null
        if (tripBooking.getDistanceInKm() == null) {
            throw new TripBookingException("Distance in Km cannot be null");
        }
        
        // Save the updated TripBooking entity
        TripBooking updatedTripBooking = tripBookingRepository.save(tripBooking);
        
        // Convert the updated entity back to DTO
        return convertToDTO(updatedTripBooking);
    }

    @Override
    public void cancelTripBooking(int tripId) {
        if (!tripBookingRepository.existsById(tripId)) {
            throw new TripBookingException("Trip booking not found");
        }
        TripBooking tripBooking = tripBookingRepository.findById(tripId).orElseThrow(() -> new TripBookingException("Trip booking not found"));
        tripBooking.setStatus(TripStatus.CANCELLED);
        tripBookingRepository.save(tripBooking);
    }

    @Override
    public TripBookingDTO getTripBookingById(int tripId) {
        TripBooking tripBooking = tripBookingRepository.findById(tripId).orElseThrow(() -> new TripBookingException("Trip booking not found"));
        return convertToDTO(tripBooking);
    }

    @Override
    public List<TripBookingDTO> getAllTripBookings() {
        return tripBookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripBookingDTO> viewBookingHistory(int customerId) {
        return tripBookingRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TripBookingDTO bookCab(int customerId, TripBookingDTO tripBookingDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new TripBookingException("Customer not found"));
        Driver driver = driverRepository.findById(tripBookingDTO.getDriverId())
                .orElseThrow(() -> new TripBookingException("Driver not found"));
        Cab cab = cabRepository.findById(tripBookingDTO.getCabId())
                .orElseThrow(() -> new TripBookingException("Cab not found"));

        // Create and save the new TripBooking
        TripBooking tripBooking = new TripBooking();
        tripBooking.setCustomer(customer);
        tripBooking.setDriver(driver);
        tripBooking.setCab(cab);
        tripBooking.setPickupLocation(tripBookingDTO.getPickupLocation());
        tripBooking.setDropoffLocation(tripBookingDTO.getDropoffLocation());
        tripBooking.setStartDate(tripBookingDTO.getStartDate());
        tripBooking.setEndDate(tripBookingDTO.getEndDate());
        tripBooking.setStatus(TripStatus.PENDING); // Set initial status
        tripBooking.setDistanceInKm(tripBookingDTO.getDistanceInKm());
        // Calculate and set the bill
        float bill = calculateBill(tripBookingDTO.getDistanceInKm(), cab.getPerKmRate());
        tripBooking.setBill(bill);

        TripBooking savedTripBooking = tripBookingRepository.save(tripBooking);
        return convertToDTO(savedTripBooking);
    }
    @Override
    public List<TripBookingDTO> viewDriverBookingHistory(int driverId) throws TripBookingException {
        // Fetch trip bookings associated with the driverId
        List<TripBooking> tripBookings = tripBookingRepository.findByDriverId(driverId);

        if (tripBookings.isEmpty()) {
            throw new TripBookingException("No booking history found for the driver with ID: " + driverId);
        }

        // Convert each TripBooking entity to a TripBookingDTO and return the list
        return tripBookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TripBookingDTO convertToDTO(TripBooking tripBooking) {
        TripBookingDTO dto = new TripBookingDTO();
        dto.setTripId(tripBooking.getTripId());
        dto.setCustomerId(tripBooking.getCustomer().getCustomerId());
        dto.setDriverId(tripBooking.getDriver().getDriverId());
        dto.setCabId(tripBooking.getCab().getCabId());
        dto.setPickupLocation(tripBooking.getPickupLocation());
        dto.setDropoffLocation(tripBooking.getDropoffLocation());
        dto.setStartDate(tripBooking.getStartDate());
        dto.setEndDate(tripBooking.getEndDate());
        dto.setStatus(tripBooking.getStatus());
        dto.setDistanceInKm(tripBooking.getDistanceInKm()); // Correctly set distance
        dto.setBill(tripBooking.getBill()); // Ensure bill is set
        return dto;
    }

    private TripBooking convertToEntity(TripBookingDTO dto) {
        TripBooking tripBooking = new TripBooking();
        tripBooking.setTripId(dto.getTripId());
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new TripBookingException("Customer not found"));
        Driver driver = driverRepository.findById(dto.getDriverId())
                .orElseThrow(() -> new TripBookingException("Driver not found"));
        Cab cab = cabRepository.findById(dto.getCabId())
                .orElseThrow(() -> new TripBookingException("Cab not found"));

        tripBooking.setCustomer(customer);
        tripBooking.setDriver(driver);
        tripBooking.setCab(cab);
        tripBooking.setPickupLocation(dto.getPickupLocation());
        tripBooking.setDropoffLocation(dto.getDropoffLocation());
        tripBooking.setStartDate(dto.getStartDate());
        tripBooking.setEndDate(dto.getEndDate());
        tripBooking.setStatus(dto.getStatus());
        tripBooking.setDistanceInKm(dto.getDistanceInKm()); // Ensure correct setting

        // Calculate and set the bill
        float bill = calculateBill(dto.getDistanceInKm(), cab.getPerKmRate());
        tripBooking.setBill(bill);

        return tripBooking;
    }

    private float calculateBill(float distanceInKm, float perKmRate) {
        return distanceInKm * perKmRate;
    }
	
}
