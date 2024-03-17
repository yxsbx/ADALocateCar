package com.adalocatecar.service;

import com.adalocatecar.dto.ClientDTO;

import java.io.IOException;
import java.util.List;

public interface ClientService {
    void createClient(ClientDTO clientToCreate);

    List<ClientDTO> readAllClients() throws IOException;

    void updateClient(ClientDTO clientToUpdate);

    String deleteClient(ClientDTO clientToDelete);

    ClientDTO searchClientByDocument(String id);

    List<ClientDTO> searchClientsByName(String name) throws IOException;

}