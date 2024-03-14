package com.adalocatecar.controller;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.utility.ValidationVehicle;

import java.io.IOException;
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
                    updateVehicle(scanner);
                    break;
                case 3:
                    readAllVehicles();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void createVehicle(Scanner scanner) {
        saveVehicle(scanner, false);
    }

    private void readAllVehicles() {
        try {
            List<VehicleDTO> vehicles = vehicleService.findAllVehicles();
            if (vehicles.isEmpty()) {
                System.out.println("No vehicles found.");
            } else {
                System.out.println("All Vehicles:");
                for (VehicleDTO vehicle : vehicles) {
                    System.out.println(vehicle);
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while retrieving vehicles: " + e.getMessage());
        }
    }

    private void updateVehicle(Scanner scanner) {
        saveVehicle(scanner, true);
    }

    private void saveVehicle(Scanner scanner, boolean isUpdate) {
        System.out.println("Enter vehicle details:");

        System.out.print("License Plate EX:(ABC1D23): ");
        String licensePlate = scanner.nextLine();

        String validationMessage = ValidationVehicle.validateLicensePlate(licensePlate);
        if (!validationMessage.isEmpty()) {
            System.out.println("Error: " + validationMessage);
            return;
        }

        System.out.print("Model: ");
        String model = scanner.nextLine();

        validationMessage = ValidationVehicle.validateModel(model);
        if (!validationMessage.isEmpty()) {
            System.out.println("Error: " + validationMessage);
            return;
        }

        System.out.print("Type (SMALL, MEDIUM, SUV): ");
        String type = scanner.nextLine();

        validationMessage = ValidationVehicle.validateType(type);
        if (!validationMessage.isEmpty()) {
            System.out.println("Error: " + validationMessage);
            return;
        }

        if (!isUpdate) {
            VehicleDTO newVehicle = new VehicleDTO(licensePlate, model, type, null);

            try {
                String response;
                response = vehicleService.createVehicle(newVehicle);
                if (response.equals(ValidationVehicle.SUCCESS_MESSAGE)) {
                    System.out.println("Vehicle registered successfully.");
                } else {
                    System.out.println("Error: " + response);
                }
            } catch (IOException e) {
                System.out.println("Error occurred while saving vehicle: " + e.getMessage());
            }
        } else {

            try {
               VehicleDTO vehicleDTO = vehicleService.findVehicleByLicensePlate(licensePlate);

                if (vehicleDTO != null && !vehicleDTO.isAvailable()) {
                    System.out.println("Vehicle is not available for update because it is currently rented.");
                }else{
                    vehicleDTO.setModel(model);
                    vehicleDTO.setType(type);
                    vehicleService.updateVehicle(vehicleDTO);
                }
            }catch(RuntimeException | IOException e){
                System.out.println("Error: " + e.getMessage());
            }

        }
    }
}