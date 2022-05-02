package com.kdt.hairsalon.service.appointment;

import com.kdt.hairsalon.model.Appointment;
import com.kdt.hairsalon.repository.appointment.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<AppointmentDto> findAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(AppointmentDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDto findByAppointmentId(UUID appointmentId) {
        return AppointmentDto.of(
                appointmentRepository.findByAppointmentId(appointmentId).orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."))
        );
    }

    @Override
    public AppointmentDto findByCustomerId(UUID customerId) {
        return AppointmentDto.of(
                appointmentRepository.findByCustomerId(customerId).orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."))
        );
    }

    @Override
    public List<AppointmentDto> findByDesignerId(UUID designerId) {
        return appointmentRepository.findByDesignerId(designerId)
                .stream()
                .map(AppointmentDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByAppointmentId(UUID appointmentId) {
        appointmentRepository.deleteByAppointmentId(appointmentId);
    }

    @Override
    public AppointmentDto updatedByAppointmentId(UUID appointmentId, UUID menuId, LocalDateTime appointedAt) {
        Appointment appointment = new Appointment(appointmentId, menuId, )
        appointmentRepository.updateByAppointmentId()
    }
}
