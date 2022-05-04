package com.kdt.hairsalon.service.appointment;

import com.kdt.hairsalon.repository.appointment.AppointmentWithNames;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    AppointmentDto make(UUID menuId, UUID customerId, UUID designerId, LocalDateTime appointedAt);

    List<AppointmentWithNames> findAll();

    AppointmentDto findByAppointmentId(UUID appointmentId);

    AppointmentDto findByCustomerId(UUID customerId);

    List<AppointmentDto> findByDesignerId(UUID designerId);

    void deleteByAppointmentId(UUID appointmentId);

    AppointmentDto updatedByAppointmentId(UUID appointmentId, LocalDateTime appointedAt);
}
