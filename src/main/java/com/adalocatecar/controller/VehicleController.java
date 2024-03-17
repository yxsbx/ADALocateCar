package com.adalocatecar.controller;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.ValidationInput;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Controls vehicle-related operations such as creating, updating, deleting, and searching for vehicles.
 */

public class VehicleController {
    private final VehicleService vehicleService;

    /**
     * Constructor for VehicleController.
     *
     * @param vehicleService The service responsible for vehicle operations.
     */

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Manages the vehicle operations menu and user input.
     *
     * @param scanner The scanner to read user input.
     */

    public void manageVehicles(Scanner scanner) {
        while (true) {
            System.out.println("\nVehicle Management");
            System.out.println("1. Create vehicle");
            System.out.println("2. Read all vehicles");
            System.out.println("3. Update vehicle information");
            System.out.println("4. Delete vehicle");
            System.out.println("5. Search vehicle by model or license plate");
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
                    createVehicle(scanner);
                    break;
                case 2:
                    readAllVehicles();
                    break;
                case 3:
                    updateVehicle(scanner);
                    break;
                case 4:
                    deleteVehicle(scanner);
                    break;
                case 5:
                    searchVehicle(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    /**
     * Handles the process of creating a new vehicle.
     *
     * @param scanner The scanner to read user input.
     */

    public void createVehicle(Scanner scanner) {
        String licensePlate, model, type;
        System.out.println("Enter vehicle details:");
        do {
            System.out.print("License Plate (ABC1D23): ");
            licensePlate = scanner.nextLine();
        } while (!ValidationInput.isValidLicensePlate(licensePlate));

        do {
            System.out.print("Model: ");
            model = scanner.nextLine();
        } while (!ValidationInput.isValidModel(model));

        do {
            System.out.print("Type (SMALL, MEDIUM, SUV): ");
            type = scanner.nextLine();
        } while (!ValidationInput.isValidType(type));

        VehicleDTO newVehicle = new VehicleDTO(licensePlate.toUpperCase(), model, type, null);

        try {
            vehicleService.createVehicle(newVehicle);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while saving the vehicle.");
        }
    }

    /**
     * Retrieves and displays all vehicles from the service.
     */

    private void readAllVehicles() {
        try {
            List<VehicleDTO> vehicles = vehicleService.readAllVehicles();
            System.out.println("\nAll Vehicles:");
            for (VehicleDTO vehicle : vehicles) {
                System.out.println(vehicle);
            }
        } catch (Exception e) {
            System.out.println("Failed to display vehicles: " + e.getMessage());
        }
    }

    /**
     * Handles the process of updating existing vehicle information.
     *
     * @param scanner The scanner to read user input.
     */

    public void updateVehicle(Scanner scanner) {
        String licensePlate, model, type;
        readAllVehicles();

        do {
            System.out.println("\nEnter the license plate of the vehicle that you want to edit:");
            licensePlate = scanner.nextLine().trim();
        } while (!ValidationInput.isValidLicensePlate(licensePlate));

        try {
            vehicleService.searchVehicleById(licensePlate);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }

        do {
            System.out.println("Enter vehicle new model:");
            model = scanner.nextLine().trim();
        } while (!ValidationInput.isValidModel(model));

        do {
            System.out.println("Enter vehicle new type:");
            type = scanner.nextLine().trim();
        } while (!ValidationInput.isValidModel(model));


        VehicleDTO editedVehicle = new VehicleDTO(licensePlate, model, type, null);

        try {
            vehicleService.updateVehicle(editedVehicle);
        } catch (RuntimeException | IOException e) {
            System.out.println("Error while updating client information:" + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a vehicle.
     *
     * @param scanner The scanner to read user input.
     */

    public void deleteVehicle(Scanner scanner) {
        String vehiclelicensePlate;
        readAllVehicles();

        do {
            System.out.println("Enter vehicle license plate to delete");
            vehiclelicensePlate = scanner.nextLine().trim();
        } while (!ValidationInput.isValidLicensePlate(vehiclelicensePlate));

        try {
            vehicleService.deleteVehicle(vehiclelicensePlate);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Handles searching for vehicles by license plate or model.
     *
     * @param scanner The scanner to read user input.
     */

    private void searchVehicle(Scanner scanner) {
        String query, searchType;

        do {
            System.out.println("Enter vehicle license plate or name to search:");
            query = scanner.nextLine().trim();
            searchType = ValidationInput.validateQuerySearchType(query);
        } while (searchType == null);

        try {
            if (searchType.equals("licensePlate")) {
                VehicleDTO vehicle = vehicleService.searchVehicleById(query);
                System.out.println("Client found with the provided document:");
                System.out.println(vehicle);
            } else {
                List<VehicleDTO> vehicles = vehicleService.searchVehiclesByName(query);
                System.out.println("Clients found with the provided name:");
                for (VehicleDTO vehicle : vehicles) {
                    System.out.println(vehicle);
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage() + query);
        }
    }
}