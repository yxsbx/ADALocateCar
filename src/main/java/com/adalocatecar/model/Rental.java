package com.adalocatecar.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Rental {
    private String licensePlate;
    private String clientId;
    private LocalDateTime startDate;
    private LocalDateTime expectedEndDate;
    private LocalDateTime actualEndDate;
    private double totalCost;

    public Rental(String licensePlate, String clientId, LocalDateTime startDate, LocalDateTime expectedEndDate) {
        this.licensePlate = licensePlate;
        this.clientId = clientId;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isActive() {
        return actualEndDate == null && LocalDateTime.now().isBefore(expectedEndDate);
    }

    public void calculateTotalCost(double baseCostPerDay) {
        if (actualEndDate != null) {
            long daysRented = Duration.between(startDate, actualEndDate).toDays();
            this.totalCost = baseCostPerDay * daysRented;
        } else {
            throw new IllegalStateException("Rental has not been completed yet.");
        }
    }
}