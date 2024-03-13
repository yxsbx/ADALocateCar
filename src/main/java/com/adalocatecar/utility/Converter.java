package com.adalocatecar.utility;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;
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
        return  new Vehicle(vehicleDTO.getLicensePlate(),vehicleDTO.getModel(),vehicleDTO.getType(),vehicleDTO.getRentalContract());
    }

    public static ClientDTO convertToDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getClientType());
    }

    public static VehicleDTO convertToDTO(Vehicle vehicle){
        return new VehicleDTO(vehicle.getLicensePlate(), vehicle.getModel(), vehicle.getType(), vehicle.getRentalContract());
    }

    public static List<VehicleDTO> convertToDTOList(List<Vehicle> all) {
        List<VehicleDTO> dtos = new ArrayList<>();
        for (Vehicle vehicle : all) {
            dtos.add(convertToDTO(vehicle));
        }
        return Collections.unmodifiableList(dtos);
    }
}
