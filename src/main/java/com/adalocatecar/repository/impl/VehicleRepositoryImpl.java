package com.adalocatecar.repository.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Rental;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.VehicleRepository;
import com.adalocatecar.utility.Converter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VehicleRepositoryImpl extends GenericsRepositoryImpl<Vehicle, String> implements VehicleRepository {

    private static final File filePath = new File("src/data/vehicles.txt");
    private static final Logger logger = Logger.getLogger(VehicleRepositoryImpl.class.getName());
    private final List<Vehicle> vehicles = new ArrayList<>();

    public VehicleRepositoryImpl() {
        super(filePath);
        loadEntities();
    }

    private void loadEntities() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Vehicle vehicle = stringToObject(line);
                vehicles.add(vehicle);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading entities from file", e);
        }
    }

    @Override
    public Optional<Vehicle> findByLicensePlate(String licensePlate) {
        return vehicles.stream()
                .filter(vehicle -> vehicle.getLicensePlate().equalsIgnoreCase(licensePlate)).findFirst();
    }

    @Override
    public List<Vehicle> findByType(String type) {
        List<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getType().equalsIgnoreCase(type)) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    @Override
    public List<Vehicle> findByModel(String model) {
        List<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getModel().toLowerCase().contains(model.toLowerCase())) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    @Override
    protected Vehicle stringToObject(String str) {
        String[] parts = str.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid string format for Vehicle object");
        }
        String licensePlate = parts[0];
        String model = parts[1];
        String type = parts[2];
        boolean available = Boolean.parseBoolean(parts[3]);
        Vehicle vehicle = new Vehicle(licensePlate, model, type);
        vehicle.setAvailable(available);
        return vehicle;
    }

    @Override
    protected String objectToString(Vehicle object) {
        return String.join(",", object.getLicensePlate(), object.getModel(), object.getType(), String.valueOf(object.isAvailable()));
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