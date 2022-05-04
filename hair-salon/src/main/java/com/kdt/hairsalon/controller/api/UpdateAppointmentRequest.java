package com.kdt.hairsalon.controller.api;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateAppointmentRequest(UUID appointmentId, UUID menuId,
                                       LocalDateTime appointedAt) {
}
