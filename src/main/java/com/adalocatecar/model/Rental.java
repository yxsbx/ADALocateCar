package com.adalocatecar.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class Rental {
    private String id;
    private String clientId;
    private String vehicleId;
    private Date rentalDate;

    public Rental(String id, String clientId, String vehicleId, Date rentalDate) {
        this.id = id;
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.rentalDate = rentalDate;
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public Date getRentalDate() {
        return rentalDate;
    }
}
