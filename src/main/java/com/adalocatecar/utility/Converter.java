package com.adalocatecar.utility;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.dto.RentalDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.model.Rental;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.dto.VehicleDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Converter {

    public static Client convertToEntity(ClientDTO clientDTO) {
        return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getType());
    }

    public static Vehicle convertToEntity(VehicleDTO vehicleDTO){
        Vehicle vehicle = new Vehicle(vehicleDTO.getLicensePlate(),vehicleDTO.getModel(),vehicleDTO.getType());
        vehicle.setRentalContract(Converter.convertToEntity(vehicleDTO.getRentalContract()));
        return vehicle;
    }

    public static ClientDTO convertToDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getClientType());
    }

    public static VehicleDTO convertToDTO(Vehicle vehicle){
        return new VehicleDTO(
                vehicle.getLicensePlate(),
                vehicle.getModel(),
                vehicle.getType(),
                convertToDTO(vehicle.getRentalContract())
        );
    }

    public static RentalDTO convertToDTO(Rental rental){
        return new RentalDTO(
                rental.getRentalStatus(),
                convertToDTO(rental.getClientWhoRented()),
                rental.getAgencyLocal(),
                rental.getStartDate(),
                rental.getExpectedEndDate()
        );
    }

    public static Rental convertToEntity (RentalDTO rentalDTO){
        Rental rental = new Rental();
        rental.setRentalStatus(rentalDTO.getRentalStatus());
        rental.setClientWhoRented(Converter.convertToEntity(rentalDTO.getClientWhoRented()));
        rental.setAgencyLocal(rentalDTO.getAgencyLocal());
        rental.setStartDate(rentalDTO.getStartDate());
        rental.setExpectedEndDate(rentalDTO.getExpectedEndDate());
        return rental;
    }

    public static List<VehicleDTO> convertToDTOList(List<Vehicle> all) {
        List<VehicleDTO> dtos = new ArrayList<>();
        for (Vehicle vehicle : all) {
            dtos.add(convertToDTO(vehicle));
        }
        return Collections.unmodifiableList(dtos);
    }
}
