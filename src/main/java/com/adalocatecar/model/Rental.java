package com.adalocatecar.model;

import java.time.LocalDateTime;

public class Rental {
    private Boolean rentalStatus;
    private Client clientWhoRented;
    private String agencyLocal;
    private LocalDateTime startDate;
    private LocalDateTime expectedEndDate;
    private LocalDateTime actualEndDate;
    public Rental() {
        this.rentalStatus = false;
    }

    public void rentThisCar(Client client, String agency,LocalDateTime startDate, LocalDateTime expectedReturnDate) {
        if(rentalStatus) throw new IllegalStateException("Rental has already been rented");
        this.rentalStatus = true;
        this.clientWhoRented = client;
        this.agencyLocal = agency;
        this.startDate = startDate;
        this.expectedEndDate = expectedReturnDate;
    }
    public Client getClientWhoRented() {
        return clientWhoRented;
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

    public boolean getRentalStatus() {
        return rentalStatus;
    }
}