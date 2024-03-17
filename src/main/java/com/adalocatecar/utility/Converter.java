package com.adalocatecar.utility;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.model.Rental;
import com.adalocatecar.model.Vehicle;

public class Converter {

    public static Client convertToEntity(ClientDTO clientDTO) {
        Client client = new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getClientType());
        clientDTO.getRentedVehiclesPlates().forEach(client::addRentedVehicle);
        return client;
    }

    public static Vehicle convertToEntity(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle(vehicleDTO.getLicensePlate(), vehicleDTO.getModel(), vehicleDTO.getType());
        if (vehicleDTO.getRentalContract() != null) {
            vehicle.setRentalContract(convertToEntity(vehicleDTO.getRentalContract()));
        }
        return vehicle;
    }

    public static ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO(client.getId(), client.getName(), client.getClientType());
        client.getRentedVehiclesPlates().forEach(clientDTO::addRentedVehicle);
        return clientDTO;
    }

    public static VehicleDTO convertToDTO(Vehicle vehicle) {
        RentalDTO rentalDTO = null;
        if (vehicle.getRentalContract() != null) {
            rentalDTO = convertToDTO(vehicle.getRentalContract());
        }
        return new VehicleDTO(vehicle.getLicensePlate(), vehicle.getModel(), vehicle.getType(), rentalDTO);
    }

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

    public static Rental convertToEntity(RentalDTO rentalDTO) {
        Rental rental = new Rental();
        rental.setRentalStatus(rentalDTO.rentalStatus());
        rental.setIdClientWhoRented(rentalDTO.idClientWhoRented());
        rental.setAgencyLocal(rentalDTO.agencyLocal());
        rental.setStartDate(rentalDTO.startDate());
        return rental;
    }
}

