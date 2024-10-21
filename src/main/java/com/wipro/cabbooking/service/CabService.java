package com.wipro.cabbooking.service;

import com.wipro.cabbooking.dto.CabDTO;
import com.wipro.cabbooking.exception.CabException;

import java.util.List;

public interface CabService {

    CabDTO saveCab(CabDTO cabDTO) throws CabException;

    CabDTO updateCab(CabDTO cabDTO) throws CabException;

    void deleteCab(int cabId) throws CabException;

    CabDTO getCabById(int cabId) throws CabException;

    List<CabDTO> getAllCabs();
}
