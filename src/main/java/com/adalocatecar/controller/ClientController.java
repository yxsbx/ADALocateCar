package com.adalocatecar.controller;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.utility.ValidationInput;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Handles the interactions and operations related to client management.
 */

public class ClientController {
    private final ClientService clientService;

    /**
     * Constructs a ClientController with a given ClientService.
     * @param clientService The service that will be used to manage client data.
     */

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Displays the client management menu and processes user input to perform actions.
     * @param scanner The scanner to read user input.
     */

    public void manageClients(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\nClient Management Menu:");
            System.out.println("1. Create Client");
            System.out.println("2. Read Client list");
            System.out.println("3. Update client");
            System.out.println("4. Delete Client");
            System.out.println("5. Search client by document or name");
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
                    searchClient(scanner);
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

    /**
     * Creates a new client with details provided by the user.
     * @param scanner The scanner to read user input.
     */

    public void createClient(Scanner scanner) {
        String name, clientId;
        do {
            System.out.println("Enter client name:");
            name = scanner.nextLine().trim();
        } while (!ValidationInput.isValidClientName(name));

        do {
            System.out.println("Enter client document:");
            clientId = scanner.nextLine().trim();
        } while (!ValidationInput.isValidClientId(clientId));

        ClientDTO newClient = new ClientDTO(clientId, name, null);
        try {
            clientService.createClient(newClient);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while saving the client.");
        }
    }

    /**
     * Reads and displays a list of all clients.
     */

    private void readAllClients() {
        try {
            List<ClientDTO> clients = clientService.readAllClients();
            System.out.println("\nAll Clients:");
            for (ClientDTO client : clients) {
                System.out.println(client);
            }
        } catch (Exception e) {
            System.out.println("Failed to display clients: " + e.getMessage());
        }
    }

    /**
     * Updates an existing client with details provided by the user.
     * @param scanner The scanner to read user input.
     */

    public void updateClient(Scanner scanner) {
        String name, clientId;
        readAllClients();

        do {
            System.out.println("\nEnter client document (CNPJ/CPF) who you want to edit:");
            clientId = scanner.nextLine().trim();
        } while (!ValidationInput.isValidClientId(clientId));

        try {
            clientService.searchClientById(clientId);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }

        do {
            System.out.println("Enter client new name:");
            name = scanner.nextLine().trim();
        } while (!ValidationInput.isValidClientName(name));


        ClientDTO editedClient = new ClientDTO(clientId, name, null);
        try {
            clientService.updateClient(editedClient);
        } catch (RuntimeException e) {
            System.out.println("Error while updating client information:" + e.getMessage());
        }
    }

    /**
     * Deletes a client based on the document ID provided by the user.
     * @param scanner The scanner to read user input.
     */

    public void deleteClient(Scanner scanner) {
        String clientId;
        readAllClients();

        do {
            System.out.println("Enter client document (CPF or CNPJ):");
            clientId = scanner.nextLine().trim();
        } while (!ValidationInput.isValidClientId(clientId));

        try {
            ClientDTO clientToDelete = clientService.searchClientById(clientId);
            System.out.println(clientService.deleteClient(clientToDelete));
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Searches for clients based on a query provided by the user.
     * @param scanner The scanner to read user input.
     */

    private void searchClient(Scanner scanner) {
        String query, searchType;

        do {
            System.out.println("Enter client document or name to search:");
            query = scanner.nextLine().trim();
            searchType = ValidationInput.validateQuerySearchType(query);
        } while (searchType == null);

        try {
            if (searchType.equals("document")) {
                ClientDTO client = clientService.searchClientById(query);
                System.out.println("Client found with the provided document:");
                System.out.println(client);
            } else {
                List<ClientDTO> clients = clientService.searchClientsByName(query);
                System.out.println("Clients found with the provided name:");
                for (ClientDTO client : clients) {
                    System.out.println(client);
                }
            }
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage() + query);
        }
    }
}
