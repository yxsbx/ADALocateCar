package com.adalocatecar.dto;

import java.time.LocalDateTime;

public record RentalDTO(Boolean rentalStatus, String idClientWhoRented, String agencyLocal, LocalDateTime startDate) {
}