package com.adalocatecar.model;

public class Client {
    private String id;
    private String name;
    private String type;

    public Client(String id, String name, String type) {
        this.id = id;
        this.setName(name);
        this.type = type;
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
}