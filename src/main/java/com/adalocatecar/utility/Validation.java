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

    private Validation(boolean success, String message) {
        Validation.success = success;
    }

    public static Validation createValidation(boolean success, String message) {
        return new Validation(success, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public static boolean isRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidFormat(String value, String regex) {
        return value != null && Pattern.matches(regex, value);
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]{3,}");
    }

    public static boolean isUniqueField(String value, String[] existingValues) {
        if (value == null) return true;
        for (String existingValue : existingValues) {
            if (value.equals(existingValue)) {
                return false;
            }
        }
        return true;
    }

    public static Validation validateRequiredField(String value, String fieldName) {
        return isRequired(value) ? ok() : error("The %s is required.", fieldName);
    }

    public static Validation validateFormat(String value, String regex, String fieldName) {
        return isValidFormat(value, regex) ? ok() : error("Invalid format for %s.", fieldName);
    }

    public static Validation validateCPFOrCNPJ(String id) {
        return validateFormat(id, "\\d{11}|\\d{14}", "ID");
    }

    public static Validation validateName(String name) {
        return isValidName(name) ? ok() : error("Invalid name format. Name must have at least 4 alphabetical characters.");
    }

    public static Validation validateUniqueField(String value, String[] existingValues, String fieldName) {
        return isUniqueField(value, existingValues) ? ok() : error("The %s is already in use.", fieldName);
    }

    public static boolean isValidClientId(String id) {
        return id != null && Pattern.matches("\\d{11}|\\d{14}", id);
    }

    public static boolean isUniqueClientId(String id, String[] existingIds) {
        for (String existingId : existingIds) {
            if (id.equals(existingId)) {
                return false;
            }
        }
        return true;
    }

    public static Validation validateClientName(String name) {
        return isValidName(name) ? ok() : error("Invalid name format. Name must have at least 4 alphabetical characters.");
    }

    public static Validation validateClientId(String id) {
        return isValidClientId(id) ? ok() : error(INVALID_ID_FORMAT);
    }

    public static Validation validateUniqueClientId(String id, boolean isNewClient, String[] existingIds) {
        return (isNewClient || isUniqueClientId(id, existingIds)) ? ok() : error("Client with this ID already exists.");
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

    public static Validation clientNotFound() {
        return error(CLIENT_NOT_FOUND);
    }

    public static Validation ok() {
        return createValidation(true, SUCCESS);
    }

    public static Validation error(String errorMessage, Object... params) {
        return createValidation(false, String.format(errorMessage, params));
    }
}