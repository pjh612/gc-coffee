package com.kdt.hairsalon.controller.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateMenuRequest {
    private String name;

    private int price;
}
