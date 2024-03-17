package com.adalocatecar.dto;

import java.time.LocalDateTime;

/**
 * Represents a rental contract DTO (Data Transfer Object) that contains information about a vehicle rental.
 */

public record RentalDTO(Boolean rentalStatus, String idClientWhoRented, String agencyLocal, LocalDateTime startDate) {
}