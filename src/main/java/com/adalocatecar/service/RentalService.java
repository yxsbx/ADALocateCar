package com.adalocatecar.service;

import java.time.LocalDateTime;

public interface RentalService {

    /**
     * Rents a vehicle to a client based on the specified parameters.
     *
     * @param licensePlate The license plate of the vehicle to be rented.
     * @param clientId     The ID of the client renting the vehicle.
     * @param startDate    The start date of the rental.
     * @param agencyLocal  The agency location for the rental.
     * @return A message indicating the status of the rental operation.
     */

    String rentVehicle(String licensePlate, String clientId, LocalDateTime startDate, String agencyLocal);

    /**
     * Returns a rented vehicle based on the specified parameters.
     *
     * @param licensePlate  The license plate of the vehicle to be returned.
     * @param actualEndDate The actual end date of the rental.
     * @return A message indicating the status of the return operation.
     */

    String returnVehicle(String licensePlate, LocalDateTime actualEndDate);
}