package com.kdt.gccoffee.controller.api;

import com.kdt.gccoffee.service.appointment.AppointmentDto;
import com.kdt.gccoffee.service.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentRestController {

    private final AppointmentService appointmentService;

    @PostMapping("/")
    public ResponseEntity<AppointmentDto> makeAppointment(@RequestBody MakeAppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.make(request.getMenuId(), request.getCustomerId(), request.getDesignerId(), request.getAppointedAt()));
    }

    @GetMapping("/{designerId}")
    public ResponseEntity<AppointmentDto> getByDesignerId(@PathVariable("designerId") UUID id) {
        return ResponseEntity.ok(appointmentService.findByDesignerId(id));
    }

    @PatchMapping
    public ResponseEntity<AppointmentDto> updateByAppointmentId(@RequestBody UpdateAppointmentRequest request) {
        return ResponseEntity.ok(
                appointmentService.updatedByAppointmentId(request.getAppointmentId(), request.getMenuId(), request.getAppointedAt())
        );
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity deleteByAppointmentId(@PathVariable("appointmentId") UUID id) {
        appointmentService.deleteByAppointmentId(id);

        return ResponseEntity.ok().build();
    }
}
