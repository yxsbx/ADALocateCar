package com.adalocatecar.utility;

import java.util.List;
import java.util.regex.Pattern;

public class ValidationVehicle {

    public static final String ERROR_FINDING_VEHICLES_BY_NAME = "An error occurred while finding vehicles by license plate: %s.";
    public static final String SUCCESS_MESSAGE = "Vehicle updated successfully!";
    public static final String INVALID_MODEL = "Invalid model format.";
    public static final String INVALID_TYPE = "Invalid vehicle type. The type must be Small, SUV, or Medium.";
    public static final String DUPLICATED_LICENSE_PLATE = "License Plate already registered";
    public static final String INVALID_LICENSE_PLATE_FORMAT = "Invalid license plate format. Please enter the license plate in the correct format: ABC1D34";

    public enum OperationType {
        CREATE,
        UPDATE,
    }

    public static String validateUniqueLicensePlate(String licensePlate, List<String> licensePlates) {
        if (!isUniqueLicensePlate(licensePlate, licensePlates)) {
            return DUPLICATED_LICENSE_PLATE;
        }
        return "";
    }

    public static boolean isUniqueLicensePlate(String licensePlate, List<String> existingLicensePlates) {
        return !existingLicensePlates.contains(licensePlate);
    }

    public static String validateLicensePlate(String licensePlate) {
        if (!isValidLicensePlate(licensePlate)) {
            return INVALID_LICENSE_PLATE_FORMAT;
        }
        return "";
    }

    public static boolean isValidLicensePlate(String licensePlate) {
        return Pattern.matches("^[A-Z]{3}\\d{1}[A-Z]{1}\\d{2}$", licensePlate);
    }

    public static String validateModel(String model) {
        if (!isRequired(model)) {
            return INVALID_MODEL;
        }
        return "";
    }

    public static String validateType(String type) {
        if (!isValidType(type)) {
            return INVALID_TYPE;
        }
        return "";
    }

    public static boolean isValidType(String type) {
        return type.equalsIgnoreCase("Small") || type.equalsIgnoreCase("Medium") || type.equalsIgnoreCase("SUV");
    }

    public static boolean isRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidFormat(String value, String regex) {
        return value != null && Pattern.matches(regex, value);
    }

}
