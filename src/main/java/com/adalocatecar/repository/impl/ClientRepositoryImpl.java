package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ClientRepositoryImpl extends GenericsRepositoryImpl<Client, String> implements ClientRepository {
    private static final File filePath = new File("src/data/clients.txt");

    public ClientRepositoryImpl() {
        super(filePath);
    }

    @Override
    protected String objectToString(Client client) {
        return String.join(",", client.getId(), client.getName(), client.getClientType(), String.join(",", client.getRentedVehiclesPlates()));
    }

    @Override
    protected Client stringToObject(String str) {
        String[] parts = str.split(",");
        if (parts.length < 3) {
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

    @Override
    protected String getId(Client entity) {
        return entity.getId();
    }

    @Override
    protected String getName(Client entity) {
        return entity.getName();
    }
}