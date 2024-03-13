package com.adalocatecar.service;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.model.Vehicle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface ClientService {
    String createClient(ClientDTO clientDTO);

    String updateClient(ClientDTO clientDTO);

    String deleteClient(String id);

    List<ClientDTO> findAllClients() throws IOException;

    ClientDTO findClientByDocument(String id);

    List<ClientDTO> findClientsByName(String name) throws IOException;

    public boolean assignVehicleToClient(Vehicle vehicle, Client client, String agency,  LocalDateTime startDate, LocalDateTime endDate);
}