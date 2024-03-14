package com.adalocatecar.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Rental {
    private Boolean rentalStatus;
    private String idClientWhoRented = "";
    private String agencyLocal = "";
    private LocalDateTime startDate;
    private LocalDateTime expectedEndDate;
    private LocalDateTime actualEndDate;

    public Rental() {
        this.rentalStatus = false;
    }

    public void rentThisCar(String idClientWhoRented,
                            String agencyLocal,
                            LocalDateTime startDate,
                            LocalDateTime expectedReturnDate) {
        if(rentalStatus) {
            throw new IllegalStateException("Rental has already been rented");
        }
        this.rentalStatus = true;
        this.idClientWhoRented = idClientWhoRented;
        this.agencyLocal = agencyLocal;
        this.startDate = startDate;
        this.expectedEndDate = expectedReturnDate;
    }

    public String getIdClientWhoRented() {
        return idClientWhoRented;
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

    public void setRentalStatus(boolean rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public void setIdClientWhoRented(String ClientWhoRented) {
        this.idClientWhoRented = ClientWhoRented;
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

    public boolean isRentalStatus() {
        return rentalStatus;
    }

}