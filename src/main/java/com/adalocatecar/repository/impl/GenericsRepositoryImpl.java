package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Client;
import com.adalocatecar.repository.GenericsRepository;
import com.adalocatecar.utility.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class GenericsRepositoryImpl<T, ID> implements GenericsRepository <T, ID>{

    private static final Logger logger = Logger.getLogger(ClientRepositoryImpl.class.getName());
    private final String filePath = "clients.txt";

    public GenericsRepositoryImpl() {
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                logger.info("File created: " + file.getName());
            } else {
                logger.info("File already exists.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while creating the file.", e);
        }
    }

    @Override
    public void create(T object) {
        try {
            if (isUniqueObjectId(getId(object))) {
                appendClientToFile(object);
            } else {
                logger.warning("Client with ID " + getId(object) + " already exists.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while creating a client.", e);
        }
    }

    @Override
    public void update(Client client) {
        try {
            if (isUniqueClientId(client.getId())) {
                replaceClientInFile(client);
            } else {
                logger.warning("Client with ID " + client.getId() + " already exists.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while updating a client.", e);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            List<Client> clients = findAll();
            boolean removed = clients.removeIf(c -> c.getId().equals(id));
            if (removed) {
                rewriteFile(clients);
            }
            return removed;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while deleting a client.", e);
            return false;
        }
    }

    @Override
    public Optional<Client> findById(String id) {
        try {
            return findAll().stream()
                    .filter(client -> client.getId().equals(id))
                    .findFirst();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while finding a client by ID.", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Client> findAll() throws IOException {
        List<Client> clients = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile(filePath);
        for (String line : lines) {
            clients.add(stringToClient(line));
        }
        return clients;
    }

    private void rewriteFile(List<Client> clients) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Client client : clients) {
            lines.add(clientToString(client));
        }
        FileHandler.writeToFile(lines, filePath);
    }

    private String clientToString(Client client) {
        return String.join(",", client.getId(), client.getName(), client.getType());
    }

    private Client stringToClient(String str) {
        String[] parts = str.split(",");
        String id = parts[0];
        String name = parts[1];
        String type = parts[2];
        return new Client(id, name, type);
    }

    @Override
    public boolean hasRentedCars(String id) {
        List<String> lines = FileHandler.readFromFile(filePath);
        for (String line : lines) {
            String[] parts = line.split(",");
            String clientId = parts[0];
            if (clientId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUniqueObjectId(ID id) throws IOException {
        List<Client> clients = findAll();
        for (Client client : clients) {
            if (client.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    private void appendClientToFile(T client) throws IOException {
        FileHandler.writeToFile(Collections.singletonList(clientToString(client)), filePath);
    }

    private void replaceClientInFile(Client client) throws IOException {
        List<Client> clients = findAll();
        List<Client> updatedClients = clients.stream()
                .map(c -> c.getId().equals(client.getId()) ? client : c)
                .collect(Collectors.toList());
        rewriteFile(updatedClients);
    }

    protected abstract ID getId(T entity);
}
