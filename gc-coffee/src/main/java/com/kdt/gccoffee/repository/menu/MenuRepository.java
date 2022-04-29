package com.kdt.gccoffee.repository.menu;

import com.kdt.gccoffee.model.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {
    Menu insert(Menu customer);

    Optional<Menu> findById(UUID id);

    List<Menu> findAll();
}
