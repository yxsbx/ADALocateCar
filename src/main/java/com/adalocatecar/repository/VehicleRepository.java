package com.adalocatecar.repository;

import com.adalocatecar.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends GenericsRepository<Vehicle, String> {
    Optional<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> findByType(String type);

    List<Vehicle> findByModel(String model);
}