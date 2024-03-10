package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.model.Rental;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.service.RentalService;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.ValidationRentals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RentalServiceImpl implements RentalService {

    private final ClientService clientService;
    private final VehicleService vehicleService;
    private final List<Rental> rentals = new ArrayList<>();

    public RentalServiceImpl(ClientService clientService, VehicleService vehicleService) {
        this.clientService = clientService;
        this.vehicleService = vehicleService;
    }

    @Override
    public String rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate, String agencyLocal) {
        try {
            ValidationRentals.validateClientType(clientId);
            ValidationRentals.validateRentalDuration(startDate, expectedEndDate);

            Vehicle vehicle = vehicleService.findVehicleByLicensePlate(licensePlate);
            if (vehicle == null || !vehicleService.isVehicleAvailable(licensePlate, startDate, expectedEndDate)) {
                return "The selected vehicle is not available for rental.";
            }

            Optional<ClientDTO> clientOptional = clientService.findClientById(clientId);
            if (clientOptional.isPresent()) {
                ClientDTO clientDTO = clientOptional.get();
                Client client = new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getType());

                if (!clientService.assignVehicleToClient(vehicle, clientOptional, startDate, expectedEndDate)) {
                    return "Failed to rent vehicle to client";
                }

                vehicleService.markVehicleAsUnavailable(licensePlate, startDate, expectedEndDate);
                Rental rental = new Rental(client, startDate, expectedEndDate, agencyLocal);
                rentals.add(rental);

                return "Vehicle rented successfully";
            } else {
                return "Client not found";
            }
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }

    @Override
    public String returnVehicle(String licensePlate, LocalDateTime actualEndDate) {
        try {
            Vehicle vehicle = vehicleService.findVehicleByLicensePlate(licensePlate);
            if (vehicle == null) {
                return "Vehicle not found";
            }

            Rental rental = findRentalByLicensePlate(licensePlate);
            if (rental == null) {
                return "Rental record not found";
            }

            ValidationRentals.validateRentalDuration(rental.getStartDate(), actualEndDate);
            ValidationRentals.validateReturnDate(rental.getStartDate(), actualEndDate);

            rental.setActualEndDate(actualEndDate);
            vehicleService.markVehicleAsAvailable(licensePlate);

            return "Vehicle returned successfully";
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }

    @Override
    public List<RentalDTO> findAllRentals() {
        return rentals.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public RentalDTO findRentalByClientId(String clientId) {
        return rentals.stream()
                .filter(rental -> rental.getClientWhoRented().getId().equals(clientId))
                .findFirst()
                .map(this::convertToDTO)
                .orElse(null);
    }

    private Rental findRentalByLicensePlate(String licensePlate) {
        return rentals.stream()
                .filter(rental -> rental.getRentedVehicles().stream().anyMatch(vehicle -> vehicle.getLicensePlate().equals(licensePlate)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Rental> findRentalsByVehicleLicensePlate(String licensePlate) {
        List<Rental> rentalsByVehicle = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getRentedVehicles().stream().anyMatch(vehicle -> vehicle.getLicensePlate().equals(licensePlate))) {
                rentalsByVehicle.add(rental);
            }
        }
        return rentalsByVehicle;
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
    }
}