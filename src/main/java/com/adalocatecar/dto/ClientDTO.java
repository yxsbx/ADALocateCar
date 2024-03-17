package com.adalocatecar.dto;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO {
    private final String id;
    private final String name;
    private final String clientType;
    private final List<String> rentedVehiclesPlates;

    public ClientDTO(String id, String name, String clientType) {
        this.id = id;
        this.name = name;
        this.clientType = clientType;
        this.rentedVehiclesPlates = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClientType() {
        return clientType;
    }


    public List<String> getRentedVehiclesPlates() {
        return rentedVehiclesPlates;
    }

    public void addRentedVehicle(String vehicleParte) {
        this.rentedVehiclesPlates.add(vehicleParte);
    }

    @Override
    public String toString() {
        return "Document: " + getId() +
               ", Name: " + name +
               ", Client type: " + clientType +
               "\nRented Vehicles Plates: " + String.join(",", rentedVehiclesPlates);
    }
}
