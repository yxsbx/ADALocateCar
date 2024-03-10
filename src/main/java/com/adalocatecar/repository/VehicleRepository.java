package com.adalocatecar.repository;

import com.adalocatecar.model.Vehicle;

import java.util.List;

public interface VehicleRepository extends GenericsRepository<Vehicle, String> {
    Vehicle findByLicensePlate(String licensePlate);
    List<Vehicle> findByType(String type);
    List<Vehicle> findByModel(String model);
    List<Vehicle> findByYear(int year);
}