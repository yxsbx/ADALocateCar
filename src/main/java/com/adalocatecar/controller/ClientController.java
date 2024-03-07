package com.adalocatecar.controller;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.service.ClientService;
import java.util.List;
import java.util.Scanner;

public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public void manageClients(Scanner scanner) {
        while (true) {
            System.out.println("\nClient Management");
            System.out.println("1. Register Client");
            System.out.println("2. Update Client Information");
            System.out.println("3. Delete Client");
            System.out.println("4. List All Clients");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    registerClient(scanner);
                    break;
                case 2:
                    updateClient(scanner);
                    break;
                case 3:
                    deleteClient(scanner);
                    break;
                case 4:
                    listClients();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void registerClient(Scanner scanner) {
        System.out.println("Enter client details to register:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Type (Individual or Corporate): ");
        String type = scanner.nextLine();
        System.out.print("ID (CPF or CNPJ): ");
        String id = scanner.nextLine();

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(name);
        clientDTO.setType(type);
        clientDTO.setId(id);
        clientService.registerClient(clientDTO);
        System.out.println("Client registered successfully.");
    }

    public void updateClient(Scanner scanner) {
        try {
            System.out.println("Enter the ID (CPF/CNPJ) of the client to update:");
            String id = scanner.nextLine();

            System.out.println("Enter new name (leave blank to not change):");
            String name = scanner.nextLine();
            System.out.println("Enter new type (Individual or Corporate, leave blank to not change):");
            String type = scanner.nextLine();

            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(id);
            clientDTO.setName(name.isEmpty() ? null : name);
            clientDTO.setType(type.isEmpty() ? null : type);
            clientService.updateClient(clientDTO);
            System.out.println("Client updated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to update client: " + e.getMessage());
        }
    }

    private void deleteClient(Scanner scanner) {
        System.out.println("Enter client ID to delete:");
        String id = scanner.nextLine();
        clientService.deleteClient(id);
        System.out.println("Client deleted successfully.");
    }

    private void listClients() {
        List<ClientDTO> clients = clientService.findAllClients();
        clients.forEach(client -> System.out.println(client.toString()));
    }
}