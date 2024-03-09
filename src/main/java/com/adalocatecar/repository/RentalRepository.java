package com.adalocatecar.repository;

import com.adalocatecar.model.Rental;

import java.util.List;

public interface RentalRepository {
    List<Rental> findByClientId(String clientId);
}