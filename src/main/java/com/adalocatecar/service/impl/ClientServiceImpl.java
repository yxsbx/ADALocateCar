package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.service.ClientService;
import com.adalocatecar.utility.Converter;
import com.adalocatecar.utility.ValidationInput;

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
    public void createClient(ClientDTO clientDTO) {
        Client client = Converter.convertToEntity(clientDTO);
        saveClient(client, "CREATE");
    }

    @Override
    public List<ClientDTO> readAllClients() {
        List<Client> clients = clientRepository.readAll();
        List<ClientDTO> clientDTOs = new ArrayList<>();
        for (Client client : clients) {
            clientDTOs.add(Converter.convertToDTO(client));
        }
        return clientDTOs;
    }

    @Override
    public void updateClient(ClientDTO clientDTO) {
        Optional<Client> existingClient = clientRepository.searchById(clientDTO.getId());
        if (existingClient.isEmpty()) {
            throw new RuntimeException("Client not found.");
        }
        Client client = Converter.convertToEntity(clientDTO);
        saveClient(client, "UPDATE");
    }

    @Override
    public String deleteClient(ClientDTO clientToDelete) {
        if (!clientToDelete.getRentedVehiclesPlates().isEmpty()) {
            throw new RuntimeException(ValidationInput.ERROR_HAS_RENTED);
        }
        clientRepository.delete(Converter.convertToEntity(clientToDelete));
        return ValidationInput.CLIENT_DELETED;
    }

    @Override
    public ClientDTO searchClientByDocument(String id) {
        Optional<Client> optionalClient = clientRepository.searchById(id);

        if (optionalClient.isPresent()) {
            return Converter.convertToDTO(optionalClient.get());
        } else {
            throw new RuntimeException(ValidationInput.CLIENT_NOT_FOUND);
        }
    }

    @Override
    public List<ClientDTO> searchClientsByName(String name) {
        List<Client> clients = clientRepository.searchByName(name)
                .orElseThrow(() -> new RuntimeException(ValidationInput.CLIENT_NOT_FOUND));
        if (clients.isEmpty()) {
            throw new RuntimeException(ValidationInput.CLIENT_NOT_FOUND);
        }
        return clients.stream()
                .map(Converter::convertToDTO)
                .collect(Collectors.toList());
    }

    private void saveClient(Client client, String operationType) {
        if (!ValidationInput.isValidClientId(client.getId())) {
            throw new RuntimeException(ValidationInput.INVALID_ID_FORMAT);
        }

        if (!ValidationInput.isValidClientName(client.getName())) {
            throw new RuntimeException(ValidationInput.INVALID_NAME_FORMAT);
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

    private String determineClientType(String id) {
        return (id.length() == 11) ? "INDIVIDUAL" : (id.length() == 14) ? "CORPORATE" : "INVALID";
    }
}