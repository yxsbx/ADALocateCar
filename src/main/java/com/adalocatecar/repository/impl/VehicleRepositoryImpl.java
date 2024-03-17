package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.VehicleRepository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class VehicleRepositoryImpl extends GenericsRepositoryImpl<Vehicle, String> implements VehicleRepository {

    private static final File filePath = new File("src/data/vehicles.txt");

    public VehicleRepositoryImpl() {
        super(filePath);
    }

    @Override
    public Optional<Vehicle> searchByLicensePlate(String licensePlate) {
        List<Vehicle> vehicles = readAll();
        return vehicles.stream()
                .filter(vehicle -> vehicle.getLicensePlate().equalsIgnoreCase(licensePlate)).findFirst();
    }

    @Override
    public List<Vehicle> searchByType(String type) {
        List<Vehicle> vehicles = readAll();
        return vehicles.stream().filter(vehicle -> vehicle.getType().equalsIgnoreCase(type)).collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> searchByModel(String model) {
        List<Vehicle> vehicles = readAll();
        return vehicles.stream().filter(vehicle -> vehicle.getModel().toLowerCase().contains(model.toLowerCase())).collect(Collectors.toList());
    }

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

    @Override
    protected String getId(Vehicle entity) {
        return entity.getLicensePlate();
    }

    @Override
    protected String getName(Vehicle entity) {
        return entity.getModel();
    }
}