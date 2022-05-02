package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.model.Position;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateDesignerDto {
    private final UUID id;
    private final String name;
    private final Position position;
}
