package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.Validation;
import com.adalocatecar.utility.ValidationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ValidationResponse registerClient(ClientDTO clientDTO) {
        Optional<Client> existingClient = clientRepository.findById(clientDTO.getId());
        if (existingClient.isPresent()) {
            return ValidationResponse.error(ValidationResponse.CLIENT_ALREADY_EXISTS);
        }

    ValidationResponse requiredFieldValidation = Validation.validateRequiredField(clientDTO.getName(), "Name");
        if (!requiredFieldValidation.isSuccess()) {
            return requiredFieldValidation;
        }

        requiredFieldValidation = Validation.validateRequiredField(clientDTO.getType(), "Type");
        if (!requiredFieldValidation.isSuccess()) {
            return requiredFieldValidation;
        }

        requiredFieldValidation = Validation.validateRequiredField(clientDTO.getId(), "ID");
        if (!requiredFieldValidation.isSuccess()) {
            return requiredFieldValidation;
        }

        ValidationResponse idFormatValidation = Validation.validateFormat(clientDTO.getId(), "\\d{11}|\\d{14}", "ID");
        if (!idFormatValidation.isSuccess()) {
            return idFormatValidation;
        }

        ValidationResponse businessRuleValidation = Validation.validateBusinessRule(clientDTO);
        if (!businessRuleValidation.isSuccess()) {
            return businessRuleValidation;
        }

        Client client = Converter.convertToEntity(clientDTO);
        clientRepository.create(client);
        return ValidationResponse.ok();
    }

    @Override
    public ValidationResponse updateClient(ClientDTO clientDTO) {
        Optional<Client> existingClient = clientRepository.findById(clientDTO.getId());
        if (existingClient.isEmpty()) {
            return ValidationResponse.error(ValidationResponse.CLIENT_NOT_FOUND);
        }

        ValidationResponse businessRuleValidation = Validation.validateBusinessRule(clientDTO);
        if (!businessRuleValidation.isSuccess()) {
            return businessRuleValidation;
        }

        Client clientToUpdate = Converter.convertToEntity(clientDTO);
        clientRepository.update(clientToUpdate);
        return ValidationResponse.ok();
    }

    @Override
    public ValidationResponse deleteClient(String id) {
        ValidationResponse requiredFieldValidation = Validation.validateRequiredField(id, "ID");
        if (!requiredFieldValidation.isSuccess()) {
            return requiredFieldValidation;
        }

        boolean removed = clientRepository.delete(id);
        return removed ? ValidationResponse.ok() : ValidationResponse.error(ValidationResponse.CLIENT_NOT_FOUND);
    }

    @Override
    public List<ClientDTO> findAllClients() {
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

        if (client.isPresent()) {
            return ValidationResponse.ok();
        } else {
            return ValidationResponse.error(ValidationResponse.CLIENT_NOT_FOUND);
        }
    }

    @Override
    public List<ClientDTO> findClientsByName(String name) {
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
}