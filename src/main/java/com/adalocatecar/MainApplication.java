package com.adalocatecar;

import com.adalocatecar.controller.VehicleController;
import com.adalocatecar.controller.ClientController;
import com.adalocatecar.controller.RentalController;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.repository.VehicleRepository;
import com.adalocatecar.repository.impl.ClientRepositoryImpl;
import com.adalocatecar.repository.impl.VehicleRepositoryImpl;
import com.adalocatecar.service.impl.VehicleServiceImpl;
import com.adalocatecar.service.impl.ClientServiceImpl;
import com.adalocatecar.service.impl.RentalServiceImpl;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.service.RentalService;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication {
    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    public static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepositoryImpl();
        VehicleRepository vehicleRepository = new VehicleRepositoryImpl();

        
        ClientService clientService = new ClientServiceImpl(clientRepository);
        VehicleService vehicleService = new VehicleServiceImpl(vehicleRepository);
        RentalService rentalService = new RentalServiceImpl(clientService, vehicleService);


        VehicleController vehicleController = new VehicleController(vehicleService);
        ClientController clientController = new ClientController(clientService);
        RentalController rentalController = new RentalController(rentalService);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\nWelcome to ADA LocateCar System");
                System.out.println("1. Manage Vehicles");
                System.out.println("2. Manage Clients");
                System.out.println("3. Manage Rentals");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        vehicleController.manageVehicles(scanner);
                        break;
                    case "2":
                        clientController.manageClients(scanner);
                        break;
                    case "3":
                        rentalController.manageRentals(scanner);
                        break;
                    case "4":
                        System.out.println("Thank you for using ADA LocateCar System. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred: " + e.getMessage(), e);
            System.out.println("An unexpected error occurred. Please check the log for details.");
        }
    }
}