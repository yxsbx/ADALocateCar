package com.adalocatecar.service.impl;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.service.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final List<ClientDTO> clients = new ArrayList<>();

    @Override
    public void registerClient(ClientDTO clientDTO) {
        System.out.println("Client registered successfully.");
    }

    @Override
    public void updateClient(ClientDTO clientDTO) {
    }

    @Override
    public void deleteClient(String id) {
        System.out.println("Client with ID " + id + " deleted successfully.");
    }

    @Override
    public List<ClientDTO> findAllClients() {
        return new ArrayList<>(clients);
    }

    @Override
    public ClientDTO findClientById(String id) {
        return null;
    }

    @Override
    public List<ClientDTO> findClientsByName(String name) {
        return null;
    }
}