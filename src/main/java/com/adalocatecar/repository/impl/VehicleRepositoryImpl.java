package com.adalocatecar.repository.impl;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Vehicle;
import com.adalocatecar.repository.VehicleRepository;
import java.io.File;
import java.util.List;

public class VehicleRepositoryImpl extends GenericsRepositoryImpl <Vehicle, String> implements VehicleRepository {

  private static final File filePath = new File("src/data/vehicles.txt");

  public VehicleRepositoryImpl() {
    super(filePath);
  }

  @Override
  public VehicleDTO findByLicensePlate(String licensePlate) {
    return null;
  }

  @Override
  public List<VehicleDTO> findByType(String type) {
    return null;
  }

  @Override
  public List<VehicleDTO> findByModel(String model) {
    return null;
  }

  @Override
  public List<VehicleDTO> findByYear(int year) {
    return null;
  }

  @Override
  protected Vehicle stringToObject(String str) {
    return null;
  }

  @Override
  protected String objectToString(Vehicle object) {
    return null;
  }

  @Override
  protected String getId(Vehicle entity) {
    return entity.getLicensePlate();
  }
}

