package com.adalocatecar.controller;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.utility.ValidationClient;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public void manageClients(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\nClient Management Menu:");
            System.out.println("1. Create Client");
            System.out.println("2. Read Client list");
            System.out.println("3. Update client");
            System.out.println("4. Delete Client");
            System.out.println("5. Find client by document or name");
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
                    createClient(scanner);
                    break;
                case 2:
                    readAllClients();
                    break;
                case 3:
                    updateClient(scanner);
                    break;
                case 4:
                    deleteClient(scanner);
                    break;
                case 5:
                    readClient(scanner);
                    break;
                case 0:
                    System.out.println("Returning to Main Menu.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public void createClient(Scanner scanner) {
        System.out.println("Enter client name:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter client document:");
        String id = scanner.nextLine().trim();

        String validationMessage = ValidationClient.validateClientNameFormat(name);
        if (validationMessage != null) {
            System.err.println("Error: " + validationMessage);
            return;
        }

        validationMessage = ValidationClient.validateClientIdFormat(id);

        if (validationMessage != null) {
            System.err.println("Error: " + validationMessage);
            return;
        }

        ClientDTO newClient = new ClientDTO( id, name,null);
        System.out.println(clientService.createClient(newClient));

    }

    private void readAllClients() {
        try {
            List<ClientDTO> clients = clientService.findAllClients();
            System.out.println("All Clients:");
            for (ClientDTO client : clients) {
                System.out.println(client);
            }
        } catch (Exception e) {
            System.out.println("Failed to display clients: " + e.getMessage());
        }
    }

    private void readClient(Scanner scanner) {
        System.out.println("Enter client document or name to search:");
        String query = scanner.nextLine().trim();

        if (ValidationClient.validateClientIdFormat(query) == null) {
            Optional<ClientDTO> client = Optional.ofNullable(clientService.findClientByDocument(query));
            if (client.isPresent()) {
                System.out.println("Client found with the provided document:");
                System.out.println(client.get());
            } else {
                System.out.println(ValidationClient.clientNotFound());
            }
        } else {
            try {
                List<ClientDTO> clients = clientService.findClientsByName(query);
                if (!clients.isEmpty()) {
                    System.out.println("Clients found with the provided name:");
                    for (ClientDTO client : clients) {
                        System.out.println(client);
                    }
                } else {
                    System.out.println(ValidationClient.clientNotFound());
                }
            } catch (IOException e) {
                System.out.println("Error occurred while searching for clients by name: " + e.getMessage());
            }
        }
    }

    public void updateClient(Scanner scanner) {
        System.out.println("Enter client name:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter client ID (CPF or CNPJ):");
        String id = scanner.nextLine().trim();

        String validationMessage = ValidationClient.validateClientNameFormat(name);
        if (validationMessage != null) {
            System.out.println("Error: " + validationMessage);
            return;
        }

        validationMessage = ValidationClient.validateClientIdFormat(id);
        if (validationMessage != null) {
            System.out.println("Error: " + validationMessage);
            return;
        }

        ClientDTO editedClient = new ClientDTO(name, id, null);
        clientService.updateClient(editedClient);
    }

    public void deleteClient(Scanner scanner) {
        System.out.println("Enter client ID (CPF or CNPJ):");
        String id = scanner.nextLine().trim();

        String validationMessage = ValidationClient.validateClientIdFormat(id);
        if (validationMessage != null) {
            System.out.println("Error: " + validationMessage);
            return;
        }

        clientService.deleteClient(id);
    }
}
