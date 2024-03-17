package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.service.RentalService;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.ValidationInput;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Implementation of the RentalService interface that handles rental-related operations.
 */

public class RentalServiceImpl implements RentalService {

    private final ClientService clientService;
    private final VehicleService vehicleService;

    /**
     * Constructs a new RentalServiceImpl with the specified ClientService and VehicleService.
     *
     * @param clientService  The client service to be used for client-related operations.
     * @param vehicleService The vehicle service to be used for vehicle-related operations.
     */

    public RentalServiceImpl(ClientService clientService, VehicleService vehicleService) {
        this.clientService = clientService;
        this.vehicleService = vehicleService;
    }

    /**
     * Rents a vehicle to a client.
     *
     * @param licensePlate The license plate of the vehicle to be rented.
     * @param clientId     The ID of the client renting the vehicle.
     * @param startDate    The start date of the rental.
     * @param agencyLocal  The local agency where the vehicle is rented from.
     * @return A success message if the rental is successful, or an error message if an exception occurs.
     */

    @Override
    public String rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, String agencyLocal) {

        try {
            VehicleDTO vehicleDTO = vehicleService.searchVehicleById(licensePlate);
            if (!ValidationInput.validateVehicleAvailability(vehicleDTO.isAvailable())) {
                throw new IllegalArgumentException(ValidationInput.VEHICLE_NOT_AVAILABLE);
            }

            if (!ValidationInput.isValidLocation(agencyLocal)) {
                throw new IllegalArgumentException(ValidationInput.INVALID_LOCATION_FORMAT);
            }

            if (startDate == null || startDate.toString().isEmpty()) {
                throw new IllegalArgumentException(ValidationInput.INVALID_DATE_FORMAT);
            }

            RentalDTO rentalDTO = new RentalDTO(true, clientId, agencyLocal, startDate);
            vehicleDTO.setRentalContract(rentalDTO);
            vehicleService.updateVehicle(vehicleDTO);

            ClientDTO client = clientService.searchClientById(clientId);
            client.addRentedVehicle(licensePlate);
            clientService.updateClient(client);

            return ValidationInput.SUCCESS_RENTED;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Returns a vehicle that was previously rented.
     *
     * @param licensePlate  The license plate of the vehicle to be returned.
     * @param actualEndDate The actual end date of the rental period.
     * @return A success message with the total cost if the return is successful, or an error message if an exception occurs.
     */

    @Override
    public String returnVehicle(String licensePlate, LocalDateTime actualEndDate) {
        try {
            VehicleDTO vehicleDTO = vehicleService.searchVehicleById(licensePlate);
            if (vehicleDTO.isAvailable() || vehicleDTO.getRentalContract() == null) {
                throw new IllegalArgumentException(ValidationInput.VEHICLE_NOT_RENTED);
            }

            LocalDateTime startDate = vehicleDTO.getRentalContract().startDate();
            if (actualEndDate == null || !actualEndDate.isAfter(startDate)) {
                throw new IllegalArgumentException(ValidationInput.INVALID_RETURN_DATE);
            }

            ClientDTO client = clientService.searchClientById(vehicleDTO.getRentalContract().idClientWhoRented());
            client.getRentedVehiclesPlates().remove(licensePlate);
            clientService.updateClient(client);

            double cost = calculateRentalCost(vehicleDTO, actualEndDate);
            vehicleDTO.setRentalContract(new RentalDTO(false,"" , "", null));
            vehicleService.updateVehicle(vehicleDTO);

            return String.format("Vehicle returned successfully, total cost: $%.2f", cost);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Calculates the rental cost based on the vehicle type, rental duration, and client type.
     *
     * @param vehicle         The vehicle DTO for which the cost is calculated.
     * @param actualEndDate   The actual end date of the rental period.
     * @return The total rental cost after applying any applicable discounts.
     */

    private double calculateRentalCost(VehicleDTO vehicle, LocalDateTime actualEndDate) {
        Duration duration = Duration.between(vehicle.getRentalContract().startDate(), actualEndDate);
        long daysRented = duration.toDays() + (duration.toHours() % 24 > 0 ? 1 : 0);

        double totalCost = getBaseCost(vehicle.getType()) * daysRented;
        totalCost = applyDiscountIfEligible(totalCost, vehicle.getRentalContract().idClientWhoRented(), daysRented);

        return totalCost;
    }

    /**
     * Retrieves the daily rental rate based on the vehicle type.
     *
     * @param type The type of the vehicle.
     * @return The daily rental rate for the specified vehicle type.
     */

    private double getBaseCost(String type) {
        switch (type) {
            case "SMALL":
                return ValidationInput.BASE_DAILY_RATE_SMALL;
            case "MEDIUM":
                return ValidationInput.BASE_DAILY_RATE_MEDIUM;
            case "SUV":
                return ValidationInput.BASE_DAILY_RATE_SUV;
            default:
                return 0.0;
        }
    }

    /**
     * Applies discounts to the total rental cost based on client type and rental duration.
     *
     * @param totalCost   The total rental cost before applying discounts.
     * @param clientId    The ID of the client who rented the vehicle.
     * @param daysRented  The number of days the vehicle was rented for.
     * @return The total rental cost after applying any applicable discounts.
     */

    private double applyDiscountIfEligible(double totalCost, String clientId, long daysRented) {
        if (daysRented >= 3) {
            ClientDTO clientWhoRented = clientService.searchClientById(clientId);
            String clientType = clientWhoRented.getClientType();
            if ("INDIVIDUAL".equals(clientType) && daysRented >= 5) {
                totalCost *= (1 - ValidationInput.DISCOUNT_FOR_INDIVIDUAL);
            } else if ("CORPORATE".equals(clientType)) {
                totalCost *= (1 - ValidationInput.DISCOUNT_FOR_CORPORATE);
            }
        }
        return totalCost;
    }
}