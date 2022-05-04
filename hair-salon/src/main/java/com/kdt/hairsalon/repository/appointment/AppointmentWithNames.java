package com.kdt.hairsalon.repository.appointment;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AppointmentWithNames {
    private UUID id;
    private String designerName;
    private String menuName;
    private String customerName;
    private LocalDateTime appointedAt;

    public AppointmentWithNames(UUID id, String designerName, String menuName, String customerName, LocalDateTime appointedAt) {
        this.id = id;
        this.designerName = designerName;
        this.menuName = menuName;
        this.customerName = customerName;
        this.appointedAt = appointedAt;
    }
}
