package com.adalocatecar.model;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final String id;
    private final String name;
    private String clientType;
    private final List<String> rentedVehiclesPlates;

    public Client(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.clientType = type;
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

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public void addRentedVehicle(String vehicleParte) {
        this.rentedVehiclesPlates.add(vehicleParte);
    }

    public List<String> getRentedVehiclesPlates() {
        return rentedVehiclesPlates;
    }
}
