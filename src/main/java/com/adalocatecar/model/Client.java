package com.adalocatecar.model;

public class Client {
    private String id;
    private String name;
    private String clientType;

    public Client(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.clientType = type;
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

    public boolean hasRentedCars() {
        return true;
    }
}
