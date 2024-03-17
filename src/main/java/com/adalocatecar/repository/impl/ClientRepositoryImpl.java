package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of the ClientRepository interface using a file-based storage system.
 */

public class ClientRepositoryImpl extends GenericsRepositoryImpl<Client, String> implements ClientRepository {
    private static final File filePath = new File("src/data/clients.txt");

    /**
     * Constructs a ClientRepositoryImpl object with the specified file path.
     */

    public ClientRepositoryImpl() {
        super(filePath);
    }

    /**
     * Converts a Client object to a string representation for storage.
     *
     * @param client The Client object to convert.
     * @return A string representing the Client object.
     */

    @Override
    protected String objectToString(Client client) {
        return String.join(",", client.getId(), client.getName(), client.getClientType(), String.join(",", client.getRentedVehiclesPlates()));
    }

    /**
     * Converts a string from the storage to a Client object.
     *
     * @param str The string representation of the Client object.
     * @return The Client object parsed from the string.
     * @throws IllegalArgumentException if the input string is invalid.
     */

    @Override
    protected Client stringToObject(String str) {
        String[] parts = str.split(",");
        if (parts.length < 3 || parts[0].isEmpty()) {
            throw new IllegalArgumentException("Invalid input string: " + str);
        }

        String id = parts[0];
        String name = parts[1];
        String type = parts[2];

        List<String> vehicleLicensePlates = Arrays.asList(parts).subList(3, parts.length);

        Client client = new Client(id, name, type);
        vehicleLicensePlates.forEach(client::addRentedVehicle);

        return client;
    }

    /**
     * Gets the ID of the Client entity.
     *
     * @param entity The Client entity.
     * @return The ID of the Client.
     */

    @Override
    protected String getId(Client entity) {
        return entity.getId();
    }

    /**
     * Gets the name of the Client entity.
     *
     * @param entity The Client entity.
     * @return The name of the Client.
     */

    @Override
    protected String getName(Client entity) {
        return entity.getName();
    }
}