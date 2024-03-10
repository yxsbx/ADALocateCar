package com.adalocatecar.service;

import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.model.Rental;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {
    String rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate, String agencyLocal);
    String returnVehicle(String licensePlate, LocalDateTime actualEndDate);
    List<RentalDTO> findAllRentals();
    RentalDTO findRentalByClientId(String clientId);
    List<Rental> findRentalsByVehicleLicensePlate(String licensePlate);
}