package com.adalocatecar.repository.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.VehicleRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    public Vehicle findByLicensePlate(String licensePlate) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate().equals(licensePlate)) {
                return vehicle;
            }
        }
        return null;
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
            if (vehicle.getType().equalsIgnoreCase(model)) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    @Override
    public List<Vehicle> findByYear(int year) {
        List<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getYear() == year) {
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
        int year = Integer.parseInt(parts[3]);
        return new Vehicle(licensePlate, model, type, year);
    }

    @Override
    protected String objectToString(Vehicle object) {
        return String.join(",", object.getLicensePlate(), object.getType(), String.valueOf(object.getYear()));
    }

    @Override
    protected String getId(Vehicle entity) {
        return entity.getLicensePlate();
    }

    private VehicleDTO convertToDTO(Vehicle vehicle) {
        return new VehicleDTO(vehicle.getLicensePlate(), vehicle.getModel(), vehicle.getType(), vehicle.getYear());
    }

    @Override
    protected String getName(Vehicle entity){ return  entity.getModel();};
}

