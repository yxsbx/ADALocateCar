package com.adalocatecar.service;

import com.adalocatecar.dto.VehicleDTO;

import java.io.IOException;
import java.util.List;

public interface VehicleService {
    void createVehicle(VehicleDTO vehicleDTO) throws IOException;

    void updateVehicle(VehicleDTO vehicleDTO) throws IOException;

    void deleteVehicle(String licensePlate);

    List<VehicleDTO> readAllVehicles() throws IOException;

    VehicleDTO searchVehicleByLicensePlate(String licensePlate);

    List<VehicleDTO> searchVehiclesByModel(String model);
}
