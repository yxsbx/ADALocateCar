package com.adalocatecar.dto;


public class VehicleDTO {
    private final String licensePlate;
    private String model;
    private String type;
    private RentalDTO rentalContract;

    /**
     * Constructs a VehicleDTO object with the specified details.
     *
     * @param licensePlate   The license plate of the vehicle.
     * @param model          The model of the vehicle.
     * @param type           The type of the vehicle.
     * @param rentalContract The rental contract associated with the vehicle.
     */

    public VehicleDTO(String licensePlate, String model, String type, RentalDTO rentalContract) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.type = type;
        this.rentalContract = rentalContract;
    }

    /**
     * Gets the license plate of the vehicle.
     *
     * @return The license plate of the vehicle.
     */

    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Gets the model of the vehicle.
     *
     * @return The model of the vehicle.
     */

    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the vehicle.
     *
     * @param model The model to be set.
     */

    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the type of the vehicle.
     *
     * @return The type of the vehicle.
     */

    public String getType() {
        return type;
    }

    /**
     * Sets the type of the vehicle.
     *
     * @param type The type to be set.
     */

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the rental contract associated with the vehicle.
     *
     * @return The rental contract of the vehicle.
     */

    public RentalDTO getRentalContract() {
        return rentalContract;
    }

    /**
     * Sets the rental contract associated with the vehicle.
     *
     * @param rentalContract The rental contract to be set.
     */

    public void setRentalContract(RentalDTO rentalContract) {
        this.rentalContract = rentalContract;
    }

    /**
     * Checks if the vehicle is available for rental.
     *
     * @return True if the vehicle is available; false otherwise.
     */

    public boolean isAvailable() {
        return !rentalContract.rentalStatus();
    }

    /**
     * Returns a string representation of the VehicleDTO object.
     *
     * @return A string representation of the object.
     */

    @Override
    public String toString() {
        return "License Plate: " + licensePlate +
               ", Model: " + model +
               ", Type: " + type +
               ", Available: " + isAvailable();
    }
}