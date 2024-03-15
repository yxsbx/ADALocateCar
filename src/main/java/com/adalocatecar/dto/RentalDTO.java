package com.adalocatecar.dto;

import java.time.LocalDateTime;

public class RentalDTO {
    private Boolean rentalStatus;
    private String idClientWhoRented;
    private String agencyLocal;
    private LocalDateTime startDate;

    public RentalDTO(Boolean rentalStatus,
                     String idClientWhoRented,
                     String agencyLocal,
                     LocalDateTime startDate) {
        this.rentalStatus = rentalStatus;
        this.idClientWhoRented = idClientWhoRented;
        this.agencyLocal = agencyLocal;
        this.startDate = startDate;
    }

    public Boolean getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(Boolean rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public String getIdClientWhoRented() {
        return idClientWhoRented;
    }

    public void setIdClientWhoRented(String clientWhoRented) {
        this.idClientWhoRented = clientWhoRented;
    }

    public String getAgencyLocal() {
        return agencyLocal;
    }

    public void setAgencyLocal(String agencyLocal) {
        this.agencyLocal = agencyLocal;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}