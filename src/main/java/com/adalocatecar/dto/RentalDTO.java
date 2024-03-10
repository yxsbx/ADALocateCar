package com.adalocatecar.dto;

import java.time.LocalDateTime;

public class RentalDTO {
    private String licensePlate;
    private String vehicleType;
    private LocalDateTime startDate;
    private LocalDateTime expectedEndDate;
    private LocalDateTime actualEndDate;
    private String agencyLocal;
    private double totalCost;

    public RentalDTO(String licensePlate, String vehicleType, LocalDateTime startDate, LocalDateTime expectedEndDate, LocalDateTime actualEndDate, String agencyLocal, double totalCost) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.actualEndDate = actualEndDate;
        this.agencyLocal = agencyLocal;
        this.totalCost = totalCost;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(LocalDateTime expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public LocalDateTime getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(LocalDateTime actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getAgencyLocal() {
        return agencyLocal;
    }

    public void setAgencyLocal(String agencyLocal) {
        this.agencyLocal = agencyLocal;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}