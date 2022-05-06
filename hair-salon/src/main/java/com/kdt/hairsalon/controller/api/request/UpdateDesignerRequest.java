package com.kdt.hairsalon.controller.api.request;

import com.kdt.hairsalon.model.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UpdateDesignerRequest {
    private UUID id;
    @NotBlank
    private String name;
    private Position position;
    private String specialty;
}
