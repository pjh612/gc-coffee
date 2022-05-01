package com.kdt.gccoffee.service.designer;

import com.kdt.gccoffee.model.Designer;

import com.kdt.gccoffee.model.Position;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DesignerService {
    DesignerDto create(String name, Position position);
    DesignerDto findById(UUID id);

    List<DesignerDto> findAll();
    void deleteById(UUID id);
    UUID update(UUID id, String name, Position position);
}
