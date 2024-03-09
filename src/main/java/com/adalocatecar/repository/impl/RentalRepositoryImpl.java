package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Rental;
import com.adalocatecar.repository.RentalRepository;
import com.adalocatecar.utility.FileHandler;
import com.adalocatecar.utility.Validation;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RentalRepositoryImpl extends GenericsRepositoryImpl<Rental, String> implements RentalRepository {

  private static final File filePath = new File("src/data/rentals.txt");
  private final RentalRepository rentalRepository;
  private static final Logger logger = Logger.getLogger(RentalRepositoryImpl.class.getName());

  public RentalRepositoryImpl(RentalRepository rentalRepository) {
    super(filePath);
    this.rentalRepository = rentalRepository;
  }

  @Override
  protected String objectToString(Rental object) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return String.format("%s,%s,%s,%s",
            object.getId(),
            object.getClientId(),
            object.getVehicleId(),
            dateFormat.format(object.getRentalDate()));
  }

  @Override
  protected Rental stringToObject(String str) {
    String[] parts = str.split(",");
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date rentalDate = dateFormat.parse(parts[3]);
      return new Rental(parts[0], parts[1], parts[2], rentalDate);
    } catch (ParseException e) {
      logger.log(Level.SEVERE, "Error while parsing rental record", e);
      return null;
    }
  }

  @Override
  protected String getId(Rental entity) {
    return entity.getId();
  }

  @Override
  protected boolean hasRentedCars(String id) {
    List<Rental> rentalRecords = rentalRepository.findByClientId(id);
    return !rentalRecords.isEmpty();
  }

  @Override
  public List<Rental> findByClientId(String clientId) {
    List<Rental> rentals = new ArrayList<>();
    List<String> lines = FileHandler.readFromFile(filePath.getAbsolutePath());
    for (String line : lines) {
      Rental rental = stringToObject(line);
      if (rental.getClientId().equals(clientId)) {
        rentals.add(rental);
      }
    }
    return rentals;
  }
}
