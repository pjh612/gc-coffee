package com.kdt.gccoffee.controller.api;

import com.kdt.gccoffee.model.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class MakeAppointmentRequest {
    private final UUID customerId;
    private final UUID menuId;
    private final UUID designerId;
    private final LocalDateTime appointedAt;
}
