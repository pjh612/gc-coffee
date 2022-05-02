package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.model.Gender;

import java.time.LocalDate;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerJoinRequest {
    private final String name;
    private final String email;
    private final Gender gender;
    private final LocalDate birth;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


}
