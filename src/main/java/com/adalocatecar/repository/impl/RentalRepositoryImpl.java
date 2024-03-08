package com.adalocatecar.repository.impl;

import com.adalocatecar.model.Rental;
import com.adalocatecar.repository.RentalRepository;

import java.io.File;

public class RentalRepositoryImpl extends GenericsRepositoryImpl <Rental, String> implements RentalRepository {
  private static final File filePath = new File("src/data/rentals.txt");

  public RentalRepositoryImpl() {
    super(filePath);
  }

  @Override
  protected String objectToString(Rental object) {
    return null;
  }

  @Override
  protected Rental stringToObject(String str) {
    return null;
  }

  @Override
  protected String getId(Rental entity) {
    return null;
  }
}
