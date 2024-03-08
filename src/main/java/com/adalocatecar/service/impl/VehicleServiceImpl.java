package com.adalocatecar.service.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.ValidationResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    private final List<VehicleDTO> vehicles = new ArrayList<>();

    @Override
    public ValidationResponse registerVehicle(VehicleDTO vehicleDTO) {
        System.out.println("Vehicle registered successfully.Não implementou nada só tá enrolando :D");
        return null;
    }

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO) {
    }

    @Override
    public void deleteVehicle(String licensePlate) {
    }

    @Override
    public List<VehicleDTO> findAllVehicles() {
        return null;
    }

    @Override
    public boolean isVehicleAvailable(String licensePlate, LocalDateTime startDate, LocalDateTime expectedEndDate) {
        return false;
    }

    @Override
    public void markVehicleAsUnavailable(String licensePlate) {
    }

    @Override
    public void markVehicleAsAvailable(String licensePlate) {
        System.out.println("Vehicle with license plate " + licensePlate + " marked as available.");
    }

    @Override
    public VehicleDTO findVehicleByLicensePlate(String licensePlate) {
        return null;
    }

    @Override
    public List<VehicleDTO> findVehiclesByType(String type) {
        return null;
    }

    @Override
    public List<VehicleDTO> findVehiclesByModel(String model) {
        return null;
    }

    @Override
    public List<VehicleDTO> findVehiclesByYear(int year) {
        return null;
    }
}