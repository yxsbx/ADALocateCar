package com.adalocatecar.repository;

import com.adalocatecar.dto.VehicleDTO;
import java.util.List;

public interface VehicleRepository {
    void save(VehicleDTO vehicle);
    void update(VehicleDTO vehicle);
    void delete(String licensePlate);
    List<VehicleDTO> findAll();
    VehicleDTO findByLicensePlate(String licensePlate);
    List<VehicleDTO> findByType(String type);
    List<VehicleDTO> findByModel(String model);
    List<VehicleDTO> findByYear(int year);
}