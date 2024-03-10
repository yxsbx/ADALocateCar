package com.adalocatecar.service;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Vehicle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface VehicleService {
    String createVehicle(VehicleDTO vehicleDTO) throws IOException;
    Object updateVehicle(VehicleDTO vehicleDTO) throws IOException;
    void deleteVehicle(String licensePlate);
    List<VehicleDTO> findAllVehicles() throws IOException;
    boolean isVehicleAvailable(String licensePlate, LocalDateTime startDate, LocalDateTime expectedEndDate);
    void markVehicleAsUnavailable(String licensePlate, LocalDateTime startDate, LocalDateTime expectedEndDate);
    void markVehicleAsAvailable(String licensePlate);
    Vehicle findVehicleByLicensePlate(String licensePlate);
    List<VehicleDTO> findVehiclesByType(String type);
    List<VehicleDTO> findVehiclesByModel(String model);

    LocalDateTime findRentalStartDateByLicensePlate(String licensePlate);
}
