package com.adalocatecar.controller;

import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.service.RentalService;

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
            System.out.println("3. List All Rentals");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

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
        System.out.println("Enter the client ID:");
        String clientId = scanner.nextLine();
        System.out.println("Enter start date (yyyy-MM-dd HH:mm):");
        String startDateStr = scanner.nextLine();
        System.out.println("Enter expected end date (yyyy-MM-dd HH:mm):");
        String endDateStr = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);

        rentalService.rentVehicle(licensePlate, clientId, startDate, endDate);
        System.out.println("Vehicle rented successfully.");
    }

    private void returnVehicle(Scanner scanner) {
        System.out.println("Enter the license plate of the vehicle to return:");
        String licensePlate = scanner.nextLine();
        System.out.println("Enter actual end date (yyyy-MM-dd HH:mm):");
        String endDateStr = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);

        rentalService.returnVehicle(licensePlate, endDate);
        System.out.println("Vehicle returned successfully.");
    }

    private void listAllRentals() {
        List<RentalDTO> rentals = rentalService.findAllRentals();
        rentals.forEach(System.out::println);
    }
}