package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.service.RentalService;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.ValidationRentals;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class RentalServiceImpl implements RentalService {

    private final ClientService clientService;
    private final VehicleService vehicleService;


    public RentalServiceImpl(ClientService clientService, VehicleService vehicleService) {
        this.clientService = clientService;
        this.vehicleService = vehicleService;
    }

    public String rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate, String agencyLocal) {
        VehicleDTO vehicleDTO;
        ClientDTO clientDTO;
        try {
            vehicleDTO = vehicleService.findVehicleByLicensePlate(licensePlate);
            ValidationRentals.validateVehicleAvailability(vehicleDTO);
            clientDTO = clientService.findClientByDocument(clientId);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        RentalDTO rentalDTO = new RentalDTO(true, clientDTO.getId(), agencyLocal, startDate, expectedEndDate);
        vehicleDTO.setRentalContract(rentalDTO);
        clientDTO.getRentedVehiclesPlates().add(vehicleDTO.getLicensePlate());

        System.out.println(clientDTO);
        try {
            vehicleService.updateVehicle(vehicleDTO);
            clientService.updateClient(clientDTO);
            return "Vehicle rented successfully";

        } catch (IOException e) {
            return "Error while updating vehicle information: " + e.getMessage();
        }catch(RuntimeException e) {
            return "Error while updating client information:" + e.getMessage();
        }
    }

    @Override
    public String returnVehicle(String licensePlate, LocalDateTime actualEndDate) {
        VehicleDTO vehicleDTO;
        try{
            vehicleDTO = vehicleService.findVehicleByLicensePlate(licensePlate);
        } catch (RuntimeException e) {
            return e.getMessage();
        }

        if (!vehicleDTO.isAvailable()) {
            return "Vehicle not currently rented";
        }
        RentalDTO rentalDTO = vehicleDTO.getRentalContract();
        if (rentalDTO == null) {
            return "Rental agreement not found.";
        }
        rentalDTO.setExpectedEndDate(actualEndDate);

        try {
            vehicleService.updateVehicle(vehicleDTO);
            return "Vehicle returned successfully";
        } catch (Exception e) {
            return "Error while returning vehicle: " + e.getMessage();
        }
    }

    private double calculateRentalCost(Vehicle vehicle, LocalDateTime ActualEndDate) {
        Duration duration = Duration.between(vehicle.getRentalContract().getStartDate(),
                vehicle.getRentalContract().getActualEndDate());

        long days = duration.toDays() + (duration.toHours() % 24 > 0 ? 1 : 0);

        double totalCost = 0.0;

        double baseCost = switch (vehicle.getType()) {
            case "SMALL" -> ValidationRentals.BASE_DAILY_RATE_SMALL;
            case "MEDIUM" -> ValidationRentals.BASE_DAILY_RATE_MEDIUM;
            case "SUV" -> ValidationRentals.BASE_DAILY_RATE_SUV;
            default -> 0.0;
        };
        totalCost += baseCost * days;

        if(days >= 3) {
            ClientDTO clientWhoRented = clientService.findClientByDocument(vehicle.getRentalContract().getIdClientWhoRented());
            String clientType = clientWhoRented.getClientType();
            if ("Individual".equals(clientType) && days >= 5) {
                totalCost *= (1 - ValidationRentals.DISCOUNT_FOR_INDIVIDUAL);
            } else if ("Corporate".equals(clientType)) {
                totalCost *= (1 - ValidationRentals.DISCOUNT_FOR_CORPORATE);
            }
        }

        return totalCost;
    }
}