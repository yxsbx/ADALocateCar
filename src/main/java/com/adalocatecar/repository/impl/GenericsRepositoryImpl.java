package com.adalocatecar.repository.impl;

import com.adalocatecar.repository.GenericsRepository;
import com.adalocatecar.utility.FileHandler;
import com.adalocatecar.utility.Validation;

import java.io.File;
import java.io.IOException;
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
        if (Validation.fileExists(filePath)) {
            logger.info(Validation.fileAlreadyExists(filePath.getName()));
        } else {
            logger.info(Validation.fileCreated(filePath.getName()));
        }
    }

    @Override
    public void create(T object) {
        try {
            if (isUniqueObjectId(getId(object))) {
                appendObjectToFile(object);
            } else {
                logger.warning(Validation.objectAlreadyExists(getId(object)));
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, Validation.errorWhileCreatingObject(), e);
        }
    }

    @Override
    public void update(T object) {
        try {
            if (isUniqueObjectId(getId(object))) {
                replaceObjectInFile(object);
            } else {
                logger.warning(Validation.objectAlreadyExists(getId(object)));
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, Validation.errorWhileUpdatingObject(), e);
        }
    }

    @Override
    public boolean delete(ID id) {
        try {
            List<T> objects = findAll();
            boolean removed = objects.removeIf(o -> getId(o).equals(id));
            if (removed) {
                rewriteFile(objects);
            }
            return removed;
        } catch (IOException e) {
            logger.log(Level.SEVERE, Validation.errorWhileDeletingObject(), e);
            return false;
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try {
            return findAll().stream()
                    .filter(obj -> getId(obj).equals(id))
                    .findFirst();
        } catch (IOException e) {
            logger.log(Level.SEVERE, Validation.errorWhileFindingObjectByID(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<T> findAll() throws IOException {
        List<T> objects = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile(filePath.getAbsolutePath());
        for (String line : lines) {
            objects.add(stringToObject(line));
        }
        return objects;
    }

    private void rewriteFile(List<T> objects) throws IOException {
        List<String> lines = new ArrayList<>();
        for (T object : objects) {
            lines.add(objectToString(object));
        }
        FileHandler.writeToFile(lines, filePath.getAbsolutePath());
    }

    private boolean isUniqueObjectId(ID id) throws IOException {
        List<T> objects = findAll();
        for (T object : objects) {
            if (getId(object).equals(id)) {
                return false;
            }
        }
        return true;
    }

    private void appendObjectToFile(T object) throws IOException {
        FileHandler.writeToFile(Collections.singletonList(objectToString(object)), filePath.getAbsolutePath());
    }

    private void replaceObjectInFile(T object) throws IOException {
        List<T> objects = findAll();
        List<T> updatedObjects = objects.stream()
                .map(o -> getId(o).equals(getId(object)) ? object : o)
                .collect(Collectors.toList());
        rewriteFile(updatedObjects);
    }

    protected abstract T stringToObject(String str);

    protected abstract String objectToString(T object);

    protected abstract ID getId(T entity);

    protected abstract boolean hasRentedCars(ID id);
}
