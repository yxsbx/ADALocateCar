package com.adalocatecar.dto;

public class VehicleDTO {
    private String licensePlate;
    private String model;
    private String type;
    private int year;
    private boolean available;

    public VehicleDTO(String licensePlate, String model, String type, int year) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.type = type;
        this.year = year;
        this.available = true;
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
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean checkAvailability() {
        return available;
    }
}