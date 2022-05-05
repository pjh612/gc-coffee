package com.kdt.hairsalon.controller.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@RequiredArgsConstructor
@Getter
public class CreateMenuRequest {
    @NotBlank
    private String name;

    @PositiveOrZero
    private int price;
}
