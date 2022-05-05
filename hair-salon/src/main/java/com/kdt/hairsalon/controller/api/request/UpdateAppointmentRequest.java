package com.kdt.hairsalon.controller.api.request;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateAppointmentRequest(@NotBlank UUID appointmentId, @NotBlank UUID menuId,
                                       @Future LocalDateTime appointedAt) {
}
