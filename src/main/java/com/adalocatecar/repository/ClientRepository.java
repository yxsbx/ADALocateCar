package com.adalocatecar.repository;

import com.adalocatecar.model.Client;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends GenericsRepository<Client, String> {
    boolean hasRentedCars(String id) throws IOException;
}