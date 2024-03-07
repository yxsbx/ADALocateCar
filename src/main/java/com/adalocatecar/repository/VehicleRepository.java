package com.adalocatecar.repository;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Client;

import java.util.List;

public interface VehicleRepository {
    void create(Client client);
    void update(Client client);
    boolean delete(String id);
    List<VehicleDTO> findAll();
    VehicleDTO findByLicensePlate(String licensePlate);
    List<VehicleDTO> findByType(String type);
    List<VehicleDTO> findByModel(String model);
    List<VehicleDTO> findByYear(int year);
}