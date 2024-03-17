package com.adalocatecar.service;

import java.time.LocalDateTime;

public interface RentalService {
    String rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, String agencyLocal);

    String returnVehicle(String licensePlate, LocalDateTime actualEndDate);
}