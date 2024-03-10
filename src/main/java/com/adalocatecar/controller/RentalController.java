package com.adalocatecar.controller;

import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.service.RentalService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            System.out.println("3. List All Rentals");
            System.out.println("4. Back to Main Menu");
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
                    listAllRentals();
                    break;
                case 4:
                    System.out.println("Exiting Rental Management...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void rentVehicle(Scanner scanner) {
        System.out.println("Enter the license plate of the vehicle to rent:");
        String licensePlate = scanner.nextLine();
        System.out.println("Enter the client document:");
        String clientId = scanner.nextLine();
        System.out.println("Enter the location of rental agency:");
        String agencyLocal = scanner.nextLine();
        System.out.println("Enter start date (yyyy-MM-dd HH:mm):");
        String startDateStr = scanner.nextLine();
        System.out.println("Enter expected end date (yyyy-MM-dd HH:mm):");
        String endDateStr = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);

            String response = rentalService.rentVehicle(licensePlate, clientId, startDate, endDate, agencyLocal);
            System.out.println(response);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter dates in the format yyyy-MM-dd HH:mm.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
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

    private void listAllRentals() {
        try {
            List<RentalDTO> rentals = rentalService.findAllRentals();
            if (rentals.isEmpty()) {
                System.out.println("No rentals found.");
            } else {
                System.out.println("All Rentals:");
                rentals.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}