package com.kdt.hairsalon.controller.api.request;

import com.kdt.hairsalon.model.AppointmentStatus;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateAppointmentRequest(UUID appointmentId, UUID menuId, AppointmentStatus status,
                                       @Future(message = "잘못된 예약 시간입니다.") LocalDateTime appointedAt) {
}
