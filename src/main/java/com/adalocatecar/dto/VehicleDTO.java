package com.adalocatecar.dto;

public class VehicleDTO {
    private String licensePlate;
    private String brand;
    private String model;
    private String type;
    private int year;
    private boolean isAvailable;

    public VehicleDTO() {
    }

    public VehicleDTO(String licensePlate,String brand, String model, String type, int year) {
        this.licensePlate = licensePlate;
        this.brand = brand;
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

    public String getBrand() {return brand;}

    public void setBrand(String brand) {this.brand = brand;}
}
