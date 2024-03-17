package com.adalocatecar.model;

import java.time.LocalDateTime;

/**
 * Represents a rental contract entity with information such as rental status, client ID, agency local, and start date.
 */

public class Rental {
    private Boolean rentalStatus;
    private String idClientWhoRented = "";
    private String agencyLocal = "";
    private LocalDateTime startDate;

    /**
     * Constructs a Rental object with default values.
     */

    public Rental() {
        this.rentalStatus = false;
    }

    /**
     * Gets the ID of the client who rented the vehicle.
     *
     * @return The client ID.
     */

    public String getIdClientWhoRented() {
        return idClientWhoRented;
    }

    /**
     * Gets the local agency from which the vehicle was rented.
     *
     * @return The agency local.
     */

    public String getAgencyLocal() {
        return agencyLocal;
    }

    /**
     * Gets the start date of the rental period.
     *
     * @return The start date.
     */

    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the rental status of the contract.
     *
     * @param rentalStatus The rental status to set (true if rented, false if available).
     */

    public void setRentalStatus(boolean rentalStatus) {
        this.rentalStatus = rentalStatus;
    }


    /**
     * Sets the ID of the client who rented the vehicle.
     *
     * @param ClientWhoRented The client ID to set.
     */

    public void setIdClientWhoRented(String ClientWhoRented) {
        this.idClientWhoRented = ClientWhoRented;
    }

    /**
     * Sets the local agency from which the vehicle was rented.
     *
     * @param agencyLocal The agency local to set.
     */

    public void setAgencyLocal(String agencyLocal) {
        this.agencyLocal = agencyLocal;
    }

    /**
     * Sets the start date of the rental period.
     *
     * @param startDate The start date to set.
     */

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Checks if the vehicle is currently rented.
     *
     * @return True if the vehicle is rented, false if available.
     */

    public boolean isRentalStatus() {
        return rentalStatus;
    }
}