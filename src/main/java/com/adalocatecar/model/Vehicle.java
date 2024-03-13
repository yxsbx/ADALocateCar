package com.adalocatecar.model;

public class Vehicle {
    private String licensePlate;
    private String model;
    private String type;
    private final Rental rentalContract = new Rental();

    public Vehicle(String licensePlate, String model, String type) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.type = type;
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

    public Rental getRentalContract() {
        return rentalContract;
    }

    public boolean isAvailable() {
        return !rentalContract.getRentalStatus();
    }
}
