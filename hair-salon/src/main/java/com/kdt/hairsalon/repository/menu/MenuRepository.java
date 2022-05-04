package com.kdt.hairsalon.repository.menu;

import com.kdt.hairsalon.model.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {
    Menu insert(Menu customer);

    Optional<Menu> findById(UUID id);

    void deleteById(UUID id);

    List<Menu> findAll();
}
