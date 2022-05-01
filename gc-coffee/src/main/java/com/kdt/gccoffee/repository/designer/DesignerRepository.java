package com.kdt.gccoffee.repository.designer;

import com.kdt.gccoffee.model.Designer;

import com.kdt.gccoffee.model.Position;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DesignerRepository {
    Designer insert(Designer designer);
    Optional<Designer> findById(UUID id);

    List<Designer> findAll();
    void deleteById(UUID id);
    UUID update(UUID id, String name, Position position);
}
