package com.adalocatecar.utility;

public class ValidationResponse {
    public static final String SUCCESS = "Success.";
    public static final String FAILURE = "Validation failed.";
    public static final String CLIENT_ALREADY_EXISTS = "Client with this ID already exists.";
    public static final String INVALID_ID_FORMAT = "Invalid ID format. Must be either a CPF with 11 digits or a CNPJ with 14 digits.";
    public static final String CLIENT_NOT_FOUND = "Client not found.";
    public static final String CLIENT_UPDATED = "Client information updated successfully.";
    public static final String INVALID_NAME_FORMAT = "Invalid name format. Name must have at least 4 alphabetical characters.";


    public static ValidationResponse error(String errorMessage) {
        return new ValidationResponse(false, errorMessage);
    }

    public static ValidationResponse ok() {
        return new ValidationResponse(true, SUCCESS);
    }

    private final boolean success;
    private final String message;

    private ValidationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}