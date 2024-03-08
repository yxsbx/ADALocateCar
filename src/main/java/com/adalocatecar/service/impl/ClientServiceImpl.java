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
        try {
            String id = clientDTO.getId();
            String[] existingIds = getAllClientIdsFromRepository();
            if (!Validation.validateRequiredField(id, "ID").isSuccess()) {
                return Validation.error("ID is required");
            }
            if (!Validation.isValidClientId(id)) {
                return Validation.error(Validation.INVALID_ID_FORMAT);
            }
            if (!Validation.isUniqueClientId(id, existingIds)) {
                return Validation.error("Client with this ID already exists.");
            }
            if (!Validation.isValidName(clientDTO.getName())) {
                return Validation.error(Validation.INVALID_NAME_FORMAT);
            }

            clientDTO.setType(determineClientType(id));
            clientRepository.create(Converter.convertToEntity(clientDTO));

            return Validation.ok();
        } catch (IOException e) {
            return Validation.errorCreatingClient();
        }
    }

    @Override
    public Validation updateClient(ClientDTO clientDTO) {
        try {
            String id = clientDTO.getId();
            String[] existingIds = getAllClientIdsFromRepository();
            if (!Validation.isValidClientId(id)) {
                return Validation.error(Validation.INVALID_ID_FORMAT);
            }
            if (!Validation.isUniqueClientId(id, existingIds)) {
                return Validation.error(Validation.CLIENT_ALREADY_EXISTS);
            }
            if (!Validation.isValidName(clientDTO.getName())) {
                return Validation.error(Validation.INVALID_NAME_FORMAT);
            }

            clientDTO.setType(determineClientType(id));
            clientRepository.update(Converter.convertToEntity(clientDTO));

            return Validation.ok();
        } catch (IOException e) {
            return Validation.errorUpdatingClient();
        }
    }

    @Override
    public Validation deleteClient(String id) {
        if (!Validation.isRequired(id)) {
            return Validation.error(Validation.REQUIRED_FIELD_ERROR, "ID");
        }
        if (!Validation.isValidClientId(id)) {
            return Validation.error(Validation.INVALID_ID_FORMAT);
        }

        boolean hasRentedCars = clientRepository.hasRentedCars(id);
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
                ClientDTO clientDTO = Converter.convertToDTO(client);
                clientDTOs.add(clientDTO);
            }
            return clientDTOs;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public ClientDTO findClientById(String id) {
        if (!Validation.isRequired(id)) {
            throw new IllegalArgumentException("ID is required");
        }
        if (!Validation.isValidClientId(id)) {
            throw new IllegalArgumentException("Invalid ID format");
        }

        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return Converter.convertToDTO(client.get());
        } else {
            return null;
        }
    }

    @Override
    public List<ClientDTO> findClientsByName(String name) {
        if (!Validation.isRequired(name)) {
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
            System.err.println(String.format(Validation.ERROR_FINDING_CLIENTS_BY_NAME, e.getMessage()));
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