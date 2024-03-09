package com.adalocatecar.utility;

import java.util.regex.Pattern;

public class Validation {

    public static final String SUCCESS = "Success.";
    public static final String INVALID_ID_FORMAT = "Invalid ID format. Must be either a CPF with 11 digits or a CNPJ with 14 digits.";
    public static final String CLIENT_NOT_FOUND = "Client not found.";
    public static final String ERROR_CREATING_CLIENT = "An error occurred while creating the client.";
    public static final String ERROR_UPDATING_CLIENT = "An error occurred while updating the client.";
    public static final String ERROR_DELETING_CLIENT = "The client cannot be deleted because they have rented cars.";
    public static final String ERROR_FINDING_CLIENTS_BY_NAME = "An error occurred while finding clients by name: %s";

    private static boolean success;
    private String message;

    public Validation(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static Validation createValidation(boolean success, String message) {
        return new Validation(success, message);
    }

    public static boolean isValidCPFOrCNPJ(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }

        String cleanId = id.replaceAll("\\D", "");

        return cleanId.length() == 11 || cleanId.length() == 14;
    }


    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public static Validation validateRequiredField(String value, String fieldName) {
        return isRequired(value) ? ok("Vehicle rented successfully.") : error("The %s is required.", fieldName);
    }

    public static Validation validateFormat(String value, String regex, String fieldName) {
        return isValidFormat(value, regex) ? ok("Vehicle rented successfully.") : error("Invalid format for %s.", fieldName);
    }

    public static Validation validateCPFOrCNPJ(String id) {
        return validateFormat(id, "\\d{11}|\\d{14}", "ID");
    }

    public static Validation validateName(String name) {
        return isValidName(name) ? ok("Vehicle rented successfully.") : error("Invalid name format. Name must have at least 4 alphabetical characters.");
    }

    public static Validation validateUniqueClientId(String id, boolean isNewClient, String[] existingIds) {
        return (isNewClient || isUniqueClientId(id, existingIds)) ? ok("Vehicle rented successfully.") : error("Client with this ID already exists.");
    }

    public static Validation errorCreatingClient() {
        return error(ERROR_CREATING_CLIENT);
    }

    public static Validation errorUpdatingClient() {
        return error(ERROR_UPDATING_CLIENT);
    }

    public static Validation errorDeletingClient() {
        return error(ERROR_DELETING_CLIENT);
    }

    public static Validation ok(String s) {
        return createValidation(true, SUCCESS);
    }

    public static Validation error(String errorMessage, Object... params) {
        return createValidation(false, String.format(errorMessage, params));
    }

    private static boolean isRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static boolean isValidFormat(String value, String regex) {
        return value != null && Pattern.matches(regex, value);
    }

    private static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]{3,}");
    }

    private static boolean isUniqueClientId(String id, String[] existingIds) {
        if (id == null) return true;
        for (String existingId : existingIds) {
            if (id.equals(existingId)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateLicensePlate(String licensePlate) {
        String regex = "[A-Z]{3}-\\d{4}";
        return Pattern.matches(regex, licensePlate);
    }

    public static boolean validateBrand(String brand) {
        return !brand.isEmpty();
    }

    public static boolean validateType(String type) {
        return !type.isEmpty();
    }

    public static boolean validateVehicleYear(int year) {
        return year > 0;
    }
}
