package com.adalocatecar.service;

import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.utility.Validation;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {
    Validation rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate);
    Validation returnVehicle(String licensePlate, LocalDateTime actualEndDate);
    List<RentalDTO> findAllRentals();
    RentalDTO findRentalByClientId(String clientId);
}