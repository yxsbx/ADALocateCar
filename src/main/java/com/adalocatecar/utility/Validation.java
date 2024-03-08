package com.adalocatecar.utility;

import java.util.regex.Pattern;

public class Validation {

    public static ValidationMessages validateRequiredField(String value, String fieldName) {
        if (isRequired(value)) {
            return ValidationMessages.ok();
        } else {
            return ValidationMessages.error(String.format("The %s is required.", fieldName));
        }
    }

    private static boolean isRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static boolean isValidFormat(String value, String regex) {
        if (value == null) return false;
        return Pattern.matches(regex, value);
    }

    public static ValidationMessages validateFormat(String value, String regex, String fieldName) {
        if (isValidFormat(value, regex)) {
            return ValidationMessages.ok();
        } else {
            return ValidationMessages.error(ValidationMessages.INVALID_ID_FORMAT);
        }
    }

    public static ValidationMessages validateCPFOrCNPJ(String id) {
        return validateFormat(id, "\\d{11}|\\d{14}", "ID");
    }

    public static ValidationMessages validateName(String name) {
        if (isValidName(name)) {
            return ValidationMessages.ok();
        } else {
            return ValidationMessages.error(ValidationMessages.INVALID_NAME_FORMAT);
        }
    }

    private static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]{3,}");
    }

    public static ValidationMessages validateUniqueField(String value, String[] existingValues, String fieldName) {
        if (isUniqueField(value, existingValues)) {
            return ValidationMessages.ok();
        } else {
            return ValidationMessages.error(String.format("The %s is already in use.", fieldName));
        }
    }

    private static boolean isUniqueField(String value, String[] existingValues) {
        if (value == null) return true;
        for (String existingValue : existingValues) {
            if (value.equals(existingValue)) {
                return false;
            }
        }
        return true;
    }
}