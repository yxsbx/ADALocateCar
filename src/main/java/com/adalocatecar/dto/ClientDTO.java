package com.adalocatecar.dto;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO {
    private final String id;
    private final String name;
    private final String clientType;
    private final List<String> rentedVehiclesPlates;

    /**
     * Constructs a ClientDTO object with the specified ID, name, and client type.
     *
     * @param id         The ID of the client.
     * @param name       The name of the client.
     * @param clientType The type of the client (e.g., individual or corporate).
     */

    public ClientDTO(String id, String name, String clientType) {
        this.id = id;
        this.name = name;
        this.clientType = clientType;
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
     * @return The type of the client.
     */

    public String getClientType() {
        return clientType;
    }

    /**
     * Gets the list of license plates of the vehicles rented by the client.
     *
     * @return The list of license plates of rented vehicles.
     */

    public List<String> getRentedVehiclesPlates() {
        return rentedVehiclesPlates;
    }

    /**
     * Adds a rented vehicle's license plate to the client's list of rented vehicles.
     *
     * @param vehicleParte The license plate of the rented vehicle to be added.
     */

    public void addRentedVehicle(String vehicleParte) {
        this.rentedVehiclesPlates.add(vehicleParte);
    }

    /**
     * Returns a string representation of the ClientDTO object.
     *
     * @return A string representation of the object.
     */

    @Override
    public String toString() {
        return "Document: " + getId() +
               ", Name: " + name +
               ", Client type: " + clientType +
               "\nRented Vehicles Plates: " + String.join(",", rentedVehiclesPlates);
    }
}
