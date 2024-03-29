package com.adalocatecar.controller;

import com.adalocatecar.service.RentalService;
import com.adalocatecar.utility.ValidationInput;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Controls rental-related operations such as renting and returning vehicles.
 */

public class RentalController {
    private final RentalService rentalService;

    /**
     * Constructor for RentalController.
     *
     * @param rentalService The service responsible for rental operations.
     */

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    /**
     * Manages the rental operations menu and user input.
     *
     * @param scanner The scanner to read user input.
     */

    public void manageRentals(Scanner scanner) {
        while (true) {
            System.out.println("\nRental Management");
            System.out.println("1. Rent a Vehicle");
            System.out.println("2. Return a Vehicle");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option) {
                case 1:
                    rentVehicle(scanner);
                    break;
                case 2:
                    returnVehicle(scanner);
                    break;
                case 0:
                    System.out.println("Exiting Rental Management...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    /**
     * Handles the process of renting a vehicle.
     *
     * @param scanner The scanner to read user input.
     */

    private void rentVehicle(Scanner scanner) {
        String licensePlate, clientId, agencyLocal;
        LocalDateTime startDate;

        do {
            System.out.println("Enter the license plate of the vehicle to rent:");
            licensePlate = scanner.nextLine();
        } while (!ValidationInput.isValidLicensePlate(licensePlate));

        do {
            System.out.println("Enter the client document:");
            clientId = scanner.nextLine();
        } while (!ValidationInput.isValidClientId(clientId));

        do {
            System.out.println("Enter the location of rental agency:");
            agencyLocal = scanner.nextLine();
        } while (!ValidationInput.isValidLocation(agencyLocal));

        do {
            System.out.println("Enter start date (yyyy-MM-dd HH:mm):");
            String startDateStr = scanner.nextLine();
            startDate = ValidationInput.isValidDate(startDateStr);
        } while (startDate == null);

        String response = rentalService.rentVehicle(licensePlate, clientId, startDate, agencyLocal);
        System.out.println(response);
    }

    /**
     * Handles the process of returning a rented vehicle.
     *
     * @param scanner The scanner to read user input.
     */

    private void returnVehicle(Scanner scanner) {
        String licensePlate;
        LocalDateTime endDate;
        do {
            System.out.println("Enter the license plate of the vehicle to return:");
            licensePlate = scanner.nextLine();
        } while (!ValidationInput.isValidLicensePlate(licensePlate));

        do {

            System.out.println("Enter actual end date (yyyy-MM-dd HH:mm):");
            String endDateStr = scanner.nextLine();
            endDate = ValidationInput.isValidDate(endDateStr);
        } while (endDate == null);


        try {
            System.out.println(rentalService.returnVehicle(licensePlate, endDate));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}