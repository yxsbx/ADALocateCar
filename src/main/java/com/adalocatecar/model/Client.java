package com.adalocatecar.model;

public class Client {
    private String id;
    private String name;
    private String type;
    private String contactInfo;

    public Client(String id, String name, String type, String contactInfo) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.contactInfo = contactInfo;
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}