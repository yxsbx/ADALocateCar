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

    public RentalServiceImpl(VehicleService vehicleService) {
    }

    @Override
    public void rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate) {
        System.out.println("Vehicle rented successfully.");
    }

    @Override
    public void returnVehicle(String licensePlate, LocalDateTime actualEndDate) {
    }

    @Override
    public List<RentalDTO> findAllRentals() {
        return null;
    }

    @Override
    public List<RentalDTO> findRentalByPartialName(String partialName) {
        return null;
    }

    @Override
    public RentalDTO findVehicleByPartialName(String partialName) {
        return null;
    }

    private Rental findRentalByLicensePlate(String licensePlate) {
        return null;
    }

    private double calculateTotalCost(long daysRented, String clientId) {
        return 0;
    }

    private RentalDTO convertToDTO(Rental rental) {
        return null;
    }
}