package com.adalocatecar.utility;

import com.adalocatecar.dto.ClientDTO;
import com.adalocatecar.model.Client;

public class Converter {

    public static Client convertToEntity(ClientDTO clientDTO) {
        return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getType());
    }

    public static ClientDTO convertToDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getType());
    }
}
