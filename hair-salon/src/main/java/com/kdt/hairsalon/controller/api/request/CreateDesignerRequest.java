package com.kdt.hairsalon.controller.api.request;

import com.kdt.hairsalon.model.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateDesignerRequest {
    private String name;
    private Position position;

}
