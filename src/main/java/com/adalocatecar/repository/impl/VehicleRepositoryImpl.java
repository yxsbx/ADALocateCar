package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.GenericsRepository;
import com.adalocatecar.repository.VehicleRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class VehicleRepositoryImpl extends GenericsRepositoryImpl<Vehicle, String> implements VehicleRepository {

    private static final File filePath = new File("src/data/vehicles.txt");
    private static final Logger logger = Logger.getLogger(VehicleRepositoryImpl.class.getName());
    private final GenericsRepository clientRepository = new ClientRepositoryImpl();

    public VehicleRepositoryImpl() {
        super(filePath);
        loadEntities();
    }

    private void loadEntities() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Vehicle vehicle = stringToObject(line);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading entities from file", e);
        }
    }

    @Override
    public Optional<Vehicle> findByLicensePlate(String licensePlate) {
        List<Vehicle> vehicles = findAll();
        return vehicles.stream()
                .filter(vehicle -> vehicle.getLicensePlate().equalsIgnoreCase(licensePlate)).findFirst();
    }

    @Override
    public List<Vehicle> findByType(String type) {
        List<Vehicle> vehicles = findAll();
        return vehicles.stream().filter(vehicle -> vehicle.getType().equalsIgnoreCase(type)).collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> findByModel(String model) {
        List<Vehicle> vehicles = findAll();
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

    ;
}