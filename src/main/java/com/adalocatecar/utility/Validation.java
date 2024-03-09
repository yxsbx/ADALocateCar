package com.adalocatecar.utility;

import java.util.List;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Validation {

    private static final Logger logger = Logger.getLogger(Validation.class.getName());

    public static void log(String message) {
        logger.log(Level.SEVERE, message);
    }

    // Main errors

    public static final String INVALID_OPTION_MESSAGE = "Invalid option. Please try again.";
    public static final String ERROR_OCCURRED_MESSAGE = "An error occurred: %s";

    public static String invalidOptionMessage() {
        return INVALID_OPTION_MESSAGE;
    }

    public static String errorOccurredMessage(String message) {
        return String.format(ERROR_OCCURRED_MESSAGE, message);
    }

    // Rental errors

    public static final String ERROR_CHECKING_RENTALS = "Error while checking rental records for client.";


    public static String errorCheckingRentals(String message) {
        return ERROR_CHECKING_RENTALS + " " + message;
    }



    // Generic repository errors

    public static final String ERROR_CREATING_FILE = "An error occurred while creating the file.";
    public static final String OBJECT_ALREADY_EXISTS = "Object already exists with ID: %s";
    public static final String ERROR_WHILE_CREATING_OBJECT = "An error occurred while creating the object.";
    public static final String ERROR_WHILE_UPDATING_OBJECT = "An error occurred while updating the object.";
    public static final String ERROR_WHILE_DELETING_OBJECT = "An error occurred while deleting the object.";
    public static final String ERROR_WHILE_FINDING_OBJECT_BY_ID = "An error occurred while finding the object by ID.";
    public static final String FILE_ALREADY_EXISTS_MESSAGE = "File already exists: %s";
    public static final String FILE_CREATED_MESSAGE = "File created: %s";

    public static boolean fileExists(File filePath) {
        return filePath.exists();
    }

    public static String fileAlreadyExists(String fileName) {
        return String.format(FILE_ALREADY_EXISTS_MESSAGE, fileName);
    }

    public static String fileCreated(String fileName) {
        return String.format(FILE_CREATED_MESSAGE, fileName);
    }

    public static String errorWhileCreatingFile() {
        return ERROR_CREATING_FILE;
    }

    public static <ID> String objectAlreadyExists(ID id) {
        return String.format(OBJECT_ALREADY_EXISTS, id);
    }

    public static String errorWhileCreatingObject() {
        return ERROR_WHILE_CREATING_OBJECT;
    }

    public static String errorWhileUpdatingObject() {
        return ERROR_WHILE_UPDATING_OBJECT;
    }

    public static String errorWhileDeletingObject() {
        return ERROR_WHILE_DELETING_OBJECT;
    }

    public static String errorWhileFindingObjectByID() {
        return ERROR_WHILE_FINDING_OBJECT_BY_ID;
    }

    // Client repository errors

    public static final String SUCCESS = "Success.";
    public static final String INVALID_ID_FORMAT = "Invalid ID format. Must be either a CPF with 11 digits or a CNPJ with 14 digits.";
    public static final String CLIENT_NOT_FOUND = "Client not found.";
    public static final String ERROR_CREATING_CLIENT = "An error occurred while creating the client.";
    public static final String ERROR_UPDATING_CLIENT = "An error occurred while updating the client.";
    public static final String ERROR_DELETING_CLIENT = "The client cannot be deleted because they have rented cars.";
    public static final String ERROR_FINDING_CLIENTS_BY_NAME = "An error occurred while finding clients by name: %s";
    public static final String ERROR_FINDING_ALL_CLIENTS = "An error occurred while finding all clients: %s";

    private boolean success;
    private String message;

    public Validation(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }


    public static String errorFindingAllClientsMessage(String errorMessage) {
        return String.format(ERROR_FINDING_ALL_CLIENTS, errorMessage);
    }

    public static String clientNotFoundMessage() {
        return CLIENT_NOT_FOUND;
    }

    public static Validation createValidation(boolean success, String message) {
        return new Validation(success, message);
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

    public static boolean isValidLicensePlate(String licensePlate){
        return licensePlate != null && licensePlate.matches("^[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}$");
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
    public static Validation validateLicensePlate(String licensePlate){
        return isValidLicensePlate(licensePlate) ? ok() : error("Invalid license plate format. Please enter the license plate in the correct format: ABC1D34");
    }
    public static Validation validateName(String name) {
        return isValidName(name) ? ok() : error("Invalid name format. Name must have at least 3 alphabetical characters.");
    }

    public static Validation validateBrand(String brand){
        return isValidName(brand) ? ok() : error("Invalid brand format. Brand must have at least 3 alphabetical characters.");
    }

    public static Validation validateType(String type){
        return isValidFormat(type,"^(small|Small|SMALL|medium|Medium|MEDIUM|SUV|Suv)$") ? ok() : error("Invalid vehicle type. The type must be Small, SUV, or Medium.");
    }

    public static Validation validateVehicleYear(int vehicleYear){
        String year = String.valueOf(vehicleYear);
        return isValidFormat(year,"^(19[0-9]{2}|20[0-9]{2})$") ? ok() : error("Car year is not valid. It must be a 4-digit number");
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

    public static boolean isUniqueLicensePlate(String licensePlate, List<String> existingLicensePlates){
        for(String existingLicensePlate : existingLicensePlates){
            if(licensePlate.equals(existingLicensePlate)){
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
    public static Validation validateUniqueLicensePlate(String licensePlate, List<String> licensePlates){
        return isUniqueLicensePlate(licensePlate,licensePlates) ? ok() : error("License Plate already registered");
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
