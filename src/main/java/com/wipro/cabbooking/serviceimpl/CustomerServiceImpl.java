package com.wipro.cabbooking.serviceimpl;

import com.wipro.cabbooking.dto.CustomerDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.dto.CabDTO;
import com.wipro.cabbooking.entity.Customer;
import com.wipro.cabbooking.entity.Driver;
import com.wipro.cabbooking.entity.TripBooking;
import com.wipro.cabbooking.entity.Cab;
import com.wipro.cabbooking.exception.CustomerException;
import com.wipro.cabbooking.repository.CustomerRepository;
import com.wipro.cabbooking.repository.DriverRepository;
import com.wipro.cabbooking.repository.TripBookingRepository;
import com.wipro.cabbooking.repository.CabRepository;
import com.wipro.cabbooking.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TripBookingRepository tripBookingRepository;
    private final CabRepository cabRepository;
    private final DriverRepository driverRepository;  // Declare DriverRepository

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               TripBookingRepository tripBookingRepository,
                               CabRepository cabRepository,
                               DriverRepository driverRepository) {  // Inject DriverRepository
        this.customerRepository = customerRepository;
        this.tripBookingRepository = tripBookingRepository;
        this.cabRepository = cabRepository;
        this.driverRepository = driverRepository;  // Assign DriverRepository
    }
    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomerProfile(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getCustomerId())
                .orElseThrow(() -> new CustomerException("Customer not found"));
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setAddress(customerDTO.getAddress());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        customer.setEmail(customerDTO.getEmail());
        Customer updatedCustomer = customerRepository.save(customer);
        return convertToDTO(updatedCustomer);
    }

    @Override
    public List<CabDTO> viewAvailableCabs() {
        List<Cab> cabs = cabRepository.findAll();
        return cabs.stream()
                   .map(this::convertToCabDTO)
                   .collect(Collectors.toList());
    }

    private CabDTO convertToCabDTO(Cab cab) {
        if (cab == null) {
            return null;
        }
        CabDTO cabDTO = new CabDTO();
        cabDTO.setCabId(cab.getCabId());
        cabDTO.setCarType(cab.getCarType());
        cabDTO.setPerKmRate(cab.getPerKmRate());
        return cabDTO;
    }
    @Override
    public void deleteCustomer(int customerId) throws CustomerException {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerException("Customer not found");
        }
        customerRepository.deleteById(customerId);
    }

    @Override
    public TripBookingDTO bookCab(int customerId, TripBookingDTO tripBookingDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerException("Customer not found"));

        TripBooking tripBooking = convertToEntity(tripBookingDTO);
        tripBooking.setCustomer(customer);

//         Optionally set the driver and cab if available
        Optional<Cab> cabOptional = cabRepository.findById(tripBookingDTO.getCabId());
        cabOptional.ifPresent(tripBooking::setCab);

        // Set the driver if necessary
         Optional<Driver> driverOptional = driverRepository.findById(tripBookingDTO.getDriverId());
         driverOptional.ifPresent(tripBooking::setDriver);

        TripBooking savedTripBooking = tripBookingRepository.save(tripBooking);
        return convertToDTO(savedTripBooking);
    }

    @Override
    public void cancelBooking(int tripId) {
        TripBooking tripBooking = tripBookingRepository.findById(tripId)
                .orElseThrow(() -> new CustomerException("TripBooking not found"));
        tripBookingRepository.delete(tripBooking);
    }

    @Override
    public List<TripBookingDTO> viewBookingHistory(int customerId) {
        return tripBookingRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    @Override
    public CustomerDTO getCustomerById(int customerId) throws CustomerException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerException("Customer not found"));
        return convertToDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> searchCustomersByUsername(String username) throws CustomerException {
        List<Customer> customers = customerRepository.findByUsernameContaining(username);
        if (customers.isEmpty()) {
            throw new CustomerException("No customers found with the given username");
        }
        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper methods for converting entities to DTOs and vice versa
    private CustomerDTO convertToDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setUsername(customer.getUsername());
        dto.setPassword(customer.getPassword());
        dto.setAddress(customer.getAddress());
        dto.setMobileNumber(customer.getMobileNumber());
        dto.setEmail(customer.getEmail());
        return dto;
    }

    private TripBookingDTO convertToDTO(TripBooking tripBooking) {
        if (tripBooking == null) {
            return null;
        }
        TripBookingDTO dto = new TripBookingDTO();
        dto.setTripId(tripBooking.getTripId());
        dto.setCustomerId(tripBooking.getCustomer() != null ? tripBooking.getCustomer().getCustomerId() : null);
        dto.setDriverId(tripBooking.getDriver() != null ? tripBooking.getDriver().getDriverId() : null);
        dto.setCabId(tripBooking.getCab() != null ? tripBooking.getCab().getCabId() : null);
        dto.setPickupLocation(tripBooking.getPickupLocation());
        dto.setDropoffLocation(tripBooking.getDropoffLocation());
        dto.setStartDate(tripBooking.getStartDate());
        dto.setEndDate(tripBooking.getEndDate());
        dto.setStatus(tripBooking.getStatus());
        return dto;
    }

    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setAddress(customerDTO.getAddress());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        customer.setEmail(customerDTO.getEmail());
        return customer;
    }

    private TripBooking convertToEntity(TripBookingDTO tripBookingDTO) {
        TripBooking tripBooking = new TripBooking();
        tripBooking.setTripId(tripBookingDTO.getTripId());
        // Handle necessary entities
        tripBooking.setPickupLocation(tripBookingDTO.getPickupLocation());
        tripBooking.setDropoffLocation(tripBookingDTO.getDropoffLocation());
        tripBooking.setStartDate(tripBookingDTO.getStartDate());
        tripBooking.setEndDate(tripBookingDTO.getEndDate());
        tripBooking.setStatus(tripBookingDTO.getStatus());
        return tripBooking;
    }
}
