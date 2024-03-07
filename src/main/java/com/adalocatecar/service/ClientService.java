package com.adalocatecar.service;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.utility.ValidationResponse;

import java.util.List;

public interface ClientService {

    ValidationResponse registerClient(ClientDTO clientDTO);

    ValidationResponse updateClient(ClientDTO clientDTO);

    ValidationResponse deleteClient(String id);

    List<ClientDTO> findAllClients();

    ValidationResponse findClientById(String id);

    List<ClientDTO> findClientsByName(String name);
}