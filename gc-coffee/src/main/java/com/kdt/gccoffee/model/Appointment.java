package com.kdt.gccoffee.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Appointment {
    private final UUID appointmentId;
    private final UUID menuId;
    private final UUID customerId;
    private final UUID designerId;
    private LocalDateTime appointedAt;

    public Appointment(UUID appointmentId, UUID menuId, UUID customerId, UUID designerId,
                       LocalDateTime appointedAt) {
        this.appointmentId = appointmentId;
        this.menuId = menuId;
        this.customerId = customerId;
        this.designerId = designerId;
        this.appointedAt = appointedAt;
    }
}
