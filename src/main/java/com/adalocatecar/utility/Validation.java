package com.adalocatecar.utility;

import com.adalocatecar.dto.ClientDTO;
import java.util.regex.Pattern;

public class Validation {

    public static ValidationResponse validateRequiredField(String value, String fieldName) {
        if (isRequired(value)) {
            return ValidationResponse.ok();
        } else {
            return ValidationResponse.error(String.format("The %s is required.", fieldName));
        }
    }

    public static ValidationResponse validateFormat(String value, String regex, String fieldName) {
        if (isValidFormat(value, regex)) {
            return ValidationResponse.ok();
        } else {
            return ValidationResponse.error(String.format("The %s format is invalid.", fieldName));
        }
    }

    public static ValidationResponse validateFieldLength(String value, int maxLength, String fieldName) {
        if (isFieldWithinLength(value, maxLength)) {
            return ValidationResponse.ok();
        } else {
            return ValidationResponse.error(String.format("The %s exceeds the maximum length of %d.", fieldName, maxLength));
        }
    }

    public static ValidationResponse validateUniqueField(String value, String[] existingValues, String fieldName) {
        if (isUniqueField(value, existingValues)) {
            return ValidationResponse.ok();
        } else {
            return ValidationResponse.error(String.format("The %s is already in use.", fieldName));
        }
    }

    public static ValidationResponse validateBusinessRule(ClientDTO client) {
        if (isBusinessRuleSatisfied(client)) {
            return ValidationResponse.ok();
        } else {
            return ValidationResponse.error("The business rule is not satisfied.");
        }
    }

    private static boolean isRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static boolean isValidFormat(String value, String regex) {
        if (value == null) return false;
        return Pattern.matches(regex, value);
    }

    private static boolean isFieldWithinLength(String value, int maxLength) {
        if (value == null) return true;
        return value.length() <= maxLength;
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

    private static boolean isBusinessRuleSatisfied(ClientDTO client) {
        return client.getName() != null && client.getName().length() >= 3;
    }
}