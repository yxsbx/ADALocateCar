package com.adalocatecar.service.impl;

import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.model.Rental;
import com.adalocatecar.service.RentalService;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.Validation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentalServiceImpl implements RentalService {

    private final List<Rental> rentals = new ArrayList<>();
    private VehicleService vehicleService;
    private String licensePlate;

    @Override
    public Validation rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate) {
        if (vehicleService.isVehicleAvailable(licensePlate, startDate, expectedEndDate)) {
            Rental rental = new Rental(licensePlate, clientId, startDate, expectedEndDate);
            rentals.add(rental);
            vehicleService.markVehicleAsUnavailable(licensePlate);
            return Validation.ok("Vehicle rented successfully.");
        } else {
            return Validation.error("Vehicle is not available for rental.");
        }
    }

    @Override
    public Validation returnVehicle(String licensePlate, LocalDateTime actualEndDate) {
        Rental rental = findRentalByLicensePlate(licensePlate);
        if (rental != null) {
            rentals.remove(rental);
            vehicleService.markVehicleAsAvailable(licensePlate);
            double rentalCost = calculateRentalCost(rental.getStartDate(), actualEndDate);
            rental.setActualEndDate(actualEndDate);
            rental.setTotalCost(rentalCost);
            return Validation.ok("Vehicle returned successfully. Rental cost: $" + rentalCost);
        } else {
            return Validation.error("No active rental found for this vehicle.");
        }
    }

    private double calculateRentalCost(LocalDateTime startDate, LocalDateTime actualEndDate) {
        actualEndDate = actualEndDate.plusHours(24 - actualEndDate.getHour());

        Duration duration = Duration.between(startDate, actualEndDate);
        long days = duration.toDays();
        long extraHours = duration.minusDays(days).toHours();
        if (extraHours > 0) {
            days++;
        }

        double baseCost = 0.00;
        String vehicleType = vehicleService.findVehicleByLicensePlate(licensePlate).getType();
        if (vehicleType != null) {
            baseCost = switch (vehicleType) {
                case "SMALL" -> 100.00;
                case "MEDIUM" -> 150.00;
                case "SUV" -> 200.00;
                default -> 0.00;
            };
        }

        if (days > 5) {
            baseCost -= baseCost * 0.05;
        } else if (days > 3) {
            baseCost -= baseCost * 0.10;
        }

        return baseCost * days;
    }

    @Override
    public List<RentalDTO> findAllRentals() {
        List<RentalDTO> rentalDTOs = new ArrayList<>();
        for (Rental rental : rentals) {
            rentalDTOs.add(convertToDTO(rental));
        }
        return rentalDTOs;
    }

    @Override
    public RentalDTO findRentalByClientId(String clientId) {
        for (Rental rental : rentals) {
            if (rental.getClientId().equals(clientId)) {
                return convertToDTO(rental);
            }
        }
        return null;
    }

    private Rental findRentalByLicensePlate(String licensePlate) {
        for (Rental rental : rentals) {
            if (rental.getLicensePlate().equals(licensePlate)) {
                return rental;
            }
        }
        return null;
    }

    private RentalDTO convertToDTO(Rental rental) {
        return new RentalDTO(rental.getLicensePlate(), rental.getClientId(), rental.getStartDate(), rental.getExpectedEndDate(), rental.getActualEndDate(), rental.getTotalCost());
    }
}
