package com.adalocatecar.repository;

import com.adalocatecar.dto.ClientDTO;
import java.util.List;

public interface ClientRepository {
    void save(ClientDTO client);
    void update(ClientDTO client);
    void delete(String id);
    List<ClientDTO> findAll();
    ClientDTO findById(String id);
}