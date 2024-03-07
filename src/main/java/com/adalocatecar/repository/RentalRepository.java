package com.adalocatecar.repository;

import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.model.Client;

import java.util.List;

public interface RentalRepository {
    void create(Client client);
    void update(Client client);
    boolean delete(String id);
    List<RentalDTO> findAll();
    RentalDTO findByLicensePlate(String licensePlate);
    List<RentalDTO> findByClientId(String clientId);
}