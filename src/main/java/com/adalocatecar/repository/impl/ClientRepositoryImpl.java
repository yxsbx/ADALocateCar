package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClientRepositoryImpl implements ClientRepository {
    private final String filePath = "clients.txt";

    public ClientRepositoryImpl() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void create(Client client) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(clientToString(client));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Client client) {
        List<Client> clients = findAll().stream()
                .map(c -> c.getId().equals(client.getId()) ? client : c)
                .collect(Collectors.toList());
        rewriteFile(clients);
    }

    @Override
    public boolean delete(String id) {
        List<Client> clients = findAll();
        boolean removed = clients.removeIf(c -> c.getId().equals(id));
        if (removed) {
            rewriteFile(clients);
        }
        return removed;
    }

    @Override
    public Optional<Client> findById(String id) {
        return findAll().stream()
                .filter(client -> client.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                clients.add(stringToClient(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private void rewriteFile(List<Client> clients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Client client : clients) {
                writer.write(clientToString(client));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String clientToString(Client client) {
        return String.join(",", client.getId(), client.getName(), client.getType(), client.getContactInfo());
    }

    private Client stringToClient(String str) {
        String[] parts = str.split(",");
        return new Client(parts[0], parts[1], parts[2], parts.length > 3 ? parts[3] : "");
    }
}