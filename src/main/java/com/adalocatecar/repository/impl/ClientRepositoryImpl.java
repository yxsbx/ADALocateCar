package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.utility.FileHandler;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClientRepositoryImpl extends GenericsRepositoryImpl<Client, String> implements ClientRepository {
    private static final File filePath = new File("src/data/clients.txt");

    public ClientRepositoryImpl() {
        super(filePath);
    }

    public boolean hasRentedCars(String id) {
        List<String> lines = FileHandler.readFromFile(filePath.getAbsolutePath());
        for (String line : lines) {
            String[] parts = line.split(",");
            String clientId = parts[0];
            if (clientId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected String objectToString(Client client) {
        return String.join(",", client.getId(), client.getName(), client.getClientType(), String.join(",", client.getRentedVehiclesPlates()));
    }

    @Override
    protected Client stringToObject(String str) {
        String[] parts = str.split(",");
        if (parts.length < 3) {
            // Handle the case where there are insufficient parts
            throw new IllegalArgumentException("Invalid input string: " + str);
        }

        String id = parts[0];
        String name = parts[1];
        String type = parts[2];

        // Now, let's read the list of vehicle license plates (if available)
        List<String> vehicleLicensePlates = Arrays.asList(parts).subList(3, parts.length);

        // Create a new Client object with the extracted information
        Client client = new Client(id, name, type);
        vehicleLicensePlates.forEach(client::addRentedVehicle);

        return client;
    }

    @Override
    protected String getId(Client entity) {
        return entity.getId();
    }

    @Override
    protected String getName(Client entity) {
        return entity.getName();
    }
}