package com.adalocatecar.repository.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.VehicleRepository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the VehicleRepository interface for managing vehicles in a file-based repository.
 */

public class VehicleRepositoryImpl extends GenericsRepositoryImpl<Vehicle, String> implements VehicleRepository {
    private static final File filePath = new File("src/data/vehicles.txt");

    /**
     * Constructs a new VehicleRepositoryImpl instance.
     */

    public VehicleRepositoryImpl() {
        super(filePath);
    }

    /**
     * Convert a string representation to a Vehicle object.
     *
     * @param str The string to convert.
     * @return The corresponding Vehicle object.
     * @throws IllegalArgumentException if the string format is invalid.
     */

    @Override
    protected Vehicle stringToObject(String str) {
        String[] parts = str.split(",");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid string format for Vehicle object");
        }
        String licensePlate = parts[0];
        String model = parts[1];
        String type = parts[2];
        boolean rented = Boolean.parseBoolean(parts[3]);
        String clientId = parts[4];
        String agencyLocal = parts[5];

        Vehicle vehicle = new Vehicle(licensePlate, model, type);
        vehicle.getRentalContract().setRentalStatus(rented);
        if (rented) {
            vehicle.getRentalContract().setIdClientWhoRented(clientId);
            vehicle.getRentalContract().setAgencyLocal(agencyLocal);
            vehicle.getRentalContract().setStartDate(
                    LocalDateTime.parse(parts[6])
            );
        }
        return vehicle;
    }

    /**
     * Convert a Vehicle object to its string representation.
     *
     * @param object The Vehicle object to convert.
     * @return The string representation of the Vehicle object.
     */

    @Override
    protected String objectToString(Vehicle object) {
        return String.join(",",
                object.getLicensePlate(),
                object.getModel(),
                object.getType(),
                String.valueOf(object.getRentalContract().isRentalStatus()),
                object.getRentalContract().getIdClientWhoRented(),
                object.getRentalContract().getAgencyLocal(),
                String.valueOf(object.getRentalContract().getStartDate())
        );
    }

    /**
     * Get the ID of a Vehicle entity.
     *
     * @param entity The Vehicle entity.
     * @return The ID of the Vehicle entity.
     */

    @Override
    protected String getId(Vehicle entity) {
        return entity.getLicensePlate();
    }

    /**
     * Get the name of a Vehicle entity.
     *
     * @param entity The Vehicle entity.
     * @return The name of the Vehicle entity.
     */

    @Override
    protected String getName(Vehicle entity) {
        return entity.getModel();
    }
}