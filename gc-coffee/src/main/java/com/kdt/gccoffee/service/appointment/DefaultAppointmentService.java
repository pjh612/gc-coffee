package com.kdt.gccoffee.service.appointment;

import com.kdt.gccoffee.model.Appointment;
import com.kdt.gccoffee.repository.appointment.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAppointmentService implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public AppointmentDto make(UUID menuId, UUID customerId, UUID designerId, LocalDateTime appointedAt) {
        Appointment appointment = new Appointment(UUID.randomUUID(), menuId, customerId, designerId, appointedAt);

        appointmentRepository.insert(appointment);

        return AppointmentDto.of(appointment);
    }

    @Override
    public AppointmentDto findByAppointmentId(UUID appointmentId) {
        return AppointmentDto.of(
                appointmentRepository.findByAppointmentId(appointmentId).orElseThrow(()-> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."))
        );
    }

    @Override
    public AppointmentDto findByCustomerId(UUID customerId) {
        return AppointmentDto.of(
                appointmentRepository.findByCustomerId(customerId).orElseThrow(()-> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."))
        );
    }

    @Override
    public AppointmentDto findByDesignerId(UUID designerId) {
        return AppointmentDto.of(
                appointmentRepository.findByDesignerId(designerId).orElseThrow(()-> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."))
        );
    }

    @Override
    public void deleteByAppointmentId(UUID appointmentId) {
        appointmentRepository.deleteByAppointmentId(appointmentId);
    }

    @Override
    public AppointmentDto updatedByAppointmentId(UUID appointmentId, UUID menuId, LocalDateTime appointedAt) {
        return null;
    }
}
