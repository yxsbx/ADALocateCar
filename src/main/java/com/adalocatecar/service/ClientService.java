package com.adalocatecar.service;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.utility.Validation;

import java.io.IOException;
import java.util.List;

public interface ClientService {
    Validation createClient(ClientDTO clientDTO);

    Validation updateClient(ClientDTO clientDTO);

    Validation deleteClient(String id);

    List<ClientDTO> findAllClients() throws IOException;

    ClientDTO findClientById(String id);

    List<ClientDTO> findClientsByName(String name) throws IOException;
}