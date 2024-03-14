package com.adalocatecar.dto;

import java.time.LocalDateTime;

public class RentalDTO {
    private Boolean rentalStatus;
    private String idClientWhoRented;
    private String agencyLocal;
    private LocalDateTime startDate;
    private LocalDateTime expectedEndDate;

    public RentalDTO(Boolean rentalStatus,
                     String idClientWhoRented,
                     String agencyLocal,
                     LocalDateTime startDate,
                     LocalDateTime expectedEndDate) {
        this.rentalStatus = rentalStatus;
        this.idClientWhoRented = idClientWhoRented;
        this.agencyLocal = agencyLocal;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
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

    public LocalDateTime getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(LocalDateTime expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }
}