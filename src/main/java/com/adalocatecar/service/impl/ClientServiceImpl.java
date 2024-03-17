package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.ValidationInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the ClientService interface that handles client-related operations.
 */

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    /**
     * Constructor for ClientServiceImpl.
     *
     * @param clientRepository The client repository to be used.
     */

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Creates a new client.
     *
     * @param clientDTO The client DTO containing the client information.
     */

    @Override
    public void createClient(ClientDTO clientDTO) {
        Client client = Converter.convertToEntity(clientDTO);
        saveClient(client, "CREATE");
    }

    /**
     * Retrieves all clients.
     *
     * @return A list of all clients.
     */

    @Override
    public List<ClientDTO> readAllClients() {
        List<Client> clients = clientRepository.readAll();
        List<ClientDTO> clientDTOs = new ArrayList<>();
        for (Client client : clients) {
            clientDTOs.add(Converter.convertToDTO(client));
        }
        return clientDTOs;
    }

    /**
     * Updates an existing client.
     *
     * @param clientDTO The client DTO containing the updated client information.
     */

    @Override
    public void updateClient(ClientDTO clientDTO) {
        Optional<Client> existingClient = clientRepository.searchById(clientDTO.getId());
        if (existingClient.isEmpty()) {
            throw new IllegalArgumentException(ValidationInput.CLIENT_NOT_FOUND);
        }
        Client client = Converter.convertToEntity(clientDTO);
        saveClient(client, "UPDATE");
    }

    /**
     * Deletes a client.
     *
     * @param clientToDelete The client DTO to be deleted.
     * @return A message confirming the deletion of the client.
     */

    @Override
    public String deleteClient(ClientDTO clientToDelete) {
        if (!clientToDelete.getRentedVehiclesPlates().isEmpty()) {
            throw new IllegalArgumentException(ValidationInput.ERROR_HAS_RENTED);
        }
        clientRepository.delete(Converter.convertToEntity(clientToDelete));
        return ValidationInput.CLIENT_DELETED;
    }

    /**
     * Searches for a client by document ID.
     *
     * @param id The document ID of the client to search for.
     * @return The client DTO if found.
     * @throws RuntimeException If the client is not found.
     */

    @Override
    public ClientDTO searchClientById(String id) {
        Optional<Client> optionalClient = clientRepository.searchById(id);

        if (optionalClient.isPresent()) {
            return Converter.convertToDTO(optionalClient.get());
        } else {
            throw new IllegalArgumentException(ValidationInput.CLIENT_NOT_FOUND);
        }
    }

    /**
     * Searches for clients by name.
     *
     * @param name The name of the clients to search for.
     * @return A list of client DTOs with matching names.
     * @throws RuntimeException If no clients are found with the specified name.
     */

    @Override
    public List<ClientDTO> searchClientsByName(String name) {
        List<Client> clients = clientRepository.searchByName(name)
                .orElseThrow(() -> new IllegalArgumentException(ValidationInput.CLIENT_NOT_FOUND));
        if (clients.isEmpty()) {
            throw new IllegalArgumentException(ValidationInput.CLIENT_NOT_FOUND);
        }
        return clients.stream()
                .map(Converter::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Saves or updates a client based on the operation type.
     *
     * @param client       The client object to be saved or updated.
     * @param operationType The type of operation to perform (CREATE or UPDATE).
     * @throws RuntimeException If the client ID or name is invalid, or if an operation type other than CREATE or UPDATE is provided.
     */

    private void saveClient(Client client, String operationType) {
        if (!ValidationInput.isValidClientId(client.getId())) {
            throw new IllegalArgumentException(ValidationInput.INVALID_ID_FORMAT);
        }

        if (!ValidationInput.isValidClientName(client.getName())) {
            throw new IllegalArgumentException(ValidationInput.INVALID_NAME_FORMAT);
        }

        String clientType = determineClientType(client.getId());
        client.setClientType(clientType);

        if ("CREATE".equals(operationType)) {
            clientRepository.create(client);
            System.out.println(ValidationInput.CLIENT_CREATED);
        } else {
            clientRepository.update(client);
            System.out.println(ValidationInput.CLIENT_UPDATED);
        }
    }

    /**
     * Determines the client type based on the length of the client ID.
     *
     * @param id The client ID to determine the type for.
     * @return The client type (INDIVIDUAL, CORPORATE, or INVALID).
     */

    private String determineClientType(String id) {
        return (id.length() == 11) ? "INDIVIDUAL" : (id.length() == 14) ? "CORPORATE" : "INVALID";
    }
}