package com.adalocatecar.service.impl;

import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.model.Rental;
import com.adalocatecar.service.RentalService;
import com.adalocatecar.service.VehicleService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentalServiceImpl implements RentalService {

    private final List<Rental> rentals = new ArrayList<>();
    private final VehicleService vehicleService;

    public RentalServiceImpl(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public void rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate) {
        if (vehicleService.isVehicleAvailable(licensePlate, startDate, expectedEndDate)) {
            Rental rental = new Rental(licensePlate, clientId, startDate, expectedEndDate);
            rentals.add(rental);
            vehicleService.markVehicleAsUnavailable(licensePlate);
            System.out.println("Vehicle rented successfully.");
        } else {
            System.out.println("Vehicle is not available for rental.");
        }
    }

    @Override
    public void returnVehicle(String licensePlate, LocalDateTime actualEndDate) {
        Rental rental = findRentalByLicensePlate(licensePlate);
        if (rental != null) {
            rentals.remove(rental);
            vehicleService.markVehicleAsAvailable(licensePlate);
            System.out.println("Vehicle returned successfully.");
        } else {
            System.out.println("No active rental found for this vehicle.");
        }
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
        return new RentalDTO(rental.getLicensePlate(), rental.getClientId(), rental.getStartDate(), rental.getExpectedEndDate());
    }
}