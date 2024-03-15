package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Client;
import com.adalocatecar.repository.GenericsRepository;
import com.adalocatecar.utility.FileHandler;
import jdk.dynalink.beans.StaticClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class GenericsRepositoryImpl<T, ID> implements GenericsRepository<T, ID> {

    private static final Logger logger = Logger.getLogger(GenericsRepositoryImpl.class.getName());
    File filePath;

    public GenericsRepositoryImpl(File filePath) {
        this.filePath = filePath;
        try {
            if (filePath.createNewFile()) {
                logger.info("File created: " + filePath.getName());
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
    public void update(T object) {
        try {
            replaceObjectInFile(object);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while updating.", e);
        }
    }

    @Override
    public boolean delete(ID id) {
        try {
            List<T> clients = findAll();
            boolean removed = clients.removeIf(c -> getId(c).equals(id));
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
    public Optional<T> findById(ID id) {
        return findAll().stream()
                .filter(obj -> getId(obj).equals(id))
                .findFirst();
    }

    @Override
    public List<T> findAll() {
        try {
            return Files.lines(filePath.toPath())
                    .map(this::stringToObject)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
    @Override
    public Optional<List<T>> findByName(String name) {
        List<T> allObjects = findAll();
        List<T> filteredObjects = allObjects.stream()
                .filter(a -> getName(a).toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return Optional.of(filteredObjects);
    }
    private void rewriteFile(List<T> objects) throws IOException {
        List<String> lines = new ArrayList<>();
        for (T object : objects) {
            lines.add(objectToString(object));
        }
        FileHandler.writeToFile(lines, filePath.getAbsolutePath(), false);
    }

    private boolean isUniqueObjectId(ID id) throws IOException {
        List<T> clients = findAll();
        for (T object : clients) {
            if (getId(object).equals(id)) {
                return false;
            }
        }
        return true;
    }

    private void appendClientToFile(T object) throws IOException {
        FileHandler.writeToFile(Collections.singletonList(objectToString(object)), filePath.getAbsolutePath(), true);
    }

    private void replaceObjectInFile(T object) throws IOException {
        List<T> objects = findAll();
        List<T> updatedObjects = objects.stream().map(c -> getId(c).equals(getId(object)) ? object : c).collect(Collectors.toList());
        rewriteFile(updatedObjects);
    }

    protected abstract T stringToObject(String str);

    protected abstract String objectToString(T object);

    protected abstract ID getId(T entity);

    protected abstract String getName(T entity);
}
