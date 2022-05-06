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
    @Email(message = "잘못된 이메일 형식입니다.")
    private String email;
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Past(message = "올바르지 않은 생년월일 입니다. ex) 1996-06-12")
    private LocalDate birth;
}
