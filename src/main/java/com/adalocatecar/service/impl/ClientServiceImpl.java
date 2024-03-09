package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.Validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Validation createClient(ClientDTO clientDTO) {
        return saveClient(clientDTO, true);
    }

    @Override
    public Validation updateClient(ClientDTO clientDTO) {
        return saveClient(clientDTO, false);
    }

    @Override
    public Validation deleteClient(String id) {
        Validation idValidation = Validation.validateRequiredField(id, "ID");
        if (!idValidation.isSuccess()) {
            return idValidation;
        }

        Validation idFormatValidation = Validation.validateCPFOrCNPJ(id);
        if (!idFormatValidation.isSuccess()) {
            return idFormatValidation;
        }

        boolean hasRentedCars;
        try {
            hasRentedCars = clientRepository.hasRentedCars(id);
        } catch (IOException e) {
            return Validation.errorDeletingClient();
        }

        if (hasRentedCars) {
            return Validation.error("Cannot delete client with rented cars");
        }

        boolean removed = clientRepository.delete(id);
        return removed ? Validation.ok("Vehicle rented successfully.") : Validation.error(Validation.CLIENT_NOT_FOUND);
    }

    @Override
    public List<ClientDTO> findAllClients() {
        try {
            List<ClientDTO> clientDTOs = new ArrayList<>();
            List<Client> clients = clientRepository.findAll();
            for (Client client : clients) {
                clientDTOs.add(Converter.convertToDTO(client));
            }
            return clientDTOs;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public ClientDTO findClientById(String id) {
        Validation idValidation = Validation.validateRequiredField(id, "ID");
        if (!idValidation.isSuccess()) {
            throw new IllegalArgumentException("ID is required");
        }

        Validation idFormatValidation = Validation.validateCPFOrCNPJ(id);
        if (!idFormatValidation.isSuccess()) {
            throw new IllegalArgumentException("Invalid ID format");
        }

        Optional<Client> client = clientRepository.findById(id);
        return client.map(Converter::convertToDTO).orElse(null);
    }

    @Override
    public List<ClientDTO> findClientsByName(String name) {
        Validation nameValidation = Validation.validateRequiredField(name, "Name");
        if (!nameValidation.isSuccess()) {
            return new ArrayList<>();
        }

        List<ClientDTO> matchingClients = new ArrayList<>();
        try {
            List<Client> clients = clientRepository.findAll();
            for (Client client : clients) {
                if (client.getName().equalsIgnoreCase(name)) {
                    matchingClients.add(Converter.convertToDTO(client));
                }
            }
        } catch (IOException e) {
            System.err.printf((Validation.ERROR_FINDING_CLIENTS_BY_NAME) + "%n", e.getMessage());
            return new ArrayList<>();
        }
        return matchingClients;
    }

    private Validation saveClient(ClientDTO clientDTO, boolean isNewClient) {
        String id = clientDTO.getId();
        Validation idValidation = Validation.validateRequiredField(id, "ID");
        if (!idValidation.isSuccess()) {
            return idValidation;
        }

        Validation idFormatValidation = Validation.validateCPFOrCNPJ(id);
        if (!idFormatValidation.isSuccess()) {
            return idFormatValidation;
        }

        if (Validation.isValidCPFOrCNPJ(id)) {
            return Validation.error("Invalid CPF or CNPJ");
        }

        String[] existingIds;
        try {
            existingIds = getAllClientIdsFromRepository();
        } catch (IOException e) {
            return isNewClient ? Validation.errorCreatingClient() : Validation.errorUpdatingClient();
        }

        Validation uniqueIdValidation = Validation.validateUniqueClientId(id, isNewClient, existingIds);
        if (!uniqueIdValidation.isSuccess()) {
            return uniqueIdValidation;
        }

        Validation nameValidation = Validation.validateName(clientDTO.getName());
        if (!nameValidation.isSuccess()) {
            return nameValidation;
        }

        String clientType = determineClientType(id);
        clientDTO.setType(clientType);

        try {
            clientRepository.update(Converter.convertToEntity(clientDTO));
            return Validation.ok("Vehicle rented successfully.");
        } catch (Exception e) {
            return isNewClient ? Validation.errorCreatingClient() : Validation.errorUpdatingClient();
        }
    }

    private String[] getAllClientIdsFromRepository() throws IOException {
        List<Client> clients = clientRepository.findAll();
        List<String> ids = new ArrayList<>();
        for (Client client : clients) {
            ids.add(client.getId());
        }
        return ids.toArray(new String[0]);
    }

    private String determineClientType(String id) {
        return (id.length() == 11) ? "Individual" : "Corporate";
    }
}
