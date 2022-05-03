package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.model.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UpdateDesignerDto {
    private final UUID id;
    private final String name;
    private final Position position;
}
