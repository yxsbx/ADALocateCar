package com.adalocatecar.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface GenericsRepository<T, ID> {
    void create(T object) throws IOException;
    void update(T object) throws IOException;
    boolean delete(ID id) throws IOException;
    Optional<T> findById(ID id) throws IOException;
    List<T> findAll() throws IOException;
}
