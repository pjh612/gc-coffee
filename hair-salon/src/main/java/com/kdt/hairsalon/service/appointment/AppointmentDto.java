package com.kdt.hairsalon.service.appointment;

import com.kdt.hairsalon.model.Appointment;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AppointmentDto {
    private final UUID appointmentId;
    private final DesignerDto designerDto;
    private final CustomerDto customerDto;
    private final MenuDto menuDto;
    private final LocalDateTime appointedAt;

    public static AppointmentDto of(Appointment appointment) {
        return new AppointmentDto(appointment.getAppointmentId(),
                DesignerDto.of(appointment.getDesigner()),
                CustomerDto.of(appointment.getCustomer()),
                MenuDto.of(appointment.getMenu()),
                appointment.getAppointedAt());
    }
}