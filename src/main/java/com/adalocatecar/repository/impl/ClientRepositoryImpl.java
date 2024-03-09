package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Client;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.repository.RentalRepository;
import com.adalocatecar.model.Rental;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClientRepositoryImpl extends GenericsRepositoryImpl<Client, String> implements ClientRepository {

    private final RentalRepository rentalRepository;

    private static final File filePath = new File("src/data/clients.txt");

    public ClientRepositoryImpl(RentalRepository rentalRepository) {
        super(filePath);
        this.rentalRepository = rentalRepository;
    }

    @Override
    protected String objectToString(Client client) {
        return String.join(",", client.getId(), client.getName(), client.getType());
    }

    @Override
    protected Client stringToObject(String str) {
        String[] parts = str.split(",");
        String id = parts[0];
        String name = parts[1];
        String type = parts[2];
        return new Client(id, name, type);
    }

    @Override
    protected String getId(Client entity) {
        return entity.getId();
    }

    @Override
    public boolean hasRentedCars(String id) {
        List<Rental> rentalRecords = rentalRepository.findByClientId(id);
        return !rentalRecords.isEmpty();
    }
}