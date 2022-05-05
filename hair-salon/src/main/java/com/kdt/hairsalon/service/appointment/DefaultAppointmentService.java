package com.kdt.hairsalon.service.appointment;

import com.kdt.hairsalon.model.Appointment;
import com.kdt.hairsalon.repository.appointment.AppointmentRepository;
import com.kdt.hairsalon.repository.appointment.AppointmentWithNames;
import com.kdt.hairsalon.repository.appointment.AppointmentWithNamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultAppointmentService implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentWithNamesRepository appointmentWithNamesRepository;

    @Override
    @Transactional
    public AppointmentDto make(UUID menuId, UUID customerId, UUID designerId, LocalDateTime appointedAt) {
        com.kdt.hairsalon.model.Appointment appointment = new Appointment(UUID.randomUUID(), menuId, customerId, designerId, appointedAt);

        appointmentRepository.insert(appointment);

        return AppointmentDto.of(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentWithNames> findAll() {
        return appointmentWithNamesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentDto findByAppointmentId(UUID appointmentId) {
        return AppointmentDto.of(
                appointmentRepository.findByAppointmentId(appointmentId).orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."))
        );
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public void deleteByAppointmentId(UUID appointmentId) {
        appointmentRepository.deleteByAppointmentId(appointmentId);
    }

    @Override
    @Transactional
    public AppointmentDto updatedByAppointmentId(UUID appointmentId, LocalDateTime appointedAt) {
        Appointment foundAppointment = appointmentRepository.findByAppointmentId(appointmentId).orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));

        foundAppointment.updateAppointedAt(appointedAt);
        return AppointmentDto.of(appointmentRepository.updateByAppointmentId(foundAppointment));
    }
}
