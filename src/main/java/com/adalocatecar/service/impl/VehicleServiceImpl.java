package com.adalocatecar.service.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Rental;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.VehicleRepository;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.Validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    private final List<VehicleDTO> vehicles = new ArrayList<>();
    private VehicleRepository vehicleRepository;
    private final Rental[] rentals;

    public VehicleServiceImpl(Rental[] rentals) {
        this.rentals = rentals;
    }

    @Override
    public Object registerVehicle(VehicleDTO vehicleDTO) {
        Validation validLicensePlate = Validation.validateFormat(vehicleDTO.getLicensePlate(), "[A-Z]{3}-\\d{4}", "License Plate");
        if (!validLicensePlate.isSuccess()) {
            return validLicensePlate.isSuccess();
        }

        if (!isUniqueLicensePlate(vehicleDTO.getLicensePlate())) {
            return Validation.error("License plate already exists.").isSuccess();
        }

        Validation validBrand = Validation.validateRequiredField(vehicleDTO.getBrand(), "Brand");
        if (!validBrand.isSuccess()) {
            return validBrand.isSuccess();
        }

        Validation validType = Validation.validateRequiredField(vehicleDTO.getType(), "Type");
        if (!validType.isSuccess()) {
            return validType.isSuccess();
        }

        boolean validYear = Validation.validateVehicleYear(vehicleDTO.getYear());
        if (!validYear) {
            return validYear;
        }

        vehicles.add(vehicleDTO);
        System.out.println("Vehicle registered successfully.");
        return Validation.ok("Vehicle rented successfully.").isSuccess();
    }


    @Override
    public Object updateVehicle(VehicleDTO vehicleDTO) {
        VehicleDTO existingVehicle = findVehicleByLicensePlate(vehicleDTO.getLicensePlate());
        if (existingVehicle != null) {
            Validation validBrand = Validation.validateRequiredField(vehicleDTO.getBrand(), "Brand");
            if (!validBrand.isSuccess()) {
                return validBrand;
            }

            Validation validType = Validation.validateRequiredField(vehicleDTO.getType(), "Type");
            if (!validType.isSuccess()) {
                return validType;
            }

            boolean validYear = Validation.validateVehicleYear(vehicleDTO.getYear());
            if (!validYear) {
                return validYear;
            }

            existingVehicle.setBrand(vehicleDTO.getBrand());
            existingVehicle.setType(vehicleDTO.getType());
            existingVehicle.setYear(vehicleDTO.getYear());
            System.out.println("Vehicle updated successfully.");
            return Validation.ok("Vehicle rented successfully.");
        } else {
            System.out.println("Vehicle not found for updating.");
            return Validation.error("Vehicle not found for updating.");
        }
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

    @Override
    public void markVehicleAsUnavailable(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
        if (vehicle != null) {
            vehicle.setAvailable(false);
            vehicleRepository.update(vehicle);
            System.out.println("Vehicle with license plate " + licensePlate + " marked as unavailable.");
        } else {
            System.out.println("Vehicle not found with license plate " + licensePlate);
        }
    }

    @Override
    public void markVehicleAsAvailable(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
        if (vehicle != null) {
            vehicle.setAvailable(true);
            vehicleRepository.update(vehicle);
            System.out.println("Vehicle with license plate " + licensePlate + " marked as available.");
        } else {
            System.out.println("Vehicle not found with license plate " + licensePlate);
        }
    }

    @Override
    public boolean isVehicleAvailable(String licensePlate, LocalDateTime startDate, LocalDateTime expectedEndDate) {
        for (Rental rental : rentals) {
            if (rental.getLicensePlate().equals(licensePlate)) {
                LocalDateTime rentalStartDate = rental.getStartDate();
                LocalDateTime rentalEndDate = rental.getExpectedEndDate();
                if (startDate.isBefore(rentalEndDate) && expectedEndDate.isAfter(rentalStartDate)) {
                    return false;
                }
            }
        }
        return true;
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
            if (vehicle.getBrand().equalsIgnoreCase(model) || vehicle.getType().equalsIgnoreCase(model)) {
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
