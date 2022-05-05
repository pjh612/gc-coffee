package com.kdt.hairsalon.controller.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class MakeAppointmentRequest {
    @NotBlank
    private UUID customerId;
    @NotBlank
    private UUID menuId;
    @NotBlank
    private UUID designerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    @Future(message = "잘못된 예약 시간입니다.")
    private LocalDateTime appointedAt;
}
