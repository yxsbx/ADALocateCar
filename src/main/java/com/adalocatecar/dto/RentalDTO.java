package com.adalocatecar.dto;

import java.time.LocalDateTime;

public class RentalDTO {
    private Boolean rentalStatus;
    private ClientDTO clientWhoRented;
    private String agencyLocal;
    private LocalDateTime startDate;
    private LocalDateTime expectedEndDate;
    private LocalDateTime actualEndDate;

    public RentalDTO(Boolean rentalStatus,
                     ClientDTO client,
                     String agencyLocal,
                     LocalDateTime startDate,
                     LocalDateTime expectedEndDate,
                     LocalDateTime actualEndDate) {
        this.rentalStatus = rentalStatus;
        this.clientWhoRented = client;
        this.agencyLocal = agencyLocal;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.actualEndDate = actualEndDate;
    }

    public Boolean getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(Boolean rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public ClientDTO getClientWhoRented() {
        return clientWhoRented;
    }

    public void setClientWhoRented(ClientDTO clientWhoRented) {
        this.clientWhoRented = clientWhoRented;
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

    public LocalDateTime getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(LocalDateTime actualEndDate) {
        this.actualEndDate = actualEndDate;
    }
}