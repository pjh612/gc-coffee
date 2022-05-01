package com.kdt.gccoffee.service.appointment;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AppointmentService {

    AppointmentDto make(UUID menuId, UUID customerId, UUID designerId, LocalDateTime appointedAt);

    AppointmentDto findByAppointmentId(UUID appointmentId);

    AppointmentDto findByCustomerId(UUID customerId);

    AppointmentDto findByDesignerId(UUID designerId);
    void deleteByAppointmentId(UUID appointmentId);

    AppointmentDto updatedByAppointmentId(UUID appointmentId, UUID menuId, LocalDateTime appointedAt);
}
