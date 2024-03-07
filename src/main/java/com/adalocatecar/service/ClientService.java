package com.adalocatecar.service;

import com.adalocatecar.dto.ClientDTO;

import java.util.List;

public interface ClientService {
    void registerClient(ClientDTO clientDTO);
    void updateClient(ClientDTO clientDTO);
    void deleteClient(String id);
    List<ClientDTO> findAllClients();
    ClientDTO findClientById(String id);
    List<ClientDTO> findClientsByName(String name);
}