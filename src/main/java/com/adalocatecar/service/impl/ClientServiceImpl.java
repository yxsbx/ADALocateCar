package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.ValidationClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public String createClient(ClientDTO clientDTO) {
        Client client = Converter.convertToEntity(clientDTO);
        return saveClient(client, ValidationClient.OperationType.CREATE);
    }

    @Override
    public String updateClient(ClientDTO clientDTO) {
        Optional<Client> existingClient = clientRepository.findById(clientDTO.getId());
        if (existingClient.isEmpty()) {
            throw new RuntimeException("Client not found.");
        }
        Client client = Converter.convertToEntity(clientDTO);
        return saveClient(client, ValidationClient.OperationType.UPDATE);
    }

    @Override
    public String deleteClient(String id) {
        try {
            getAllClientIdsFromRepository();
            boolean hasRentedCars = clientRepository.hasRentedCars(id);

            String deletionResult = ValidationClient.validateDeletion(hasRentedCars);

            if (!deletionResult.equals(ValidationClient.SUCCESS_MESSAGE)) {
                return deletionResult;
            }

            boolean deleted = clientRepository.delete(id);
            if (!deleted) {
                System.out.println(ValidationClient.clientNotFound());
            }
            return "Client deleted successfully.";
        } catch (IOException e) {
            return "Failed to delete client due to an error.";
        }
    }

    @Override
    public List<ClientDTO> findAllClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOs = new ArrayList<>();
        for (Client client : clients) {
            clientDTOs.add(Converter.convertToDTO(client));
        }
        return clientDTOs;
    }

    @Override
    public ClientDTO findClientByDocument(String id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            return Converter.convertToDTO(optionalClient.get());
        } else {
            throw new RuntimeException(ValidationClient.ERROR_FINDING_CLIENTS_BY_DOCUMENT);
        }
    }

    @Override
    public List<ClientDTO> findClientsByName(String name) {
        List<Client> clients = clientRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException(ValidationClient.ERROR_FINDING_CLIENTS_BY_NAME));
        if (clients.isEmpty()) {
            throw new RuntimeException(ValidationClient.ERROR_FINDING_CLIENTS_BY_NAME);
        }
        return clients.stream()
                .map(Converter::convertToDTO)
                .collect(Collectors.toList());
    }

    private String[] getAllClientIdsFromRepository() throws IOException {
        List<Client> clients = clientRepository.findAll();
        String[] clientIds = new String[clients.size()];
        for (int i = 0; i < clients.size(); i++) {
            clientIds[i] = clients.get(i).getId();
        }
        return clientIds;
    }

    public String saveClient(Client client, ValidationClient.OperationType operationType) {
        String validateIdFormat = ValidationClient.validateClientIdFormat(client.getId());

        if (validateIdFormat != null) {
            return validateIdFormat;
        }

        String clientType = determineClientType(client.getId());
        client.setClientType(clientType);

        try {
            if (operationType == ValidationClient.OperationType.CREATE) {
                clientRepository.create(client);
            } else if (operationType == ValidationClient.OperationType.UPDATE) {
                clientRepository.update(client);
            }
            return ValidationClient.SUCCESS_MESSAGE;
        } catch (Exception e) {
            return "An error occurred while saving the client.";
        }
    }

    private String determineClientType(String id) {
        return (id.length() == 11) ? "INDIVIDUAL" : (id.length() == 14) ? "CORPORATE" : "INVALID";
    }
}