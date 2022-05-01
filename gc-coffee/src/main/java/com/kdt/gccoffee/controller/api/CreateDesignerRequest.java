package com.kdt.gccoffee.controller.api;

import com.kdt.gccoffee.model.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateDesignerRequest {
    private final String name;
    private final Position position;
}
