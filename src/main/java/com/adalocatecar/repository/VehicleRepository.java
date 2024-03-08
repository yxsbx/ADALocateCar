package com.adalocatecar.repository;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.model.Vehicle;

import java.util.List;

public interface VehicleRepository extends GenericsRepository <Vehicle, String> {
    VehicleDTO findByLicensePlate(String licensePlate);
    List<VehicleDTO> findByType(String type);
    List<VehicleDTO> findByModel(String model);
    List<VehicleDTO> findByYear(int year);
}