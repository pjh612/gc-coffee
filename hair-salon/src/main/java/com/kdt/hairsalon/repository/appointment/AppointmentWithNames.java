package com.kdt.hairsalon.repository.appointment;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AppointmentWithNames {
    private UUID appointmentId;
    private UUID designerId;
    private UUID customerId;
    private UUID menuId;
    private String designerName;
    private String menuName;
    private String customerName;
    private LocalDateTime appointedAt;

    public AppointmentWithNames(UUID appointmentId, UUID designerId, UUID customerId, UUID menuId, String designerName, String menuName, String customerName, LocalDateTime appointedAt) {
        this.appointmentId = appointmentId;
        this.designerId = designerId;
        this.customerId = customerId;
        this.menuId = menuId;
        this.designerName = designerName;
        this.menuName = menuName;
        this.customerName = customerName;
        this.appointedAt = appointedAt;
    }
}
