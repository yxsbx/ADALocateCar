package com.adalocatecar.repository;

import java.util.List;
import java.util.Optional;

public interface GenericsRepository<T, ID> {
    void create(T clientToCreate);

    List<T> readAll();

    void update(T clientToUpdate);

    boolean delete(T clientToDelete);

    Optional<T> searchById(ID id);

    Optional<List<T>> searchByName(String name);
}
