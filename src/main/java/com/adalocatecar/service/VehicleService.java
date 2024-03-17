package com.adalocatecar.service;

import com.adalocatecar.dto.VehicleDTO;

import java.io.IOException;
import java.util.List;

public interface VehicleService {

    /**
     * Creates a new vehicle based on the provided vehicle DTO.
     *
     * @param vehicleDTO The vehicle DTO containing information about the vehicle to be created.
     * @throws IOException if an I/O error occurs while creating the vehicle.
     */

    void createVehicle(VehicleDTO vehicleDTO) throws IOException;

    /**
     * Retrieves a list of all vehicles as vehicle DTOs.
     *
     * @return A list of vehicle DTOs representing all vehicles.
     * @throws IOException if an I/O error occurs while reading vehicles.
     */

    List<VehicleDTO> readAllVehicles() throws IOException;

    /**
     * Updates an existing vehicle based on the provided vehicle DTO.
     *
     * @param vehicleDTO The vehicle DTO containing updated information about the vehicle.
     * @throws IOException if an I/O error occurs while updating the vehicle.
     */

    void updateVehicle(VehicleDTO vehicleDTO) throws IOException;

    /**
     * Deletes a vehicle based on its license plate.
     *
     * @param licensePlate The license plate of the vehicle to be deleted.
     */

    void deleteVehicle(String licensePlate);

    /**
     * Searches for a vehicle by its license plate and returns its DTO representation.
     *
     * @param licensePlate The license plate of the vehicle to search for.
     * @return The vehicle DTO representing the found vehicle.
     */

    VehicleDTO searchVehicleById(String licensePlate);

    /**
     * Searches for vehicles by their model and returns a list of their DTO representations.
     *
     * @param model The model of the vehicles to search for.
     * @return A list of vehicle DTOs representing the found vehicles.
     */

    List<VehicleDTO> searchVehiclesByName(String model);
}