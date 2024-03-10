package com.adalocatecar.service;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.model.Vehicle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    String createClient(ClientDTO clientDTO);

    String updateClient(ClientDTO clientDTO);

    String deleteClient(String id);

    List<ClientDTO> findAllClients() throws IOException;

    Optional<ClientDTO> findClientById(String id);

    List<ClientDTO> findClientsByName(String name) throws IOException;

    Client findClientByVehicleLicensePlate(String licensePlate);

    boolean returnVehicleFromClient(String licensePlate);

    public boolean assignVehicleToClient(Vehicle vehicle, Optional<ClientDTO> client, LocalDateTime startDate, LocalDateTime endDate);
}