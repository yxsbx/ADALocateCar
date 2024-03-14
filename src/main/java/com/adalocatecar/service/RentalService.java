package com.adalocatecar.service;

import com.adalocatecar.dto.RentalDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {
    String rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate, String agencyLocal);
    String returnVehicle(String licensePlate, LocalDateTime actualEndDate);
}