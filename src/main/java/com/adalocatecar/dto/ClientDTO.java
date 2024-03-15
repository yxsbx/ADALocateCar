package com.adalocatecar.dto;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO {
    private String id;
    private String name;
    private String clientType;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public List<String> getRentedVehiclesPlates() {
        return rentedVehiclesPlates;
    }

    public void addRentedVehicle(String vehicleParte) {
       this.rentedVehiclesPlates.add(vehicleParte);
    }

    @Override
    public String toString() {
        return "Name=" + name +
               "\nClient type = " + clientType +
               "\nRented Vehicles Plates = " + String.join(",", rentedVehiclesPlates) + "\n";
    }
}
