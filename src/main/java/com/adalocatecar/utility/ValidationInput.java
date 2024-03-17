package com.adalocatecar.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class for input validation and constant error/success messages used in the application.
 */

public class ValidationInput {

    // Constants for error messages for client

    public static final String CLIENT_NOT_FOUND = "Client not found.";
    public static final String INVALID_ID_FORMAT = "Invalid ID format. Must be either a CPF with 11 digits or a CNPJ with 14 digits.";
    public static final String INVALID_NAME_FORMAT = "Invalid name format. Must contain at least 3 characters.";
    public static final String ERROR_HAS_RENTED = "The client cannot be deleted because they have active rental agreements.";

    // Constants for regex patterns for client

    private static final String ID_PATTERN = "\\d{11}|\\d{14}";
    private static final String NAME_PATTERN = "[a-zA-Z0-9\\s]+";

    // Constants for error messages for vehicle

    public static final String VEHICLE_NOT_FOUND = "Vehicle not found.";
    public static final String INVALID_LICENSE_PLATE_FORMAT = "Invalid license plate format.";
    public static final String DUPLICATED_LICENSE_PLATE = "License Plate already registered";
    public static final String INVALID_MODEL_FORMAT = "Invalid model format.";
    public static final String INVALID_TYPE = "Invalid vehicle type. The type must be Small, SUV, or Medium.";
    public static final String ERROR_IS_RENTED = "This vehicle is currently rented and cannot be deleted.";
    public static final String INVALID_QUERY_FORMAT = "Invalid query format.";

    // Constants for regex patterns for vehicle

    private static final String LICENSE_PLATE_PATTERN = "[A-Z]{3}\\d[A-Z]\\d{2}";

    // Constants for error messages for rental

    public static final String INVALID_LOCATION_FORMAT = "Invalid location format.";
    public static final String INVALID_DATE_FORMAT = "Invalid date format. Please enter dates in the format yyyy-MM-dd HH:mm.";
    public static final String VEHICLE_NOT_AVAILABLE = "The selected vehicle is not available for rental.";
    public static final String INVALID_RETURN_DATE = "The return date must be after the rental date.";
    public static final String VEHICLE_NOT_RENTED = "This vehicle is not rented.";

    // Constants for regex patterns for rental

    private static final String LOCATION_PATTERN = "[a-zA-Z\\s]+";
    public static final double DISCOUNT_FOR_INDIVIDUAL = 0.05;
    public static final double DISCOUNT_FOR_CORPORATE = 0.10;
    public static final double BASE_DAILY_RATE_SMALL = 100.00;
    public static final double BASE_DAILY_RATE_MEDIUM = 150.00;
    public static final double BASE_DAILY_RATE_SUV = 200.00;

    // Constants for success messages

    public static final String CLIENT_CREATED = "Client created successfully!";
    public static final String CLIENT_UPDATED = "Client updated successfully!";
    public static final String CLIENT_DELETED = "Client deleted successfully!";
    public static final String VEHICLE_CREATED = "Vehicle created successfully!";
    public static final String VEHICLE_UPDATED = "Vehicle updated successfully!";
    public static final String VEHICLE_DELETED = "Vehicle deleted successfully!";
    public static final String SUCCESS_RENTED = "Vehicle rented successfully!";

    // Validations for clients

    /**
     * Validates if the client name is valid.
     *
     * @param name The name to validate.
     * @return True if the name is valid, false otherwise.
     */

    public static boolean isValidClientName(String name) {
        return name.length() >= 3 && Pattern.matches(NAME_PATTERN, name);
    }

    /**
     * Validates if the client ID is valid.
     *
     * @param id The ID to validate.
     * @return True if the ID is valid, false otherwise.
     */

    public static boolean isValidClientId(String id) {
        return Pattern.matches(ID_PATTERN, id);
    }

    // Validations for vehicles

    /**
     * Validates if the license plate is in a valid format.
     *
     * @param licensePlate The license plate to validate.
     * @return True if the license plate is valid, false otherwise.
     */

    public static boolean isValidLicensePlate(String licensePlate) {
        return Pattern.matches(LICENSE_PLATE_PATTERN, licensePlate.toUpperCase());
    }

    /**
     * Checks if a license plate is unique among existing ones.
     *
     * @param licensePlate          The license plate to check.
     * @param existingLicensePlates List of existing license plates.
     * @return True if the license plate is unique, false otherwise.
     */

    public static boolean isUniqueLicensePlate(String licensePlate, List<String> existingLicensePlates) {
        return !existingLicensePlates.contains(licensePlate);
    }

    /**
     * Validates if the model of a vehicle is valid.
     *
     * @param model The model to validate.
     * @return True if the model is valid, false otherwise.
     */

    public static boolean isValidModel(String model) {
        return !model.isEmpty();
    }

    /**
     * Validates if the type of a vehicle is valid.
     *
     * @param type The type to validate.
     * @return True if the type is valid, false otherwise.
     */

    public static boolean isValidType(String type) {
        return type.equalsIgnoreCase("Small") || type.equalsIgnoreCase("Medium") || type.equalsIgnoreCase("SUV");
    }

    /**
     * Validates the query search type based on the input.
     *
     * @param query The input query to validate.
     * @return The search type ("document", "licensePlate", "name") if valid, or an error message.
     */

    public static String validateQuerySearchType(String query) {
        if (Pattern.matches(ID_PATTERN, query)) {
            return "document";
        } else if (Pattern.matches(LICENSE_PLATE_PATTERN, query)) {
            return "licensePlate";
        } else if (Pattern.matches(NAME_PATTERN, query)) {
            return "name";
        }
        return INVALID_QUERY_FORMAT;
    }

    // Validations for rental

    /**
     * Validates if the location format is valid.
     *
     * @param location The location to validate.
     * @return True if the location format is valid, false otherwise.
     */

    public static boolean isValidLocation(String location) {
        return Pattern.matches(LOCATION_PATTERN, location);
    }

    /**
     * Validates if the date format is valid and converts it to LocalDateTime.
     *
     * @param date The date to validate and parse.
     * @return LocalDateTime object if the date format is valid, or null if parsing fails.
     */

    public static LocalDateTime isValidDate(String date) {
        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Validates the availability of a vehicle for rental.
     *
     * @param isAvailable Flag indicating if the vehicle is available.
     * @return True if the vehicle is available, false otherwise.
     */

    public static boolean validateVehicleAvailability(boolean isAvailable) {
        return isAvailable;
    }
}
