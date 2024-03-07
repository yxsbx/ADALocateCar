package com.adalocatecar.model;

public class Vehicle {
    private String licensePlate;
    private String model;
    private String type;
    private int year;
    private boolean isAvailable;

    public Vehicle(String licensePlate, String model, String type, int year) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.type = type;
        this.year = year;
        this.isAvailable = true;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}