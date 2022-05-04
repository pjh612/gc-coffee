package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.model.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UpdateDesignerRequest {
    private  UUID id;
    private  String name;
    private  Position position;
}
