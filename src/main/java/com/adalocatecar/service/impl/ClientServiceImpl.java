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
        String id = clientDTO.getId();
        Validation idValidation = Validation.validateRequiredField(id, "ID");
        if (!idValidation.isSuccess()) {
            return idValidation;
        }

        Validation idFormatValidation = Validation.validateCPFOrCNPJ(id);
        if (!idFormatValidation.isSuccess()) {
            return idFormatValidation;
        }

        String[] existingIds;
        try {
            existingIds = getAllClientIdsFromRepository();
        } catch (IOException e) {
            return Validation.errorCreatingClient();
        }

        Validation uniqueIdValidation = Validation.validateUniqueClientId(id, true, existingIds);
        if (!uniqueIdValidation.isSuccess()) {
            return uniqueIdValidation;
        }

        Validation nameValidation = Validation.validateName(clientDTO.getName());
        if (!nameValidation.isSuccess()) {
            return nameValidation;
        }

        clientDTO.setType(determineClientType(id));
        try {
            clientRepository.update(Converter.convertToEntity(clientDTO));
            return Validation.ok();
        } catch (Exception e) {
            return Validation.errorCreatingClient();
        }
    }

    @Override
    public Validation updateClient(ClientDTO clientDTO) {
        String id = clientDTO.getId();
        Validation idValidation = Validation.validateCPFOrCNPJ(id);
        if (!idValidation.isSuccess()) {
            return idValidation;
        }

        String[] existingIds;
        try {
            existingIds = getAllClientIdsFromRepository();
        } catch (IOException e) {
            return Validation.errorUpdatingClient();
        }

        Validation uniqueIdValidation = Validation.validateUniqueClientId(id, false, existingIds);
        if (!uniqueIdValidation.isSuccess()) {
            return uniqueIdValidation;
        }

        Validation nameValidation = Validation.validateName(clientDTO.getName());
        if (!nameValidation.isSuccess()) {
            return nameValidation;
        }

        clientDTO.setType(determineClientType(id));
        try {
            clientRepository.update(Converter.convertToEntity(clientDTO));
            return Validation.ok();
        } catch (Exception e) {
            return Validation.errorUpdatingClient();
        }
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

        boolean hasRentedCars = false;
        try {
            hasRentedCars = clientRepository.hasRentedCars(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (hasRentedCars) {
            return Validation.error(Validation.ERROR_DELETING_CLIENT);
        }

        boolean removed = clientRepository.delete(id);
        return removed ? Validation.ok() : Validation.error(Validation.CLIENT_NOT_FOUND);
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
            System.err.printf(Validation.errorFindingAllClientsMessage(e.getMessage()));
            return new ArrayList<>();
        }
    }


    @Override
    public Validation<ClientDTO> findClientById(String id) {
        Validation idValidation = Validation.validateRequiredField(id, "ID");
        if (!idValidation.isSuccess()) {
            return Validation.error(idValidation.getMessage());
        }

        Validation idFormatValidation = Validation.validateCPFOrCNPJ(id);
        if (!idFormatValidation.isSuccess()) {
            return Validation.error(idFormatValidation.getMessage());
        }

        Optional<Client> clientOptional;
        try {
            clientOptional = clientRepository.findById(id);
        } catch (IOException e) {
            return Validation.error("Error while finding client by ID: " + e.getMessage());
        }

        if (clientOptional.isPresent()) {
            ClientDTO clientDTO = Converter.convertToDTO(clientOptional.get());
            return Validation.ok(clientDTO);
        } else {
            return Validation.error(Validation.CLIENT_NOT_FOUND);
        }
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
