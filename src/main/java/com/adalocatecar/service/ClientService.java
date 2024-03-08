package com.adalocatecar.service;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.utility.ValidationResponse;

import java.io.IOException;
import java.util.List;

public interface ClientService {

    ValidationResponse createClient(ClientDTO clientDTO);

    ValidationResponse updateClient(ClientDTO clientDTO);

    ValidationResponse deleteClient(String id);

    List<ClientDTO> findAllClients() throws IOException;

    ValidationResponse findClientById(String id);

    List<ClientDTO> findClientsByName(String name) throws IOException;
}