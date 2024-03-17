package com.adalocatecar.service.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.VehicleRepository;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.ValidationInput;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;


    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = Converter.convertToEntity(vehicleDTO);
        saveVehicle(vehicle, "CREATE");
    }

    @Override
    public List<VehicleDTO> readAllVehicles() {
        return vehicleRepository.readAll().stream()
                .map(Converter::convertToDTO)
                .collect(Collectors.toList());
    }

    private List<String> readAllLicensePlates() {
        return vehicleRepository.readAll().stream()
                .map(Vehicle::getLicensePlate)
                .collect(Collectors.toList());
    }

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO) {
        Vehicle existingVehicle = vehicleRepository.searchByLicensePlate(vehicleDTO.getLicensePlate())
                .orElseThrow(() -> new RuntimeException(ValidationInput.VEHICLE_NOT_FOUND));
        existingVehicle.setModel(vehicleDTO.getModel());
        existingVehicle.setType(vehicleDTO.getType());
        saveVehicle(existingVehicle, "UPDATE");
    }

    @Override
    public void deleteVehicle(String licensePlate) {
        VehicleDTO vehicleToDelete = searchVehicleByLicensePlate(licensePlate);
        if (!vehicleToDelete.isAvailable()) {
            throw new RuntimeException(ValidationInput.ERROR_IS_RENTED);
        }
        vehicleRepository.delete(Converter.convertToEntity(vehicleToDelete));
        System.out.println(ValidationInput.VEHICLE_DELETED);
    }

    private void saveVehicle(Vehicle vehicle, String operationType) {
        if ("CREATE".equals(operationType)) {
            List<String> existingLicensePlates = readAllLicensePlates();
            if (!ValidationInput.isUniqueLicensePlate(vehicle.getLicensePlate(), existingLicensePlates)) {
                throw new RuntimeException(ValidationInput.DUPLICATED_LICENSE_PLATE);
            }
        }

        if (!ValidationInput.isValidLicensePlate(vehicle.getLicensePlate())) {
            throw new RuntimeException(ValidationInput.INVALID_LICENSE_PLATE_FORMAT);
        }

        if (!ValidationInput.isValidModel(vehicle.getModel())) {
            throw new RuntimeException(ValidationInput.INVALID_MODEL_FORMAT);
        }

        if (!ValidationInput.isValidType(vehicle.getType())) {
            throw new RuntimeException(ValidationInput.INVALID_TYPE);
        }

        if (operationType.equals("CREATE")) {
            vehicleRepository.create(vehicle);
            System.out.println(ValidationInput.VEHICLE_CREATED);
        } else {
            vehicleRepository.update(vehicle);
            System.out.println(ValidationInput.VEHICLE_UPDATED);
        }
    }


    @Override
    public VehicleDTO searchVehicleByLicensePlate(String licensePlate) {
        return vehicleRepository.searchByLicensePlate(licensePlate)
                .map(Converter::convertToDTO)
                .orElseThrow(() -> new RuntimeException(ValidationInput.VEHICLE_NOT_FOUND));
    }

    @Override
    public List<VehicleDTO> searchVehiclesByModel(String model) {
        return vehicleRepository.searchByModel(model).stream()
                .map(Converter::convertToDTO)
                .collect(Collectors.toList());
    }
}