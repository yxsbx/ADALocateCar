package com.adalocatecar.model;

import java.time.LocalDateTime;

public class Rental {
    private Boolean rentalStatus;
    private String idClientWhoRented = "";
    private String agencyLocal = "";
    private LocalDateTime startDate;

    public Rental() {
        this.rentalStatus = false;
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

    public boolean isRentalStatus() {
        return rentalStatus;
    }

}