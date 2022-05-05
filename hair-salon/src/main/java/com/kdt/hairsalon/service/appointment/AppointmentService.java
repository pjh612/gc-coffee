package com.kdt.hairsalon.service.appointment;

import com.kdt.hairsalon.model.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    AppointmentDto make(UUID menuId, UUID customerId, UUID designerId, LocalDateTime appointedAt);

    List<AppointmentDto> findAll();

    AppointmentDto findByAppointmentId(UUID appointmentId);

    AppointmentDto findByCustomerId(UUID customerId);

    List<AppointmentDto> findByDesignerId(UUID designerId);

    List<AppointmentDto> findByStatus(AppointmentStatus status);

    void deleteByAppointmentId(UUID appointmentId);

    AppointmentDto updateByAppointmentId(UUID appointmentId, AppointmentStatus status, LocalDateTime appointedAt);
}
