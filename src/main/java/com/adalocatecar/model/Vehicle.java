package com.adalocatecar.model;

public class Vehicle {
    private String licensePlate;
    private String model;
    private String type;
    private Rental rentalContract;

    public Vehicle(String licensePlate, String model, String type) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.type = type;
        this.rentalContract = new Rental();
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

    public void setRentalContract(Rental rentalContract) {
        this.rentalContract = rentalContract;
    }

    public void removeRentalContract() {
        this.rentalContract = null;
    }

    public boolean isAvailable() {
        return !rentalContract.isRentalStatus();
    }

}
