package com.adalocatecar.dto;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO {
    private String id;
    private String name;
    private String type;
    private final List<VehicleDTO> rentedVehicles;
    public ClientDTO(String id, String name, String type) {
        this.id = id;
        this.setName(name);
        this.type = type;
        this.rentedVehicles = new ArrayList<>();
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

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public List<VehicleDTO> getRentedVehicles() {
        return rentedVehicles;
    }
    public void addRentedVehicle(VehicleDTO vehicle) {
        this.rentedVehicles.add(vehicle);
    }

    @Override
    public String toString() {
        return "Client{" +
                "CPF/CNPJ='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
