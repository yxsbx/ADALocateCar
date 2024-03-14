package com.adalocatecar.service.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.VehicleRepository;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.ValidationVehicle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;


    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public String createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = Converter.convertToEntity(vehicleDTO);
        return saveVehicle(vehicle, ValidationVehicle.OperationType.CREATE);
    }

    @Override
    public String updateVehicle(VehicleDTO vehicleDTO) {
        Optional<Vehicle> existingVehicle = vehicleRepository.findByLicensePlate(vehicleDTO.getLicensePlate());
        if (existingVehicle.isEmpty()) {
            throw new RuntimeException( "Vehicle not found.");
        }

        Vehicle updatedVehicle = Converter.convertToEntity(vehicleDTO);

        return saveVehicle(updatedVehicle, ValidationVehicle.OperationType.UPDATE);
    }

    @Override
    public void deleteVehicle(String licensePlate) {
        vehicleRepository.delete(licensePlate);
    }

    private String saveVehicle(Vehicle vehicle, ValidationVehicle.OperationType operationType) {
        String validationMessage = validateVehicle(vehicle, operationType);
        if (!validationMessage.isEmpty()) {
            return validationMessage;
        }

        try {
            if (operationType == ValidationVehicle.OperationType.CREATE) {
                vehicleRepository.create(vehicle);
            } else if (operationType == ValidationVehicle.OperationType.UPDATE) {
                vehicleRepository.update(vehicle);
            }
            return ValidationVehicle.SUCCESS_MESSAGE;
        } catch (Exception e) {
            return "An error occurred while saving the vehicle.";
        }
    }

    private String validateVehicle(Vehicle vehicle, ValidationVehicle.OperationType operationType) {
        String typeValidation = ValidationVehicle.validateType(vehicle.getType());
        if (!typeValidation.isEmpty()) {
            return typeValidation;
        }

        if (operationType == ValidationVehicle.OperationType.CREATE) {
            String licensePlateValidation = ValidationVehicle.validateLicensePlate(vehicle.getLicensePlate());
            if (!licensePlateValidation.isEmpty()) {
                return licensePlateValidation;
            }

            List<String> existingLicensePlates = findAllLicensePlates();
            String uniqueLicensePlateValidation = ValidationVehicle.validateUniqueLicensePlate(vehicle.getLicensePlate(), existingLicensePlates);
            if (!uniqueLicensePlateValidation.isEmpty()) {
                return uniqueLicensePlateValidation;
            }
        }

        return "";
    }

    private List<String> findAllLicensePlates()  {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<String> licensePlates = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            licensePlates.add(vehicle.getLicensePlate());
        }
        return licensePlates;
    }

    @Override
    public List<VehicleDTO> findAllVehicles() {
        return Converter.convertToDTOList(vehicleRepository.findAll());
    }

    @Override
    public boolean isVehicleAvailable(String licensePlate) {
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (vehicle.isEmpty()) {
            throw new RuntimeException("Vehicle not found.");
        }
        return vehicle.get().isAvailable();
    }

    @Override
    public VehicleDTO findVehicleByLicensePlate(String licensePlate) {
    Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (vehicle.isEmpty()) {
            throw new RuntimeException("Vehicle not found.");
        }
        return Converter.convertToDTO(vehicle.get());
    }

    @Override
    public List<VehicleDTO> findVehiclesByType(String type) {
        List<Vehicle> vehicles = vehicleRepository.findByType(type);
        return vehicles.stream().map(Converter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<VehicleDTO> findVehiclesByModel(String model) {
        List<Vehicle> vehicles = vehicleRepository.findByModel(model);
        return vehicles.stream()
                .map(Converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocalDateTime findRentalStartDateByLicensePlate(String licensePlate) {
        return null;
    }
}