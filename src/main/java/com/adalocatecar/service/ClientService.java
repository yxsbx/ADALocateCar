package com.adalocatecar.service;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.utility.ValidationMessages;

import java.io.IOException;
import java.util.List;

public interface ClientService {

    ValidationMessages createClient(ClientDTO clientDTO);

    ValidationMessages updateClient(ClientDTO clientDTO);

    ValidationMessages deleteClient(String id);

    List<ClientDTO> findAllClients() throws IOException;

    ValidationMessages findClientById(String id);

    List<ClientDTO> findClientsByName(String name) throws IOException;
}