package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.Validation;
import com.adalocatecar.utility.ValidationResponse;

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
    public ValidationResponse createClient(ClientDTO clientDTO) {
        return processClientUpdate(clientDTO, true);
    }

    @Override
    public ValidationResponse updateClient(ClientDTO clientDTO) {
        return processClientUpdate(clientDTO, false);
    }

    @Override
    public ValidationResponse deleteClient(String id) {
        ValidationResponse requiredFieldValidation = Validation.validateRequiredField(id, "ID");
        if (!requiredFieldValidation.isSuccess()) {
            return requiredFieldValidation;
        }

        ValidationResponse idValidation = Validation.validateCPFOrCNPJ(id);
        if (!idValidation.isSuccess()) {
            return idValidation;
        }

        boolean hasRentedCars = clientRepository.hasRentedCars(id);
        if (hasRentedCars) {
            return ValidationResponse.error("The client cannot be deleted because they have rented cars.");
        }

        boolean removed = clientRepository.delete(id);
        return removed ? ValidationResponse.ok() : ValidationResponse.error(ValidationResponse.CLIENT_NOT_FOUND);
    }

    @Override
    public List<ClientDTO> findAllClients() throws IOException {
        List<ClientDTO> clientDTOs = new ArrayList<>();
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            ClientDTO clientDTO = Converter.convertToDTO(client);
            clientDTOs.add(clientDTO);
        }
        return clientDTOs;
    }

    @Override
    public ValidationResponse findClientById(String id) {
        ValidationResponse requiredFieldValidation = Validation.validateRequiredField(id, "ID");
        if (!requiredFieldValidation.isSuccess()) {
            return requiredFieldValidation;
        }

        Optional<Client> client = clientRepository.findById(id);
        return client.map(c -> ValidationResponse.ok())
                .orElse(ValidationResponse.error(ValidationResponse.CLIENT_NOT_FOUND));
    }

    @Override
    public List<ClientDTO> findClientsByName(String name) throws IOException {
        ValidationResponse requiredFieldValidation = Validation.validateRequiredField(name, "Name");
        if (!requiredFieldValidation.isSuccess()) {
            return new ArrayList<>();
        }

        List<ClientDTO> matchingClients = new ArrayList<>();
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            if (client.getName().equalsIgnoreCase(name)) {
                matchingClients.add(Converter.convertToDTO(client));
            }
        }
        return matchingClients;
    }

    private String[] getAllClientIdsFromRepository() throws IOException {
        List<Client> clients = clientRepository.findAll();
        List<String> ids = new ArrayList<>();
        for (Client client : clients) {
            ids.add(client.getId());
        }
        return ids.toArray(new String[0]);
    }

    private ValidationResponse processClientUpdate(ClientDTO clientDTO, boolean isNewClient) {
        ValidationResponse nameValidation = Validation.validateName(clientDTO.getName());
        if (!nameValidation.isSuccess()) {
            return nameValidation;
        }

        ValidationResponse idValidation = Validation.validateCPFOrCNPJ(clientDTO.getId());
        if (!idValidation.isSuccess()) {
            return idValidation;
        }

        if (!isNewClient) {
            Optional<Client> existingClient = clientRepository.findById(clientDTO.getId());
            if (existingClient.isEmpty()) {
                return ValidationResponse.error(ValidationResponse.CLIENT_NOT_FOUND);
            }
        }

        try {
            String[] existingIds = getAllClientIdsFromRepository();
            ValidationResponse uniqueFieldValidation = Validation.validateUniqueField(clientDTO.getId(), existingIds, "ID");
            if (!uniqueFieldValidation.isSuccess()) {
                return uniqueFieldValidation;
            }
        } catch (IOException e) {
            return ValidationResponse.error("An error occurred while retrieving existing client IDs.");
        }

        String type = (clientDTO.getId().length() == 11) ? "Individual" : "Corporate";
        clientDTO.setType(type);
        Client clientToUpdate = Converter.convertToEntity(clientDTO);
        if (isNewClient) {
            clientRepository.create(clientToUpdate);
        } else {
            clientRepository.update(clientToUpdate);
        }

        return ValidationResponse.ok();
    }
}