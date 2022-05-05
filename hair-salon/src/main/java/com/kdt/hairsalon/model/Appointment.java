package com.kdt.hairsalon.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Appointment {
    private final UUID appointmentId;
    private final Designer designer;
    private final Customer customer;
    private final Menu menu;
    private AppointmentStatus status;
    private LocalDateTime appointedAt;

    public Appointment(UUID appointmentId, Designer designer, Customer customer, Menu menu, AppointmentStatus status, LocalDateTime appointedAt) {
        this.appointmentId = appointmentId;
        this.designer = designer;
        this.customer = customer;
        this.menu = menu;
        this.status = status;
        this.appointedAt = appointedAt.withNano(0);
    }

    public void updateAppointedAt(LocalDateTime appointedAt) {
        this.appointedAt = appointedAt;
    }

    public void updateStatus(AppointmentStatus appointmentStatus) {
        this.status = appointmentStatus;
    }
}
