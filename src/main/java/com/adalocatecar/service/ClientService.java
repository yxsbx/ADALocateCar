package com.adalocatecar.service;

import com.adalocatecar.dto.ClientDTO;

import java.io.IOException;
import java.util.List;

public interface ClientService {

    /**
     * Creates a new client based on the provided client DTO.
     *
     * @param clientToCreate The client DTO containing information about the client to be created.
     */

    void createClient(ClientDTO clientToCreate);

    /**
     * Retrieves a list of all clients as client DTOs.
     *
     * @return A list of client DTOs representing all clients.
     * @throws IOException if an I/O error occurs while reading clients.
     */

    List<ClientDTO> readAllClients() throws IOException;

    /**
     * Updates an existing client based on the provided client DTO.
     *
     * @param clientToUpdate The client DTO containing updated information about the client.
     */

    void updateClient(ClientDTO clientToUpdate);

    /**
     * Deletes a client based on the provided client DTO.
     *
     * @param clientToDelete The client DTO representing the client to be deleted.
     * @return A message indicating the status of the deletion operation.
     */

    String deleteClient(ClientDTO clientToDelete);

    /**
     * Searches for a client by document ID and returns its DTO representation.
     *
     * @param id The document ID of the client to search for.
     * @return The client DTO representing the found client.
     */

    ClientDTO searchClientById(String id);

    /**
     * Searches for clients by name and returns a list of their DTO representations.
     *
     * @param name The name of the clients to search for.
     * @return A list of client DTOs representing the found clients.
     * @throws IOException if an I/O error occurs while searching for clients.
     */

    List<ClientDTO> searchClientsByName(String name) throws IOException;
}