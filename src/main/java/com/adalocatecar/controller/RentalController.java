package com.adalocatecar.controller;

import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.service.RentalService;
import com.adalocatecar.utility.ValidationInput;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    public void manageRentals(Scanner scanner) {
        while (true) {
            System.out.println("\nRental Management");
            System.out.println("1. Rent a Vehicle");
            System.out.println("2. Return a Vehicle");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option) {
                case 1:
                    rentVehicle(scanner);
                    break;
                case 2:
                    returnVehicle(scanner);
                    break;
                case 3:
                    System.out.println("Exiting Rental Management...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void rentVehicle(Scanner scanner) {
        String licensePlate, clientId, agencyLocal;
        LocalDateTime startDate, endDate;

        do {
            System.out.println("Enter the license plate of the vehicle to rent:");
            licensePlate = scanner.nextLine();
        } while (!ValidationInput.isValidLicensePlate(licensePlate));

        do {
            System.out.println("Enter the client document:");
            clientId = scanner.nextLine();
        } while (!ValidationInput.validateClientIdFormat(clientId));

        do {
            System.out.println("Enter the location of rental agency:");
            agencyLocal = scanner.nextLine();
        } while (!ValidationInput.isValidLocation(agencyLocal)); // Melhorar Validação para a localização

        do {
            System.out.println("Enter start date (yyyy-MM-dd HH:mm):");
            String startDateStr = scanner.nextLine();
            startDate = ValidationInput.isValidDate(startDateStr);
        } while (startDate == null);

        do {
            System.out.println("Enter expected end date (yyyy-MM-dd HH:mm):");
            String endDateStr = scanner.nextLine();
            endDate = ValidationInput.isValiEndDate(startDate, endDateStr);
        } while (endDate == null);

        String response = rentalService.rentVehicle(licensePlate, clientId, startDate, endDate, agencyLocal);
        System.out.println(response);
    }

    private void returnVehicle(Scanner scanner) {
        System.out.println("Enter the license plate of the vehicle to return:");
        String licensePlate = scanner.nextLine();
        System.out.println("Enter actual end date (yyyy-MM-dd HH:mm):");
        String endDateStr = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);

            rentalService.returnVehicle(licensePlate, endDate);
            System.out.println("Vehicle returned successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}