package com.adalocatecar.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a client entity with information such as ID, name, client type, and rented vehicles.
 */

public class Client {
    private final String id;
    private final String name;
    private String clientType;
    private final List<String> rentedVehiclesPlates;

    /**
     * Constructs a Client object with the specified parameters.
     *
     * @param id    The ID of the client.
     * @param name  The name of the client.
     * @param type  The type of the client (e.g., individual, corporate).
     */

    public Client(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.clientType = type;
        this.rentedVehiclesPlates = new ArrayList<>();
    }

    /**
     * Gets the ID of the client.
     *
     * @return The ID of the client.
     */

    public String getId() {
        return id;
    }

    /**
     * Gets the name of the client.
     *
     * @return The name of the client.
     */

    public String getName() {
        return name;
    }

    /**
     * Gets the type of the client.
     *
     * @return The client type (e.g., individual, corporate).
     */

    public String getClientType() {
        return clientType;
    }

    /**
     * Sets the type of the client.
     *
     * @param clientType The client type to set (e.g., individual, corporate).
     */

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    /**
     * Adds a rented vehicle to the client's list of rented vehicle plates.
     *
     * @param vehiclePlate The license plate of the rented vehicle to add.
     */

    public void addRentedVehicle(String vehiclePlate) {
        this.rentedVehiclesPlates.add(vehiclePlate);
    }

    /**
     * Gets the list of rented vehicle plates associated with the client.
     *
     * @return The list of rented vehicle plates.
     */

    public List<String> getRentedVehiclesPlates() {
        return rentedVehiclesPlates;
    }
}
