package com.kdt.hairsalon.repository.appointment;

import com.kdt.hairsalon.model.Appointment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository {
    Appointment insert(Appointment appointment);

    void deleteByAppointmentId(UUID appointmentId);

    Optional<Appointment> findByAppointmentId(UUID appointmentId);

    Optional<Appointment> findByCustomerId(UUID customerId);

    List<Appointment> findByDesignerId(UUID designerId);

    List<AppointmentWithNames> findAll();

    Appointment updateByAppointmentId(Appointment appointment);
}
