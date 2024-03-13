package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.service.RentalService;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.ValidationRentals;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public class RentalServiceImpl implements RentalService {

    private final ClientService clientService;
    private final VehicleService vehicleService;


    public RentalServiceImpl(ClientService clientService, VehicleService vehicleService) {
        this.clientService = clientService;
        this.vehicleService = vehicleService;
    }

    @Override
    public String rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate, String agencyLocal) {
        try {
            ValidationRentals.validateClientIdFormat(clientId);
            ValidationRentals.validateRentalDuration(startDate, expectedEndDate);

            VehicleDTO vehicle = vehicleService.findVehicleByLicensePlate(licensePlate);
            if ((vehicle == null) || vehicle.isAvailable()) {
                return "The selected vehicle is not available for rental.";
            }

            Optional<ClientDTO> clientOptional = Optional.ofNullable(clientService.findClientByDocument(clientId));
            if (clientOptional.isPresent()) {
                ClientDTO clientDTO = clientOptional.get();
                Client client = new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getType());

                if (!clientService.assignVehicleToClient(Converter.convertToEntity(vehicle), client, agencyLocal, startDate, expectedEndDate)) {
                    return "Failed to rent vehicle to client";
                }

                vehicleService.updateVehicle(vehicle);
                return "Vehicle rented successfully";
            } else {
                return "Client not found";
            }
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String returnVehicle(String licensePlate, LocalDateTime actualEndDate) {
        try {
            VehicleDTO vehicle = vehicleService.findVehicleByLicensePlate(licensePlate);
            if (vehicle == null) {
                return "Vehicle not found";
            }

            ValidationRentals.validateRentalDuration(vehicle.getRentalContract().getStartDate(), actualEndDate);
            ValidationRentals.validateReturnDate(vehicle.getRentalContract().getStartDate(), actualEndDate);

            vehicle.getRentalContract().setActualEndDate(actualEndDate);

            return "Vehicle returned successfully";
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }
/*
    @Override
    public List<RentalDTO> findAllRentals() {
        return new ArrayList<>(findAllRentals());
    }

    @Override
    public RentalDTO findRentalByClientId(String clientId) {
        return findRentalByClientId(clientId);
    }
    @Override
    public Rental findRentalByLicensePlate(String licensePlate) {
        return findRentalByLicensePlate(licensePlate);
    }



    private RentalDTO convertToDTO(Rental rental) {
        double totalCost = calculateRentalCost(rental);
        return new RentalDTO(
                rental.getRentedVehicles().getFirst().getLicensePlate(),
                rental.getClientWhoRented().getId(),
                rental.getStartDate(),
                rental.getExpectedEndDate(),
                rental.getActualEndDate(),
                rental.getAgencyLocal(),
                totalCost
        );
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
    }*/
}