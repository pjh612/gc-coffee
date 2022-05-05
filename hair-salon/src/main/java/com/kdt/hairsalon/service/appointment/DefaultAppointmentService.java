package com.kdt.hairsalon.service.appointment;

import com.kdt.hairsalon.model.*;
import com.kdt.hairsalon.repository.appointment.AppointmentRepository;
import com.kdt.hairsalon.repository.customer.CustomerRepository;
import com.kdt.hairsalon.repository.designer.DesignerRepository;
import com.kdt.hairsalon.repository.menu.MenuRepository;
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
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;
    private final DesignerRepository designerRepository;

    @Override
    @Transactional
    public AppointmentDto make(UUID menuId, UUID customerId, UUID designerId, LocalDateTime appointedAt) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Designer designer = designerRepository.findById(designerId).orElseThrow(() -> new IllegalArgumentException("디자이너 정보를 찾을 수 없습니다."));
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("시술메뉴 정보를 찾을 수 없습니다."));
        Appointment appointment = new Appointment(UUID.randomUUID(), designer, customer, menu, AppointmentStatus.APPOINTED, appointedAt);

        appointmentRepository.insert(appointment);

        return AppointmentDto.of(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> findAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(AppointmentDto::of)
                .collect(Collectors.toList());
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
    @Transactional(readOnly = true)
    public List<AppointmentDto> findByDesignerId(UUID designerId) {
        return appointmentRepository.findByDesignerId(designerId)
                .stream()
                .map(AppointmentDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> findByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status)
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
    public AppointmentDto updateByAppointmentId(UUID appointmentId, AppointmentStatus status, LocalDateTime appointedAt) {
        Appointment foundAppointment = appointmentRepository.findByAppointmentId(appointmentId).orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));

        foundAppointment.updateAppointedAt(appointedAt);
        foundAppointment.updateStatus(status);
        return AppointmentDto.of(appointmentRepository.updateByAppointmentId(foundAppointment));
    }
}
