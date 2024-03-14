package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.model.Rental;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.service.RentalService;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.ValidationRentals;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class RentalServiceImpl implements RentalService {

    private final ClientService clientService;
    private final VehicleService vehicleService;


    public RentalServiceImpl(ClientService clientService, VehicleService vehicleService) {
        this.clientService = clientService;
        this.vehicleService = vehicleService;
    }

    public String rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate, String agencyLocal) {
        VehicleDTO vehicleDTO = vehicleService.findVehicleByLicensePlate(licensePlate);
        try {
            ValidationRentals.validateVehicleAvailability(vehicleDTO);
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }

        ClientDTO clientDTO = clientService.findClientByDocument(clientId);

        RentalDTO rentalDTO = new RentalDTO(true, clientDTO, agencyLocal, startDate, expectedEndDate, null);
        vehicleDTO.setRentalContract(rentalDTO);
        vehicleDTO.setAvailable(false);

        vehicleDTO.setRentalContract(rentalDTO);

        try {
            vehicleService.updateVehicle(vehicleDTO);
            clientDTO.getRentedVehicles().add(vehicleDTO);
            clientService.updateClient(clientDTO);
            return "Vehicle rented successfully";

        } catch (IOException e) {
            return "Error while updating vehicle information: " + e.getMessage();
        }
    }

    @Override
    public String returnVehicle(String licensePlate, LocalDateTime actualEndDate) {
        VehicleDTO vehicleDTO = vehicleService.findVehicleByLicensePlate(licensePlate);
        if (vehicleDTO == null || !vehicleDTO.isAvailable()) {
            return "Vehicle not found or not currently rented";
        }
        RentalDTO rentalDTO = vehicleDTO.getRentalContract();
        if (rentalDTO == null) {
            return "Rental agreement not found.";
        }
        rentalDTO.setActualEndDate(actualEndDate);
        vehicleDTO.setAvailable(true);

        try {
            vehicleService.updateVehicle(vehicleDTO);
            return "Vehicle returned successfully";
        } catch (Exception e) {
            return "Error while returning vehicle: " + e.getMessage();
        }
    }

    private double calculateRentalCost(Rental rental) {
        Duration duration = Duration.between(rental.getStartDate(), rental.getActualEndDate() != null ? rental.getActualEndDate() : LocalDateTime.now());
        long days = duration.toDays() + (duration.toHours() % 24 > 0 ? 1 : 0);

        double totalCost = 0.0;
        for (Vehicle vehicle : rental.getRentedVehicles()) {
            double baseCost = switch (vehicle.getType()) {
                case "SMALL" -> ValidationRentals.BASE_DAILY_RATE_SMALL;
                case "MEDIUM" -> ValidationRentals.BASE_DAILY_RATE_MEDIUM;
                case "SUV" -> ValidationRentals.BASE_DAILY_RATE_SUV;
                default -> 0.0;
            };
            totalCost += baseCost * days;
        }

        String clientType = rental.getClientWhoRented().getClientType();
        if ("Individual".equals(clientType) && days > 5) {
            totalCost *= (1 - ValidationRentals.DISCOUNT_FOR_INDIVIDUAL);
        } else if ("Corporate".equals(clientType) && days > 3) {
            totalCost *= (1 - ValidationRentals.DISCOUNT_FOR_CORPORATE);
        }

        return totalCost;
    }
}