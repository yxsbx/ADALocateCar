package com.adalocatecar.service;

import com.adalocatecar.dto.RentalDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {
    void rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate);
    void returnVehicle(String licensePlate, LocalDateTime actualEndDate);
    List<RentalDTO> findAllRentals();
    RentalDTO findRentalByClientId(String clientId);
}
