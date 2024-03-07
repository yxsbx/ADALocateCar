package com.adalocatecar.repository;

import com.adalocatecar.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    void create(Client client);
    void update(Client client);
    boolean delete(String id);
    Optional<Client> findById(String id);
    List<Client> findAll();
}