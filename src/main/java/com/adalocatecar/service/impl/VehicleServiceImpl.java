package com.adalocatecar.service.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.Validation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    private final List<VehicleDTO> vehicles = new ArrayList<>();

    @Override
    public Validation registerVehicle(VehicleDTO vehicleDTO) {
        Validation validLicensePlate = Validation.validateLicensePlate(vehicleDTO.getLicensePlate());
        if(!validLicensePlate.isSuccess()){
            return validLicensePlate;
        }

        List<String> listOfLicensePlates = findAllLicensePlates();

        Validation uniqueLicensePlate = Validation.validateUniqueLicensePlate(vehicleDTO.getLicensePlate(), listOfLicensePlates);
        if(!uniqueLicensePlate.isSuccess()){
            return uniqueLicensePlate;
        }

        Validation validBrand = Validation.validateBrand(vehicleDTO.getBrand());
        if(!validBrand.isSuccess()){
            return validBrand;
        }

        Validation validType = Validation.validateType(vehicleDTO.getType());
        if(!validType.isSuccess()){
            return validType;
        }

        Validation validYear = Validation.validateVehicleYear(vehicleDTO.getYear());
        if(!validYear.isSuccess()){
            return validYear;
        }

        vehicles.add(vehicleDTO);
        System.out.println("Vehicle registered successfully.");
        return Validation.ok();
    }

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getLicensePlate().equals(vehicleDTO.getLicensePlate())) {
                vehicles.set(i, vehicleDTO);
                System.out.println("Vehicle updated successfully.");
                return;
            }
        }
        System.out.println("Vehicle not found for updating.");
    }

    @Override
    public void deleteVehicle(String licensePlate) {
        vehicles.removeIf(vehicle -> vehicle.getLicensePlate().equals(licensePlate));
        System.out.println("Vehicle deleted successfully.");
    }

    @Override
    public List<VehicleDTO> findAllVehicles() {
        return vehicles;
    }

    public List<String> findAllLicensePlates(){
        List<String> licensePlates = new ArrayList<>();
        for (VehicleDTO vehicle : vehicles) {
            licensePlates.add(vehicle.getLicensePlate());
        }
        return licensePlates;
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
        for (VehicleDTO vehicle : vehicles) {
            if (vehicle.getLicensePlate().equals(licensePlate)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public List<VehicleDTO> findVehiclesByType(String type) {
        List<VehicleDTO> matchingVehicles = new ArrayList<>();
        for (VehicleDTO vehicle : vehicles) {
            if (vehicle.getType().equalsIgnoreCase(type)) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    @Override
    public List<VehicleDTO> findVehiclesByModel(String model) {
        List<VehicleDTO> matchingVehicles = new ArrayList<>();
        for (VehicleDTO vehicle : vehicles) {
            if (vehicle.getModel().equalsIgnoreCase(model)) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    @Override
    public List<VehicleDTO> findVehiclesByYear(int year) {
        List<VehicleDTO> matchingVehicles = new ArrayList<>();
        for (VehicleDTO vehicle : vehicles) {
            if (vehicle.getYear() == year) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }
}
