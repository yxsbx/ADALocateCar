/*
package com.adalocatecar.repository.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Client;
import com.adalocatecar.model.Rental;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.ClientRepository;
import com.adalocatecar.repository.RentalRepository;
import com.adalocatecar.utility.FileHandler;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RentalRepositoryImpl extends GenericsRepositoryImpl<Rental, String> implements RentalRepository {
    private static final File filePath = new File("src/data/rentals.txt");
    private final ClientRepository clientRepository = new ClientRepositoryImpl();
    public RentalRepositoryImpl() {
        super(filePath);
    }

    @Override
    protected String objectToString(Rental rental) {
        return String.join(",", rental.getClientWhoRented().getId(),
                rental.getRentedCars().stream().map(Vehicle::getLicensePlate).collect(Collectors.joining(",")),
                rental.getAgencyLocal(),
                rental.getStartDate().toString(),
                rental.getExpectedEndDate().toString());
    }

    @Override
    protected Rental stringToObject(String str) {
        try {
            String[] parts = str.split(",");
            String clientId = parts[0];
            String carIds = parts[1];
            String agencyLocal = parts[2];
            String startDateStr = parts[3];
            String endDateStr = parts[4];

            LocalDateTime startDate = DateTimeUtils.convertStringToLocalDateTime(startDateStr);
            LocalDateTime endDate = DateTimeUtils.convertStringToLocalDateTime(endDateStr);

            Optional<Client> client = clientRepository.findById(clientId);

            Rental newRental = new Rental(client.get(), agencyLocal, startDate, endDate);

            String[] carIdArray = carIds.split(",");
            for (String carId : carIdArray) {
                Vehicle vehicle = new Vehicle(carId, null, null, 0);
                newRental.addRentedCars(vehicle);
            }

            return newRental;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class DateTimeUtils {
        public static LocalDateTime convertStringToLocalDateTime(String dateTimeStr) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        }
    }
    public VehicleDTO getVehicle(String licensePlate) {
        List<Rental> rentedCars = findAll();

        for (Rental rentedVehicle : rentedCars) {
            for (Vehicle vehicle : rentedVehicle.getRentedCars()) {
                if (vehicle.getLicensePlate().equals(licensePlate)) {
                    VehicleDTO vehicleDTO = new VehicleDTO(
                            vehicle.getLicensePlate(),
                            vehicle.getModel(),
                            vehicle.getType(),
                            vehicle.getYear()
                    );
                    return vehicleDTO;
                }
            }
        }
        return null;
    }

    @Override
    protected String getId(Rental rental) {
        return rental.getClientWhoRented().getId();
    }

    @Override
    protected String getName(Rental entity) {
        return null;
    }

    @Override
    public List<Rental> findRentalsByClientId(String clientId) {
        List<Rental> matchingRentals = new ArrayList<>();
        try {
            List<String> lines = FileHandler.readFromFile(filePath.getAbsolutePath());

            for (String line : lines) {
                String[] parts = line.split(",");
                String clientIDFromRental = parts[0];
                if (clientIDFromRental.equals(clientId)) {
                    Rental rental = stringToObject(line);
                    matchingRentals.add(rental);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchingRentals;
    }
}*/
