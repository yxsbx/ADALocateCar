package com.adalocatecar.service.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.VehicleRepository;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.ValidationInput;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the VehicleService interface that handles vehicle-related operations.
 */

public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    /**
     * Constructs a new VehicleServiceImpl with the specified VehicleRepository.
     *
     * @param vehicleRepository The vehicle repository to be used for vehicle-related operations.
     */

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Creates a new vehicle based on the provided vehicle DTO.
     *
     * @param vehicleDTO The vehicle DTO containing information about the vehicle to be created.
     */

    @Override
    public void createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = Converter.convertToEntity(vehicleDTO);
        saveVehicle(vehicle, "CREATE");
    }

    /**
     * Retrieves a list of all vehicles in the repository as vehicle DTOs.
     *
     * @return A list of vehicle DTOs representing all vehicles in the repository.
     */

    @Override
    public List<VehicleDTO> readAllVehicles() {
        return vehicleRepository.readAll().stream()
                .map(Converter::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing vehicle based on the provided vehicle DTO.
     *
     * @param vehicleDTO The vehicle DTO containing updated information about the vehicle.
     */

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO) {
        vehicleRepository.searchById(vehicleDTO.getLicensePlate())
                .orElseThrow(() -> new IllegalArgumentException(ValidationInput.VEHICLE_NOT_FOUND));

        saveVehicle(Converter.convertToEntity(vehicleDTO), "UPDATE");
    }

    /**
     * Deletes a vehicle with the specified license plate.
     *
     * @param licensePlate The license plate of the vehicle to be deleted.
     */

    @Override
    public void deleteVehicle(String licensePlate) {
        VehicleDTO vehicleToDelete = searchVehicleById(licensePlate);
        if (!vehicleToDelete.isAvailable()) {
            throw new IllegalArgumentException(ValidationInput.ERROR_IS_RENTED);
        }
        vehicleRepository.delete(Converter.convertToEntity(vehicleToDelete));
        System.out.println(ValidationInput.VEHICLE_DELETED);
    }

    /**
     * Searches for a vehicle by its license plate and returns its DTO representation.
     *
     * @param licensePlate The license plate of the vehicle to search for.
     * @return The vehicle DTO representing the found vehicle.
     * @throws RuntimeException if the vehicle with the specified license plate is not found.
     */

    @Override
    public VehicleDTO searchVehicleById(String licensePlate) {
        return vehicleRepository.searchById(licensePlate.toUpperCase())
                .map(Converter::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException(ValidationInput.VEHICLE_NOT_FOUND));
    }

    /**
     * Searches for vehicles by their model and returns a list of their DTO representations.
     *
     * @param model The model of the vehicles to search for.
     * @return A list of vehicle DTOs representing the found vehicles.
     */

    @Override
    public List<VehicleDTO> searchVehiclesByName(String model) {
        return vehicleRepository.searchByName(model).map(vehicles -> vehicles.stream()
                .map(Converter::convertToDTO)
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    /**
     * Reads all license plates of vehicles in the repository.
     *
     * @return A list of all license plates of vehicles in the repository.
     */

    private List<String> readAllLicensePlates() {
        return vehicleRepository.readAll().stream()
                .map(Vehicle::getLicensePlate)
                .collect(Collectors.toList());
    }

    /**
     * Saves a vehicle to the repository based on the operation type (create or update).
     *
     * @param vehicle       The vehicle to be saved.
     * @param operationType The type of operation (create or update) to be performed.
     */

    private void saveVehicle(Vehicle vehicle, String operationType) {
        if ("CREATE".equals(operationType)) {
            List<String> existingLicensePlates = readAllLicensePlates();
            if (!ValidationInput.isUniqueLicensePlate(vehicle.getLicensePlate(), existingLicensePlates)) {
                throw new IllegalArgumentException(ValidationInput.DUPLICATED_LICENSE_PLATE);
            }

            if (!ValidationInput.isValidLicensePlate(vehicle.getLicensePlate())) {
                throw new IllegalArgumentException(ValidationInput.INVALID_LICENSE_PLATE_FORMAT);
            }
        }


        if (!ValidationInput.isValidModel(vehicle.getModel())) {
            throw new IllegalArgumentException(ValidationInput.INVALID_MODEL_FORMAT);
        }

        if (!ValidationInput.isValidType(vehicle.getType())) {
            throw new IllegalArgumentException(ValidationInput.INVALID_TYPE);
        }

        if (operationType.equals("CREATE")) {
            vehicleRepository.create(vehicle);
            System.out.println(ValidationInput.VEHICLE_CREATED);
        } else {
            vehicleRepository.update(vehicle);
            System.out.println(ValidationInput.VEHICLE_UPDATED);
        }
    }
}