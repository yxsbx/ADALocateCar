package com.adalocatecar.dto;


public class VehicleDTO {
    private String licensePlate;
    private String model;
    private String type;
    private RentalDTO rentalContract;
    //private boolean available; Comentado para explicação do Daniel

    public VehicleDTO(String licensePlate, String model, String type, RentalDTO rentalContract) {
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

    public RentalDTO getRentalContract() {
        return rentalContract;
    }

    public void setRentalContract(RentalDTO rentalContract) {
        this.rentalContract = rentalContract;
    }

    public boolean isAvailable() {
        return !rentalContract.rentalStatus();
    }

    @Override
    public String toString() {
        return "License Plate: " + licensePlate +
                ", Model: " + model +
                ", Type: " + type +
                ", Available: " + isAvailable();
    }
}