package com.kdt.hairsalon.service.appointment;

import com.kdt.hairsalon.model.Appointment;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AppointmentDto {
    private final UUID appointmentId;
    private final UUID customerId;
    private final UUID menuId;
    private final UUID designerId;
    private final LocalDateTime appointedAt;

    public static AppointmentDto of(Appointment appointment) {
        return new AppointmentDto(appointment.getAppointmentId(),
                appointment.getCustomerId(),
                appointment.getMenuId(),
                appointment.getDesignerId(),
                appointment.getAppointedAt());
    }
}
