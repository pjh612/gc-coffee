package com.kdt.hairsalon.service.menu;

import java.util.List;
import java.util.UUID;

public interface MenuService {

    MenuDto insert(String name, int price);

    MenuDto findById(UUID id);

    List<MenuDto> findAll();
}
