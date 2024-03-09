package com.adalocatecar.controller;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.Validation;

import java.util.List;
import java.util.Scanner;

public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public void manageVehicles(Scanner scanner) {
        while (true) {
            System.out.println("\nVehicle Management");
            System.out.println("1. Register Vehicle");
            System.out.println("2. Update Vehicle Information");
            System.out.println("3. List All Vehicles");
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
                    registerVehicle(scanner);
                    break;
                case 2:
                    updateVehicle(scanner);
                    break;
                case 3:
                    listAllVehicles();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void listAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.findAllVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            System.out.println("All Vehicles:");
            for (VehicleDTO vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }

    private void registerVehicle(Scanner scanner) {
        System.out.println("Enter vehicle details:");
        System.out.print("License Plate: ");
        String licensePlate = scanner.nextLine();
        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        int year;
        try {
            System.out.print("Year: ");
            year = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid year.");
            return;
        }

        VehicleDTO vehicleDTO = new VehicleDTO(licensePlate, brand, model, type, year);
        Validation response = vehicleService.registerVehicle(vehicleDTO);
        if (response.isSuccess()) {
            System.out.println("Vehicle registered successfully.");
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }

    private void updateVehicle(Scanner scanner) {
        System.out.print("Enter the License Plate of the vehicle to update: ");
        String licensePlate = scanner.nextLine();
        System.out.print("New Brand: ");
        String brand = scanner.nextLine();
        System.out.print("New Model: ");
        String model = scanner.nextLine();
        System.out.print("New Type: ");
        String type = scanner.nextLine();
        int year;
        try {
            System.out.print("New Year: ");
            year = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid year.");
            return;
        }

        VehicleDTO vehicleDTO = vehicleService.findVehicleByLicensePlate(licensePlate);
        if (vehicleDTO == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        vehicleDTO.setBrand(brand);
        vehicleDTO.setModel(model);
        vehicleDTO.setType(type);
        vehicleDTO.setYear(year);
        Validation response = vehicleService.updateVehicle(vehicleDTO);
        if (response.isSuccess()) {
            System.out.println("Vehicle updated successfully.");
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }
}
