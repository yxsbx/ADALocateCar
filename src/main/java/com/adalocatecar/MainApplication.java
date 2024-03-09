package com.adalocatecar;

import com.adalocatecar.controller.VehicleController;
import com.adalocatecar.controller.ClientController;
import com.adalocatecar.repository.impl.ClientRepositoryImpl;
import com.adalocatecar.repository.impl.VehicleRepositoryImpl;
import com.adalocatecar.service.impl.VehicleServiceImpl;
import com.adalocatecar.service.impl.ClientServiceImpl;
import com.adalocatecar.service.VehicleService;
import com.adalocatecar.service.ClientService;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication {
    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
            ClientService clientService = new ClientServiceImpl(clientRepository);

            VehicleRepositoryImpl vehicleRepository = new VehicleRepositoryImpl();
            VehicleService vehicleService = new VehicleServiceImpl();

            VehicleController vehicleController = new VehicleController(vehicleService);
            ClientController clientController = new ClientController(clientService);

            boolean running = true;
            while (running) {
                System.out.println("\nWelcome to ADA LocateCar System");
                System.out.println("1. Manage Vehicles");
                System.out.println("2. Manage Clients");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        vehicleController.manageVehicles(scanner);
                        break;
                    case 2:
                        clientController.manageClients(scanner);
                        break;
                    case 3:
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