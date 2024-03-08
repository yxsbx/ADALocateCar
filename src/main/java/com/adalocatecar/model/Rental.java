package com.adalocatecar.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Rental {
    private Vehicle vehicle;
    private Client client;
    private LocalDateTime startDate;
    private LocalDateTime expectedEndDate;
    private LocalDateTime actualEndDate;
    private double totalCost;

    public Rental(Vehicle vehicle, Client client, LocalDateTime startDate, LocalDateTime expectedEndDate) {
        this.vehicle = vehicle;
        this.client = client;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
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

    private double getDailyRate(String vehicleType) {
        switch (vehicleType) {
            case "SMALL":
                return 100.00;
            case "MEDIUM":
                return 150.00;
            case "SUV":
                return 200.00;
            default:
                throw new IllegalArgumentException("Invalid vehicle type");
        }
    }

    private double getDiscount(Client customer, int durationDays) {
        if (customer instanceof IndividualCustomer && durationDays > 5) {
            return 0.95; // 5% discount for individual customers
        } else if (customer instanceof BusinessCustomer && durationDays > 3) {
            return 0.90; // 10% discount for business customers
        } else {
            return 1.0; // No discount
        }
    }
}