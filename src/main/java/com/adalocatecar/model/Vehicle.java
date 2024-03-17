package com.adalocatecar.model;

/**
 * Represents a vehicle entity with information such as license plate, model, type, and rental contract.
 */

public class Vehicle {
    private final String licensePlate;
    private String model;
    private String type;
    private Rental rentalContract;

    /**
     * Constructs a Vehicle object with the specified parameters.
     *
     * @param licensePlate The license plate of the vehicle.
     * @param model        The model of the vehicle.
     * @param type         The type of the vehicle.
     */

    public Vehicle(String licensePlate, String model, String type) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.type = type;
        this.rentalContract = new Rental();
    }

    /**
     * Gets the license plate of the vehicle.
     *
     * @return The license plate.
     */

    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Gets the model of the vehicle.
     *
     * @return The model.
     */

    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the vehicle.
     *
     * @param model The model to set.
     */

    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the type of the vehicle.
     *
     * @return The type.
     */

    public String getType() {
        return type;
    }

    /**
     * Sets the type of the vehicle.
     *
     * @param type The type to set.
     */

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the rental contract associated with the vehicle.
     *
     * @return The rental contract.
     */

    public Rental getRentalContract() {
        return rentalContract;
    }

    /**
     * Sets the rental contract associated with the vehicle.
     *
     * @param rentalContract The rental contract to set.
     */

    public void setRentalContract(Rental rentalContract) {
        this.rentalContract = rentalContract;
    }
}
