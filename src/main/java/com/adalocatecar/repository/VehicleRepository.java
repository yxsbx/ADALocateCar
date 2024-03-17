package com.adalocatecar.repository;

import com.adalocatecar.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends GenericsRepository<Vehicle, String> {
    Optional<Vehicle> searchByLicensePlate(String licensePlate);

    List<Vehicle> searchByType(String type);

    List<Vehicle> searchByModel(String model);
}