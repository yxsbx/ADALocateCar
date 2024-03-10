package com.adalocatecar.repository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface GenericsRepository <T, ID>{
    void create(T client);
    void update(T client);
    boolean delete(ID id);
    Optional<T> findById(ID id);
    List<T> findByName(String name);
    List<T> findAll() throws IOException;
}
