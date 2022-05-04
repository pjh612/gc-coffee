package com.kdt.hairsalon.controller.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kdt.hairsalon.model.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CustomerJoinRequest {
    private String name;
    private String email;
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate birth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
