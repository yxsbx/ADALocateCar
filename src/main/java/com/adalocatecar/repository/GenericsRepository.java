package com.adalocatecar.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic interface for repository operations.
 *
 * @param <T>  The type of entity to manage.
 * @param <ID> The type of ID used for entities.
 */

public interface GenericsRepository<T, ID> {

    /**
     * Creates a new entity in the repository.
     *
     * @param entityToCreate The entity to create.
     */

    void create(T entityToCreate);

    /**
     * Retrieves all entities from the repository.
     *
     * @return A list of all entities in the repository.
     */

    List<T> readAll();

    /**
     * Updates an existing entity in the repository.
     *
     * @param entityToUpdate The entity to update.
     */

    void update(T entityToUpdate);

    /**
     * Deletes an entity from the repository.
     *
     * @param entityToDelete The entity to delete.
     * @return True if the entity was deleted successfully, false otherwise.
     */

    boolean delete(T entityToDelete);

    /**
     * Searches for an entity by its ID.
     *
     * @param id The ID of the entity to search for.
     * @return An Optional containing the entity if found, otherwise empty.
     */

    Optional<T> searchById(ID id);

    /**
     * Searches for entities by their name.
     *
     * @param name The name to search for.
     * @return An Optional containing a list of entities with the specified name if found, otherwise empty.
     */

    Optional<List<T>> searchByName(String name);
}
