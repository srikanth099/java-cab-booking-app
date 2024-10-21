package com.wipro.cabbooking.serviceimpl;

import com.wipro.cabbooking.dto.CabDTO;
import com.wipro.cabbooking.dto.DriverDTO;
import com.wipro.cabbooking.entity.Cab;
import com.wipro.cabbooking.entity.Driver;
import com.wipro.cabbooking.exception.CabException;
import com.wipro.cabbooking.repository.CabRepository;
import com.wipro.cabbooking.repository.DriverRepository;
import com.wipro.cabbooking.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CabServiceImpl implements CabService {

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public CabDTO saveCab(CabDTO cabDTO) throws CabException {
        Cab cab = new Cab();
        cab.setCarType(cabDTO.getCarType());
        cab.setPerKmRate(cabDTO.getPerKmRate());

        if (cabDTO.getDriver() != null && cabDTO.getDriver().getDriverId() > 0) {
            Driver driver = driverRepository.findById(cabDTO.getDriver().getDriverId())
                    .orElseThrow(() -> new CabException("Driver not found with ID: " + cabDTO.getDriver().getDriverId()));
            
            cab.setDriver(driver);
            driver.setCab(cab);
            driverRepository.save(driver);  // Save the updated driver
        }

        Cab savedCab = cabRepository.save(cab);
        return convertToDTO(savedCab);
    }

    @Override
    public CabDTO updateCab(CabDTO cabDTO) throws CabException {
        Cab cab = cabRepository.findById(cabDTO.getCabId())
                .orElseThrow(() -> new CabException("Cab not found with ID: " + cabDTO.getCabId()));

        cab.setCarType(cabDTO.getCarType());
        cab.setPerKmRate(cabDTO.getPerKmRate());

        if (cabDTO.getDriver() != null && cabDTO.getDriver().getDriverId() > 0) {
            Driver driver = driverRepository.findById(cabDTO.getDriver().getDriverId())
                    .orElseThrow(() -> new CabException("Driver not found with ID: " + cabDTO.getDriver().getDriverId()));

            // Unlink the current driver if it exists
            if (cab.getDriver() != null) {
                Driver currentDriver = cab.getDriver();
                currentDriver.setCab(null);
                driverRepository.save(currentDriver);
            }

            cab.setDriver(driver);
            driver.setCab(cab);
            driverRepository.save(driver);
        } else {
            if (cab.getDriver() != null) {
                Driver currentDriver = cab.getDriver();
                currentDriver.setCab(null);
                cab.setDriver(null);
                driverRepository.save(currentDriver);
            }
        }

        Cab updatedCab = cabRepository.save(cab);
        return convertToDTO(updatedCab);
    }

    @Override
    public void deleteCab(int cabId) throws CabException {
        Cab cab = cabRepository.findById(cabId)
                .orElseThrow(() -> new CabException("Cab not found with ID: " + cabId));

        if (cab.getDriver() != null) {
            Driver driver = cab.getDriver();
            driver.setCab(null);
            cab.setDriver(null);
            driverRepository.save(driver);
        }

        cabRepository.deleteById(cabId);
    }

    @Override
    public CabDTO getCabById(int cabId) throws CabException {
        Cab cab = cabRepository.findById(cabId)
                .orElseThrow(() -> new CabException("Cab not found with ID: " + cabId));
        return convertToDTO(cab);
    }

    @Override
    public List<CabDTO> getAllCabs() {
        return cabRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CabDTO convertToDTO(Cab cab) {
        CabDTO dto = new CabDTO();
        dto.setCabId(cab.getCabId());
        dto.setCarType(cab.getCarType());
        dto.setPerKmRate(cab.getPerKmRate());

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

}
