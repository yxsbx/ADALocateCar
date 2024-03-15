package com.adalocatecar.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ValidationInput {
    public static boolean validateClientIdFormat(String id) {
        if (Pattern.matches("\\d{11}|\\d{14}", id)) {
            return true;
        }
        System.err.println("Invalid client id format.");
        return false;

    }

    public static boolean isValidLicensePlate(String licensePlate) {
        if (Pattern.matches("[A-Z]{3}\\d{1}[A-Z]{1}\\d{2}", licensePlate)) {
            return true;
        }
        System.err.println("Invalid license plate format.");
        return false;
    }

    public static boolean isValidLocation(String agencyLocation) {
        if (Pattern.matches("[a-zA-Z\\s]+", agencyLocation)) {
            return true;
        }
        System.err.println("Invalid location format.");
        return false;
    }

    public static LocalDateTime isValidDate(String date) {
        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please enter dates in the format dd-MM-yyyyTHH:mm.");
            return null;
        }
    }

    public static boolean isValidClientNameFormat(String name) {
        if (name.length() >= 3 && Pattern.matches("[a-zA-Z0-9\\s]+", name)) {
            return true;
        }
        System.err.println("Invalid client name format.");
        return false;
    }

    public static String validateQuerySearchType(String query) {
        if (Pattern.matches("\\d{11}|\\d{14}", query)) {
            return "document";
        } else if (Pattern.matches("[a-zA-Z0-9\\s]+", query)) {
            return "name";
        } else {
            System.err.println("Invalid query format.");
            return null;
        }
    }
}
