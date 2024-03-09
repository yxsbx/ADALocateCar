package com.adalocatecar.repository.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.VehicleRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class VehicleRepositoryImpl extends GenericsRepositoryImpl<Vehicle, String> implements VehicleRepository {

    private static final File filePath = new File("src/data/vehicles.txt");
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
            e.printStackTrace();
        }
    }

    @Override
    public VehicleDTO findByLicensePlate(String licensePlate) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate().equals(licensePlate)) {
                return convertToDTO(vehicle);
            }
        }
        return null;
    }

    @Override
    public List<VehicleDTO> findByType(String type) {
        List<VehicleDTO> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getType().equalsIgnoreCase(type)) {
                matchingVehicles.add(convertToDTO(vehicle));
            }
        }
        return matchingVehicles;
    }

    @Override
    public List<VehicleDTO> findByModel(String model) {
        List<VehicleDTO> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getBrand().equalsIgnoreCase(model) || vehicle.getType().equalsIgnoreCase(model)) {
                matchingVehicles.add(convertToDTO(vehicle));
            }
        }
        return matchingVehicles;
    }

    @Override
    public List<VehicleDTO> findByYear(int year) {
        List<VehicleDTO> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getYear() == year) {
                matchingVehicles.add(convertToDTO(vehicle));
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
        String brand = parts[1];
        String type = parts[2];
        int year = Integer.parseInt(parts[3]);
        return new Vehicle(licensePlate, brand, type, year);
    }

    @Override
    protected String objectToString(Vehicle object) {
        return String.join(",", object.getLicensePlate(), object.getBrand(), object.getType(), String.valueOf(object.getYear()));
    }

    @Override
    protected String getId(Vehicle entity) {
        return entity.getLicensePlate();
    }

    private VehicleDTO convertToDTO(Vehicle vehicle) {
        return new VehicleDTO(vehicle.getLicensePlate(), vehicle.getBrand(), vehicle.getType(), vehicle.getYear());
    }
}

