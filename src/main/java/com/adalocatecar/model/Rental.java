package com.adalocatecar.model;

import com.adalocatecar.dto.ClientDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Rental {
    private final Client clientWhoRented;
    private List<Vehicle> rentedVehicles;
    private final String agencyLocal;
    private final LocalDateTime startDate;
    private final LocalDateTime expectedEndDate;
    private LocalDateTime actualEndDate;
    private double totalCost;
    private final StringBuilder invoice;

    public Rental(Client clientWhoRented, LocalDateTime startDate, LocalDateTime expectedEndDate, String agencyLocal) {
        this.clientWhoRented = clientWhoRented;
        this.agencyLocal = agencyLocal;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.rentedVehicles = new ArrayList<>();
        this.invoice = new StringBuilder();
        this.totalCost = 0.0;
    }

    public Client getClientWhoRented() {
        return clientWhoRented;
    }

    public List<Vehicle> getRentedVehicles() {
        return new ArrayList<>(rentedVehicles);
    }

    public String getAgencyLocal() {
        return agencyLocal;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getExpectedEndDate() {
        return expectedEndDate;
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

    public String getInvoice() {
        return invoice.toString();
    }

    public void addInvoice(String data) {
        invoice.append(data);
    }
}