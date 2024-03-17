package com.adalocatecar.utility;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.model.Rental;
import com.adalocatecar.model.Vehicle;

public class Converter {

    /**
     * Converts a ClientDTO object to a Client entity.
     *
     * @param clientDTO The ClientDTO object to be converted.
     * @return A Client entity converted from the ClientDTO object.
     */

    public static Client convertToEntity(ClientDTO clientDTO) {
        Client client = new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getClientType());
        clientDTO.getRentedVehiclesPlates().forEach(client::addRentedVehicle);
        return client;
    }

    /**
     * Converts a VehicleDTO object to a Vehicle entity.
     *
     * @param vehicleDTO The VehicleDTO object to be converted.
     * @return A Vehicle entity converted from the VehicleDTO object.
     */

    public static Vehicle convertToEntity(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle(vehicleDTO.getLicensePlate(), vehicleDTO.getModel(), vehicleDTO.getType());
        if (vehicleDTO.getRentalContract() != null) {
            vehicle.setRentalContract(convertToEntity(vehicleDTO.getRentalContract()));
        }
        return vehicle;
    }

    /**
     * Converts a RentalDTO object to a Rental entity.
     *
     * @param rentalDTO The RentalDTO object to be converted.
     * @return A Rental entity converted from the RentalDTO object.
     */

    public static Rental convertToEntity(RentalDTO rentalDTO) {
        Rental rental = new Rental();
        rental.setRentalStatus(rentalDTO.rentalStatus());
        rental.setIdClientWhoRented(rentalDTO.idClientWhoRented());
        rental.setAgencyLocal(rentalDTO.agencyLocal());
        rental.setStartDate(rentalDTO.startDate());
        return rental;
    }

    /**
     * Converts a Client entity to a ClientDTO object.
     *
     * @param client The Client entity to be converted.
     * @return A ClientDTO object converted from the Client entity.
     */

    public static ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO(client.getId(), client.getName(), client.getClientType());
        client.getRentedVehiclesPlates().forEach(clientDTO::addRentedVehicle);
        return clientDTO;
    }

    /**
     * Converts a Vehicle entity to a VehicleDTO object.
     *
     * @param vehicle The Vehicle entity to be converted.
     * @return A VehicleDTO object converted from the Vehicle entity.
     */

    public static VehicleDTO convertToDTO(Vehicle vehicle) {
        RentalDTO rentalDTO = null;
        if (vehicle.getRentalContract() != null) {
            rentalDTO = convertToDTO(vehicle.getRentalContract());
        }
        return new VehicleDTO(vehicle.getLicensePlate(), vehicle.getModel(), vehicle.getType(), rentalDTO);
    }

    /**
     * Converts a Rental entity to a RentalDTO object.
     *
     * @param rental The Rental entity to be converted.
     * @return A RentalDTO object converted from the Rental entity.
     */

    public static RentalDTO convertToDTO(Rental rental) {
        if (rental == null) {
            return null;
        }
        return new RentalDTO(rental.isRentalStatus(),
                rental.getIdClientWhoRented(),
                rental.getAgencyLocal(),
                rental.getStartDate()
        );
    }
}

