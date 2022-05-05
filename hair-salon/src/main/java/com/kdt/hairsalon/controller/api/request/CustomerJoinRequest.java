package com.kdt.hairsalon.controller.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kdt.hairsalon.model.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class CustomerJoinRequest {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    @Past
    @NotBlank
    private LocalDate birth;
}
