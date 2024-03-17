package com.adalocatecar.repository.impl;

import com.adalocatecar.repository.GenericsRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract implementation of the GenericsRepository interface providing common CRUD operations for data entities.
 *
 * @param <T>  The type of entity.
 * @param <ID> The type of ID for the entity.
 */

public abstract class GenericsRepositoryImpl<T, ID> implements GenericsRepository<T, ID> {

    private static final Logger logger = Logger.getLogger(GenericsRepositoryImpl.class.getName());
    File filePath;

    /**
     * Constructs a GenericsRepositoryImpl object with the specified file path.
     *
     * @param filePath The file path for storing data.
     */

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

    /**
     * Creates a new entity in the repository.
     *
     * @param object The object to create.
     */

    @Override
    public void create(T object) {
        try {
            if (isUniqueObjectId(getId(object))) {
                appendClientToFile(object);
            } else {
                throw new RuntimeException("Client with ID " + getId(object) + " already exists.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while creating a client.", e);
        }
    }

    /**
     * Updates an existing entity in the repository.
     *
     * @param object The object to update.
     */

    @Override
    public void update(T object) {
        try {
            replaceObjectInFile(object);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while updating.", e);
        }
    }

    /**
     * Deletes an entity from the repository.
     *
     * @param objToDelete The object to delete.
     * @return True if the object was deleted successfully, false otherwise.
     */

    @Override
    public boolean delete(T objToDelete) {
        try {
            List<T> clients = readAll();
            boolean removed = clients.removeIf(c -> getId(c).equals(getId(objToDelete)));
            if (removed) {
                rewriteFile(clients);
            }
            return removed;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while deleting", e);
            return false;
        }
    }

    /**
     * Retrieves all entities from the repository.
     *
     * @return A list of all objects in the repository.
     */

    @Override
    public List<T> readAll() {
        try {
            try (Stream<String> lines = Files.lines(filePath.toPath())) {
                return lines.map(this::stringToObject)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Searches for an entity by its ID.
     *
     * @param id The ID of the object to search for.
     * @return An optional containing the object if found, or an empty optional otherwise.
     */

    @Override
    public Optional<T> searchById(ID id) {
        return readAll().stream()
                .filter(obj -> getId(obj).equals(id))
                .findFirst();
    }

    /**
     * Searches for entities by their name.
     *
     * @param name The name to search for.
     * @return An optional containing a list of objects matching the name, or an empty optional if none are found.
     */

    @Override
    public Optional<List<T>> searchByName(String name) {
        List<T> allObjects = readAll();
        List<T> filteredObjects = allObjects.stream()
                .filter(a -> getName(a).toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return Optional.of(filteredObjects);
    }

    /**
     * Rewrites the file with updated content based on the given list of objects.
     *
     * @param objects The list of objects to be written to the file.
     * @throws IOException If an I/O error occurs while writing to the file.
     */

    private void rewriteFile(List<T> objects) throws IOException {
        List<String> lines = new ArrayList<>();
        for (T object : objects) {
            lines.add(objectToString(object));
        }
        Files.writeString(
                filePath.toPath(),
                String.join("\n", lines),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Checks if an object ID is unique among existing objects.
     *
     * @param id The ID to check for uniqueness.
     * @return True if the ID is unique, false otherwise.
     * @throws IOException If an I/O error occurs while reading from the repository.
     */

    private boolean isUniqueObjectId(ID id) throws IOException {
        List<T> clients = readAll();
        for (T object : clients) {
            if (getId(object).equals(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Appends an object's string representation to the file.
     *
     * @param object The object to append to the file.
     * @throws IOException If an I/O error occurs while appending to the file.
     */

    private void appendClientToFile(T object) throws IOException {
        Files.writeString(
                filePath.toPath(),
                "\n" + objectToString(object),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    /**
     * Replaces an object in the file with an updated object.
     *
     * @param object The updated object to replace in the file.
     * @throws IOException If an I/O error occurs while reading from or writing to the file.
     */

    private void replaceObjectInFile(T object) throws IOException {
        List<T> objects = readAll();
        List<T> updatedObjects = objects.stream().map(c -> getId(c).equals(getId(object)) ? object : c).collect(Collectors.toList());
        rewriteFile(updatedObjects);
    }

    /**
     * Converts a string from the storage to an object of type T.
     *
     * @param str The string representation of the object.
     * @return The object parsed from the string.
     */

    protected abstract T stringToObject(String str);

    /**
     * Converts an object of type T to a string representation for storage.
     *
     * @param object The object to convert.
     * @return A string representing the object.
     */

    protected abstract String objectToString(T object);

    /**
     * Retrieves the ID of an object.
     *
     * @param entity The object entity.
     * @return The ID of the object.
     */

    protected abstract ID getId(T entity);

    /**
     * Retrieves the name of an object.
     *
     * @param entity The object entity.
     * @return The name of the object.
     */

    protected abstract String getName(T entity);
}
