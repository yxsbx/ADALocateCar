package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.utility.FileHandler;
import java.io.*;
import java.util.*;

public class ClientRepositoryImpl extends GenericsRepositoryImpl <Client, String> implements ClientRepository {
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
        return String.join(",", client.getId(), client.getName(), client.getType());
    }

    @Override
    protected Client stringToObject(String str) {
        String[] parts = str.split(",");
        String id = parts[0];
        String name = parts[1];
        String type = parts[2];
        return new Client(id, name, type);
    }

    @Override
    protected String getId(Client entity) {
        return entity.getId();
    }


