package com.kdt.hairsalon.service.designer;

import com.kdt.hairsalon.model.Position;

import java.util.List;
import java.util.UUID;

public interface DesignerService {
    DesignerDto create(String name, Position position);

    DesignerDto findById(UUID id);

    List<DesignerDto> findAll();

    void deleteById(UUID id);

    UUID update(UUID id, String name, Position position);
}
