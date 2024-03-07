package com.adalocatecar.controller;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.service.VehicleService;

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

            int option = scanner.nextInt();
            scanner.nextLine();

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

    private void registerVehicle(Scanner scanner) {
        System.out.println("Enter vehicle details:");
        System.out.print("License Plate: ");
        String licensePlate = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        VehicleDTO vehicleDTO = new VehicleDTO(licensePlate, model, type, year);
        vehicleService.registerVehicle(vehicleDTO);
        System.out.println("Vehicle registered successfully.");
    }

    private void updateVehicle(Scanner scanner) {
        System.out.print("Enter the License Plate of the vehicle to update: ");
        String licensePlate = scanner.nextLine();
        System.out.print("New Model: ");
        String model = scanner.nextLine();
        System.out.print("New Type: ");
        String type = scanner.nextLine();
        System.out.print("New Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        VehicleDTO vehicleDTO = vehicleService.findVehicleByLicensePlate(licensePlate);
        if (vehicleDTO == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        vehicleDTO.setModel(model);
        vehicleDTO.setType(type);
        vehicleDTO.setYear(year);
        vehicleService.updateVehicle(vehicleDTO);
        System.out.println("Vehicle updated successfully.");
    }

    private void listAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.findAllVehicles();
        vehicles.forEach(System.out::println);
    }
}