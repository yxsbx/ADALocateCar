package com.adalocatecar.repository;

import com.adalocatecar.dto.RentalDTO;
import java.util.List;

public interface RentalRepository {
    void save(RentalDTO rental);
    void update(RentalDTO rental);
    void delete(String licensePlate);
    List<RentalDTO> findAll();
    RentalDTO findByLicensePlate(String licensePlate);
    List<RentalDTO> findByClientId(String clientId);
}