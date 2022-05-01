package com.kdt.gccoffee.controller.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UpdateAppointmentRequest {
    private final UUID appointmentId;
    private final UUID menuId;
    private final LocalDateTime appointedAt;
}
