package com.adalocatecar.utility;

import com.adalocatecar.dto.VehicleDTO;
import com.adalocatecar.model.Vehicle;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class ValidationRentals {
    public static final String ERROR_CHECKING_RENTALS = "Error while checking rental records for client.";
    public static final String INVALID_RENTAL_DURATION = "The rental duration should be at least 1 hour.";
    public static final String VEHICLE_NOT_AVAILABLE = "The selected vehicle is not available for rental.";
    public static final String RETURN_LOCATION_MISMATCH = "The vehicle must be returned to the same location as the rental.";
    public static final String INVALID_RETURN_DATE = "The return date must be after the rental date.";
    public static final String INVALID_DURATION_FOR_DISCOUNT = "The rental duration is not sufficient to apply the discount.";
    public static final String CLIENT_NOT_FOUND = "Client not found.";
    public static final double DISCOUNT_FOR_INDIVIDUAL = 0.05;
    public static final double DISCOUNT_FOR_CORPORATE = 0.10;
    public static final double BASE_DAILY_RATE_SMALL = 100.00;
    public static final double BASE_DAILY_RATE_MEDIUM = 150.00;
    public static final double BASE_DAILY_RATE_SUV = 200.00;

    public static void validateClientIdFormat(String id) {
        if (!Pattern.matches("\\d{11}|\\d{14}", id)) {
        }
    }

    public static void validateRentalDuration(LocalDateTime rentalDateTime, LocalDateTime returnDateTime) {
        if (!rentalDateTime.isBefore(returnDateTime)) {
            throw new IllegalArgumentException(INVALID_RENTAL_DURATION);
        }
    }

    public static void validateVehicleAvailability(VehicleDTO vehicle) {
        if (!vehicle.isAvailable()) {
            throw new RuntimeException(VEHICLE_NOT_AVAILABLE);
        }
    }

    public static boolean validateReturnLocation(String rentalLocation, String returnLocation) {
        if (!rentalLocation.equals(returnLocation)) {
            throw new IllegalArgumentException(RETURN_LOCATION_MISMATCH);
        }
        return true;
    }

    public static void validateReturnDate(LocalDateTime rentalDateTime, LocalDateTime returnDateTime) {
        if (!returnDateTime.isAfter(rentalDateTime)) {
            throw new IllegalArgumentException(INVALID_RETURN_DATE);
        }
    }

    public static boolean validateDurationForDiscount(LocalDateTime rentalDateTime, LocalDateTime returnDateTime, String clientType) {
        long hours = Duration.between(rentalDateTime, returnDateTime).toHours();
        if (clientType.equalsIgnoreCase("Individual")) {
            if (!(hours > 5)) {
                throw new IllegalArgumentException(INVALID_DURATION_FOR_DISCOUNT);
            }
        } else if (clientType.equalsIgnoreCase("Corporate")) {
            if (!(hours > 3)) {
                throw new IllegalArgumentException(INVALID_DURATION_FOR_DISCOUNT);
            }
        }
        return true;
    }

    public static double calculateDiscountedRate(double baseDailyRate, String clientType) {
        double discount = 0.0;
        if (clientType.equalsIgnoreCase("Individual")) {
            discount = DISCOUNT_FOR_INDIVIDUAL;
        } else if (clientType.equalsIgnoreCase("Corporate")) {
            discount = DISCOUNT_FOR_CORPORATE;
        }
        return baseDailyRate * (1 - discount);
    }
}