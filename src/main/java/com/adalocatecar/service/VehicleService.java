package com.adalocatecar.service;

import com.adalocatecar.dto.VehicleDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleService {
    Object registerVehicle(VehicleDTO vehicleDTO);
    Object updateVehicle(VehicleDTO vehicleDTO);
    void deleteVehicle(String licensePlate);
    List<VehicleDTO> findAllVehicles();
    boolean isVehicleAvailable(String licensePlate, LocalDateTime startDate, LocalDateTime expectedEndDate);
    void markVehicleAsUnavailable(String licensePlate);
    void markVehicleAsAvailable(String licensePlate);
    VehicleDTO findVehicleByLicensePlate(String licensePlate);
    List<VehicleDTO> findVehiclesByType(String type);
    List<VehicleDTO> findVehiclesByModel(String model);
    List<VehicleDTO> findVehiclesByYear(int year);
}
