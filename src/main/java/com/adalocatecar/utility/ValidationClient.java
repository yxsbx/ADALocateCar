package com.adalocatecar.utility;

import java.util.Arrays;
import java.util.regex.Pattern;

public class ValidationClient {

    public static final String ERROR_FINDING_CLIENTS_BY_NAME = "An error occurred while finding clients by name: %s.";
    public static final String ERROR_FINDING_CLIENTS_BY_DOCUMENT = "An error occurred while finding clients by name: %s.";
    public static final String SUCCESS_MESSAGE = "Client updated successfully!";
    public static final String INVALID_ID_FORMAT = "Invalid ID format. Must be either a CPF with 11 digits or a CNPJ with 14 digits.";
    public static final String CLIENT_NOT_FOUND = "Client not found.";
    public static final String ERROR_HAS_RENTED = "The client cannot be deleted because they have rented cars.";
    public static final String DUPLICATE_CLIENT_ID = "A client with this ID already exists.";
    public static final String INVALID_NAME_FORMAT = "Invalid name format. Must contain only letters and have at least 3 characters.";

    public static String validateClientIdFormat(String id) {
        if (!Pattern.matches("\\d{11}|\\d{14}", id)) {
            return INVALID_ID_FORMAT;
        }
        return null;
    }

    public static String validateClientNameFormat(String name) {
        if (!Pattern.matches("^[A-Za-zÀ-ú ]{3,}$", name)) {
            return INVALID_NAME_FORMAT;
        }
        return "";
    }

    public enum OperationType {
        CREATE,
        UPDATE,
    }

    public static String validateClientOperation(String id, String[] existingIds, OperationType operationType) {
        boolean idExists = existingIds != null && Arrays.asList(existingIds).contains(id);

        switch (operationType) {
            case CREATE:
                if (idExists) {
                    return DUPLICATE_CLIENT_ID;
                }
                break;
            case UPDATE:
                if (!idExists) {
                    return CLIENT_NOT_FOUND;
                }
                break;
        }
        return SUCCESS_MESSAGE;
    }

    public static String validateDeletion(boolean hasRentedCars) {
        if (hasRentedCars) {
            return ERROR_HAS_RENTED;
        }
        return SUCCESS_MESSAGE;
    }

    public static String clientNotFound() {
        return CLIENT_NOT_FOUND;
    }

}