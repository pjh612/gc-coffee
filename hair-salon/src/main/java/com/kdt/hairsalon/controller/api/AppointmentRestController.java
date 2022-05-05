package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.controller.api.request.MakeAppointmentRequest;
import com.kdt.hairsalon.controller.api.request.UpdateAppointmentRequest;
import com.kdt.hairsalon.service.appointment.AppointmentDto;
import com.kdt.hairsalon.service.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentRestController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> makeAppointment(@RequestBody @Valid MakeAppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.make(request.getMenuId(), request.getCustomerId(), request.getDesignerId(), request.getAppointedAt()));
    }

    @GetMapping("/designer/{designerId}")
    public ResponseEntity<List<AppointmentDto>> getByDesignerId(@PathVariable("designerId") UUID id) {
        return ResponseEntity.ok(appointmentService.findByDesignerId(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<AppointmentDto> getByCustomerId(@PathVariable("customerId") UUID id) {
        return ResponseEntity.ok(appointmentService.findByCustomerId(id));
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDto> getByAppointmentId(@PathVariable("appointmentId") UUID id) {
        return ResponseEntity.ok(appointmentService.findByAppointmentId(id));
    }

    @PatchMapping
    public ResponseEntity<AppointmentDto> updateByAppointmentId(@RequestBody @Valid UpdateAppointmentRequest request) {
        return ResponseEntity.ok(
                appointmentService.updateByAppointmentId(request.appointmentId(), request.status(), request.appointedAt())
        );
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity deleteByAppointmentId(@PathVariable("appointmentId") UUID id) {
        appointmentService.deleteByAppointmentId(id);

        return ResponseEntity.ok().build();
    }


}
