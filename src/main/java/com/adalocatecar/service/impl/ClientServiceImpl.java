package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.Validation;
import com.adalocatecar.utility.ValidationMessages;

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
    public ValidationMessages createClient(ClientDTO clientDTO) {
        return processClientUpdate(clientDTO, true);
    }

    @Override
    public ValidationMessages updateClient(ClientDTO clientDTO) {
        return processClientUpdate(clientDTO, false);
    }

    @Override
    public ValidationMessages deleteClient(String id) {
        ValidationMessages requiredFieldValidation = Validation.validateRequiredField(id, "ID");
        if (!requiredFieldValidation.isSuccess()) {
            return requiredFieldValidation;
        }

        ValidationMessages idValidation = Validation.validateCPFOrCNPJ(id);
        if (!idValidation.isSuccess()) {
            return idValidation;
        }

        boolean hasRentedCars = clientRepository.hasRentedCars(id);
        if (hasRentedCars) {
            return ValidationMessages.error("The client cannot be deleted because they have rented cars.");
        }

        boolean removed = clientRepository.delete(id);
        return removed ? ValidationMessages.ok() : ValidationMessages.error(ValidationMessages.CLIENT_NOT_FOUND);
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
    public ValidationMessages findClientById(String id) {
        ValidationMessages requiredFieldValidation = Validation.validateRequiredField(id, "ID");
        if (!requiredFieldValidation.isSuccess()) {
            return requiredFieldValidation;
        }

        Optional<Client> client = clientRepository.findById(id);
        return client.map(c -> ValidationMessages.ok())
                .orElse(ValidationMessages.error(ValidationMessages.CLIENT_NOT_FOUND));
    }

    @Override
    public List<ClientDTO> findClientsByName(String name) throws IOException {
        ValidationMessages requiredFieldValidation = Validation.validateRequiredField(name, "Name");
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

    private ValidationMessages processClientUpdate(ClientDTO clientDTO, boolean isNewClient) {
        ValidationMessages nameValidation = Validation.validateName(clientDTO.getName());
        if (!nameValidation.isSuccess()) {
            return nameValidation;
        }

        ValidationMessages idValidation = Validation.validateCPFOrCNPJ(clientDTO.getId());
        if (!idValidation.isSuccess()) {
            return idValidation;
        }

        if (!isNewClient) {
            Optional<Client> existingClient = clientRepository.findById(clientDTO.getId());
            if (existingClient.isEmpty()) {
                return ValidationMessages.error(ValidationMessages.CLIENT_NOT_FOUND);
            }
        }

        try {
            String[] existingIds = getAllClientIdsFromRepository();
            ValidationMessages uniqueFieldValidation = Validation.validateUniqueField(clientDTO.getId(), existingIds, "ID");
            if (!uniqueFieldValidation.isSuccess()) {
                return uniqueFieldValidation;
            }
        } catch (IOException e) {
            return ValidationMessages.error("An error occurred while retrieving existing client IDs.");
        }

        String type = (clientDTO.getId().length() == 11) ? "Individual" : "Corporate";
        clientDTO.setType(type);
        Client clientToUpdate = Converter.convertToEntity(clientDTO);
        if (isNewClient) {
            clientRepository.create(clientToUpdate);
        } else {
            clientRepository.update(clientToUpdate);
        }

        return ValidationMessages.ok();
    }
}