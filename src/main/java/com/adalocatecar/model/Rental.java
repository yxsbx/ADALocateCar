package com.adalocatecar.model;

import java.time.LocalDateTime;

public class Rental {
    private Boolean rentalStatus;
    private Client clientWhoRented = new Client("","","");
    private String agencyLocal;
    private LocalDateTime startDate;
    private LocalDateTime expectedEndDate;
    private LocalDateTime actualEndDate;

    public Rental() {
        this.rentalStatus = false;
    }

    public void rentThisCar(Client client,
                            String agencyLocal,
                            LocalDateTime startDate,
                            LocalDateTime expectedReturnDate) {
        if(rentalStatus) throw new IllegalStateException("Rental has already been rented");
        this.rentalStatus = true;
        this.clientWhoRented = client;
        this.agencyLocal = agencyLocal;
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

    public void setRentalStatus(Boolean rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public void setClientWhoRented(Client clientWhoRented) {
        this.clientWhoRented = clientWhoRented;
    }

    public void setAgencyLocal(String agencyLocal) {
        this.agencyLocal = agencyLocal;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setExpectedEndDate(LocalDateTime expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public boolean getRentalStatus() {
        return rentalStatus;
    }
}