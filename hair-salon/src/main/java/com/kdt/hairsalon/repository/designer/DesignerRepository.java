package com.kdt.hairsalon.repository.designer;

import com.kdt.hairsalon.model.Designer;
import com.kdt.hairsalon.model.Position;

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
