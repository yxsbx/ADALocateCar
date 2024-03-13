package com.adalocatecar.dto;

import com.adalocatecar.model.Rental;

public class VehicleDTO {
    private String licensePlate;
    private String model;
    private String type;
    private Rental rentalContract;


    public VehicleDTO(String licensePlate, String model, String type, Rental rentalContract) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.type = type;
        this.rentalContract = rentalContract;
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

    public boolean isAvailable() {
        return !rentalContract.getRentalStatus();
    }


    public Rental getRentalContract() {
        return rentalContract;
    }

    public void setRentalContract(Rental rentalContract) {
        this.rentalContract = rentalContract;
    }

    @Override
    public String toString() {
        return  "License Plate ='" + licensePlate + '\'' +
                ", Model ='" + model + '\'' +
                ", Type ='" + type + '\'' +
                ", Available =" + isAvailable();
    }
}