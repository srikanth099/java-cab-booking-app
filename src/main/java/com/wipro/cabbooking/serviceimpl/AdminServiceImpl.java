package com.wipro.cabbooking.serviceimpl;

import com.wipro.cabbooking.dto.AdminDTO;
import com.wipro.cabbooking.dto.CustomerDTO;
import com.wipro.cabbooking.dto.DriverDTO;
import com.wipro.cabbooking.dto.TripBookingDTO;
import com.wipro.cabbooking.dto.CabDTO;
import com.wipro.cabbooking.entity.Admin;
import com.wipro.cabbooking.entity.Customer;
import com.wipro.cabbooking.entity.Driver;
import com.wipro.cabbooking.entity.TripBooking;
import com.wipro.cabbooking.entity.TripStatus;
import com.wipro.cabbooking.entity.Cab;
import com.wipro.cabbooking.exception.AdminException;
import com.wipro.cabbooking.exception.CustomerException;
import com.wipro.cabbooking.exception.DriverException;
import com.wipro.cabbooking.exception.TripBookingException;
import com.wipro.cabbooking.exception.CabException;
import com.wipro.cabbooking.repository.AdminRepository;
import com.wipro.cabbooking.repository.CustomerRepository;
import com.wipro.cabbooking.repository.DriverRepository;
import com.wipro.cabbooking.repository.TripBookingRepository;
import com.wipro.cabbooking.repository.CabRepository;
import com.wipro.cabbooking.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CabRepository cabRepository;
    
    @Autowired
    private TripBookingRepository tripBookingRepository;

    @Override
    public AdminDTO saveAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(adminDTO.getPassword());
        admin.setAddress(adminDTO.getAddress());
        admin.setMobileNumber(adminDTO.getMobileNumber());
        admin.setEmail(adminDTO.getEmail());
        return convertToDTO(adminRepository.save(admin));
    }

    @Override
    public AdminDTO updateAdmin(AdminDTO adminDTO) throws AdminException {
        Admin admin = adminRepository.findById(adminDTO.getAdminId())
                .orElseThrow(() -> new AdminException("Admin not found"));
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(adminDTO.getPassword());
        admin.setAddress(adminDTO.getAddress());
        admin.setMobileNumber(adminDTO.getMobileNumber());
        admin.setEmail(adminDTO.getEmail());
        return convertToDTO(adminRepository.save(admin));
    }

    @Override
    public void deleteAdmin(int adminId) throws AdminException {
        if (!adminRepository.existsById(adminId)) {
            throw new AdminException("Admin not found");
        }
        adminRepository.deleteById(adminId);
    }

    @Override
    public AdminDTO getAdminById(int adminId) throws AdminException {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminException("Admin not found"));
        return convertToDTO(admin);
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) throws CustomerException {
        Customer customer = new Customer();
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setAddress(customerDTO.getAddress());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        customer.setEmail(customerDTO.getEmail());
        return convertToDTO(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerException {
        Customer customer = customerRepository.findById(customerDTO.getCustomerId())
                .orElseThrow(() -> new CustomerException("Customer not found"));
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setAddress(customerDTO.getAddress());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        customer.setEmail(customerDTO.getEmail());
        return convertToDTO(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(int customerId) throws CustomerException {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerException("Customer not found");
        }
        customerRepository.deleteById(customerId);
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
    public List<CustomerDTO> searchCustomersByUsername(String username) {
        return customerRepository.findByUsernameContaining(username).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DriverDTO addDriver(DriverDTO driverDTO) throws DriverException {
        Driver driver = new Driver();
        driver.setUsername(driverDTO.getUsername());
        driver.setPassword(driverDTO.getPassword());
        driver.setAddress(driverDTO.getAddress());
        driver.setMobileNumber(driverDTO.getMobileNumber());
        driver.setEmail(driverDTO.getEmail());
        driver.setLicenseNo(driverDTO.getLicenseNo());
        driver.setRating(driverDTO.getRating());
        // Handle Cab association if needed
        return convertToDTO(driverRepository.save(driver));
    }

    @Override
    public DriverDTO updateDriver(DriverDTO driverDTO) throws DriverException {
        Driver driver = driverRepository.findById(driverDTO.getDriverId())
                .orElseThrow(() -> new DriverException("Driver not found"));
        driver.setUsername(driverDTO.getUsername());
        driver.setPassword(driverDTO.getPassword());
        driver.setAddress(driverDTO.getAddress());
        driver.setMobileNumber(driverDTO.getMobileNumber());
        driver.setEmail(driverDTO.getEmail());
        driver.setLicenseNo(driverDTO.getLicenseNo());
        driver.setRating(driverDTO.getRating());
        // Handle Cab association if needed
        return convertToDTO(driverRepository.save(driver));
    }

    @Override
    public void deleteDriver(int driverId) throws DriverException {
        if (!driverRepository.existsById(driverId)) {
            throw new DriverException("Driver not found");
        }
        driverRepository.deleteById(driverId);
    }

    @Override
    public DriverDTO getDriverById(int driverId) throws DriverException {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverException("Driver not found"));
        return convertToDTO(driver);
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CabDTO addCab(CabDTO cabDTO) throws CabException {
        Cab cab = new Cab();
        cab.setCarType(cabDTO.getCarType());
        cab.setPerKmRate(cabDTO.getPerKmRate());
        // Handle Driver association if needed
        return convertToDTO(cabRepository.save(cab));
    }

    @Override
    public CabDTO updateCab(CabDTO cabDTO) throws CabException {
        Cab cab = cabRepository.findById(cabDTO.getCabId())
                .orElseThrow(() -> new CabException("Cab not found"));
        cab.setCarType(cabDTO.getCarType());
        cab.setPerKmRate(cabDTO.getPerKmRate());
        // Handle Driver association if needed
        return convertToDTO(cabRepository.save(cab));
    }

    @Override
    public void deleteCab(int cabId) throws CabException {
        if (!cabRepository.existsById(cabId)) {
            throw new CabException("Cab not found");
        }
        cabRepository.deleteById(cabId);
    }

    @Override
    public CabDTO getCabById(int cabId) throws CabException {
        Cab cab = cabRepository.findById(cabId)
                .orElseThrow(() -> new CabException("Cab not found"));
        return convertToDTO(cab);
    }

    @Override
    public List<CabDTO> getAllCabs() {
        return cabRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    

    // Helper methods for converting entities to DTOs and vice versa
    private AdminDTO convertToDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setAdminId(admin.getAdminId());
        dto.setUsername(admin.getUsername());
        dto.setPassword(admin.getPassword());
        dto.setAddress(admin.getAddress());
        dto.setMobileNumber(admin.getMobileNumber());
        dto.setEmail(admin.getEmail());
        return dto;
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setUsername(customer.getUsername());
        dto.setPassword(customer.getPassword());
        dto.setAddress(customer.getAddress());
        dto.setMobileNumber(customer.getMobileNumber());
        dto.setEmail(customer.getEmail());
        return dto;
    }

    private DriverDTO convertToDTO(Driver driver) {
        DriverDTO dto = new DriverDTO();
        dto.setDriverId(driver.getDriverId());
        dto.setUsername(driver.getUsername());
        dto.setPassword(driver.getPassword());
        dto.setAddress(driver.getAddress());
        dto.setMobileNumber(driver.getMobileNumber());
        dto.setEmail(driver.getEmail());
        dto.setLicenseNo(driver.getLicenseNo());
        dto.setRating(driver.getRating());

        // Convert Cab association if needed
        if (driver.getCab() != null) {
            CabDTO cabDTO = new CabDTO();
            cabDTO.setCabId(driver.getCab().getCabId());
            cabDTO.setCarType(driver.getCab().getCarType());
            cabDTO.setPerKmRate(driver.getCab().getPerKmRate());

            dto.setCab(cabDTO); // Set CabDTO instead of cabId
        }

        return dto;
    }

    private CabDTO convertToDTO(Cab cab) {
        CabDTO dto = new CabDTO();
        dto.setCabId(cab.getCabId());
        dto.setCarType(cab.getCarType());
        dto.setPerKmRate(cab.getPerKmRate());

        // Convert Driver association if needed
        if (cab.getDriver() != null) {
            DriverDTO driverDTO = new DriverDTO();
            driverDTO.setDriverId(cab.getDriver().getDriverId());
            driverDTO.setUsername(cab.getDriver().getUsername());
            driverDTO.setPassword(cab.getDriver().getPassword());
            driverDTO.setAddress(cab.getDriver().getAddress());
            driverDTO.setMobileNumber(cab.getDriver().getMobileNumber());
            driverDTO.setEmail(cab.getDriver().getEmail());
            driverDTO.setLicenseNo(cab.getDriver().getLicenseNo());
            driverDTO.setRating(cab.getDriver().getRating());

            dto.setDriver(driverDTO); // Set DriverDTO instead of driverId
        }

        return dto;
    }
    private TripBookingDTO convertToDTO(TripBooking tripBooking) {
        TripBookingDTO dto = new TripBookingDTO();
        dto.setTripId(tripBooking.getTripId());
        dto.setCustomerId(tripBooking.getCustomer().getCustomerId()); // Assuming getCustomer() returns Customer object
        dto.setDriverId(tripBooking.getDriver().getDriverId()); // Assuming getDriver() returns Driver object
        dto.setCabId(tripBooking.getCab().getCabId()); // Assuming getCab() returns Cab object
        dto.setPickupLocation(tripBooking.getPickupLocation());
        dto.setDropoffLocation(tripBooking.getDropoffLocation());
        dto.setStartDate(tripBooking.getStartDate());
        dto.setEndDate(tripBooking.getEndDate());
        dto.setStatus(tripBooking.getStatus()); // Assuming TripStatus is an enum
        dto.setDistanceInKm(tripBooking.getDistanceInKm());
        dto.setBill(tripBooking.getBill());
        return dto;
    }


    @Override
    public List<DriverDTO> viewBestDrivers() {
        List<Driver> drivers = driverRepository.findTop10ByOrderByRatingDesc(); // Retrieve top 10 drivers by rating
        return drivers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TripBookingDTO bookCab(int customerId, TripBookingDTO tripBookingDTO) throws TripBookingException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new TripBookingException("Customer not found"));
        Driver driver = driverRepository.findById(tripBookingDTO.getDriverId())
                .orElseThrow(() -> new TripBookingException("Driver not found"));
        Cab cab = cabRepository.findById(tripBookingDTO.getCabId())
                .orElseThrow(() -> new TripBookingException("Cab not found"));

        TripBooking tripBooking = new TripBooking();
        tripBooking.setCustomer(customer);
        tripBooking.setDriver(driver);
        tripBooking.setCab(cab);
        tripBooking.setPickupLocation(tripBookingDTO.getPickupLocation());
        tripBooking.setDropoffLocation(tripBookingDTO.getDropoffLocation());
        tripBooking.setStartDate(tripBookingDTO.getStartDate());
        tripBooking.setEndDate(tripBookingDTO.getEndDate());
        tripBooking.setStatus(tripBookingDTO.getStatus());
        tripBooking.setDistanceInKm(tripBookingDTO.getDistanceInKm());
        tripBooking.setBill(tripBookingDTO.getBill());

        return convertToDTO(tripBookingRepository.save(tripBooking));
    }


    @Override
    public void cancelTripBooking(int tripId) throws TripBookingException {
        TripBooking tripBooking = tripBookingRepository.findById(tripId)
                .orElseThrow(() -> new TripBookingException("TripBooking not found"));
        tripBooking.setStatus(TripStatus.CANCELLED);
        tripBookingRepository.save(tripBooking);
    }


    @Override
    public TripBookingDTO getTripBookingById(int tripId) throws TripBookingException {
        TripBooking tripBooking = tripBookingRepository.findById(tripId)
                .orElseThrow(() -> new TripBookingException("TripBooking not found"));
        return convertToDTO(tripBooking);
    }


    @Override
    public List<TripBookingDTO> getAllTripBookings() {
        return tripBookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<TripBookingDTO> viewBookingHistory(int customerId) throws TripBookingException {
        if (!customerRepository.existsById(customerId)) {
            throw new TripBookingException("Customer not found");
        }
        List<TripBooking> tripBookings = tripBookingRepository.findByCustomerId(customerId);
        return tripBookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<TripBookingDTO> viewDriverBookingHistory(int driverId) throws TripBookingException {
        if (!driverRepository.existsById(driverId)) {
            throw new TripBookingException("Driver not found");
        }
        List<TripBooking> tripBookings = tripBookingRepository.findByDriverId(driverId);
        return tripBookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void cancelAnyBooking(int tripId) throws TripBookingException {
        TripBooking tripBooking = tripBookingRepository.findById(tripId)
                .orElseThrow(() -> new TripBookingException("TripBooking not found"));
        tripBooking.setStatus(TripStatus.CANCELLED);
        tripBookingRepository.save(tripBooking);
    }


    @Override
    public List<TripBookingDTO> viewAllBookings() throws TripBookingException {
        return tripBookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}

