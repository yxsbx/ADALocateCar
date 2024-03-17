package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.service.RentalService;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.ValidationInput;

import java.time.Duration;
import java.time.LocalDateTime;

public class RentalServiceImpl implements RentalService {

    private final ClientService clientService;
    private final VehicleService vehicleService;


    public RentalServiceImpl(ClientService clientService, VehicleService vehicleService) {
        this.clientService = clientService;
        this.vehicleService = vehicleService;
    }

    @Override
    public String rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, String agencyLocal) {
        try {
            VehicleDTO vehicleDTO = vehicleService.searchVehicleByLicensePlate(licensePlate);
            if (!ValidationInput.validateVehicleAvailability(vehicleDTO.isAvailable())) {
                throw new RuntimeException(ValidationInput.VEHICLE_NOT_AVAILABLE);
            }

            if (!ValidationInput.isValidLocation(agencyLocal)) {
                throw new RuntimeException(ValidationInput.INVALID_LOCATION_FORMAT);
            }

            if (startDate == null || startDate.toString().isEmpty()) {
                throw new RuntimeException(ValidationInput.INVALID_DATE_FORMAT);
            }

            RentalDTO rentalDTO = new RentalDTO(true, clientId, agencyLocal, startDate);
            vehicleDTO.setRentalContract(rentalDTO);
            vehicleService.updateVehicle(vehicleDTO);

            return ValidationInput.SUCCESS_RENTED;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String returnVehicle(String licensePlate, LocalDateTime actualEndDate) {
        try {
            VehicleDTO vehicleDTO = vehicleService.searchVehicleByLicensePlate(licensePlate);
            if (vehicleDTO.isAvailable() || vehicleDTO.getRentalContract() == null) {
                throw new RuntimeException(ValidationInput.VEHICLE_NOT_RENTED);
            }

            LocalDateTime startDate = vehicleDTO.getRentalContract().startDate();
            if (actualEndDate == null || !actualEndDate.isAfter(startDate)) {
                throw new RuntimeException(ValidationInput.INVALID_RETURN_DATE);
            }

            double cost = calculateRentalCost(vehicleDTO, actualEndDate);
            vehicleDTO.setRentalContract(null);
            vehicleService.updateVehicle(vehicleDTO);

            return String.format("Vehicle returned successfully, total cost: $%.2f", cost);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private double calculateRentalCost(VehicleDTO vehicle, LocalDateTime actualEndDate) {
        LocalDateTime startDate = vehicle.getRentalContract().startDate();
        long daysRented = Duration.between(startDate, actualEndDate).toDays() + 1;

        double dailyRate = getDailyRateBasedOnVehicleType(vehicle.getType());
        double totalCost = daysRented * dailyRate;

        return applyDiscountIfEligible(totalCost, vehicle.getRentalContract().idClientWhoRented(), daysRented);
    }

    private double getDailyRateBasedOnVehicleType(String type) {
        return switch (type) {
            case "SMALL" -> ValidationInput.BASE_DAILY_RATE_SMALL;
            case "MEDIUM" -> ValidationInput.BASE_DAILY_RATE_MEDIUM;
            case "SUV" -> ValidationInput.BASE_DAILY_RATE_SUV;
            default -> 0.0;
        };
    }

    private double applyDiscountIfEligible(double totalCost, String clientId, long daysRented) {
        try {
            ClientDTO client = clientService.searchClientByDocument(clientId);
            if (client.getClientType().equalsIgnoreCase("INDIVIDUAL") && daysRented >= 5) {
                return totalCost * (1 - ValidationInput.DISCOUNT_FOR_INDIVIDUAL);
            } else if (client.getClientType().equalsIgnoreCase("CORPORATE")) {
                return totalCost * (1 - ValidationInput.DISCOUNT_FOR_CORPORATE);
            }
        } catch (RuntimeException e) {
            System.err.println("Error while fetching client data: " + e.getMessage());
        }
        return totalCost;
    }
}