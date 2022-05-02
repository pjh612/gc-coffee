package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.service.menu.MenuDto;
import com.kdt.hairsalon.service.menu.MenuService;

import java.util.List;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1")
@RequiredArgsConstructor
public class MenuRestController {

    private final MenuService menuService;

    @GetMapping("/menus")
    public ResponseEntity<List<MenuDto>> getAll() {
        return ResponseEntity.ok(menuService.findAll());
    }

    @PostMapping("/menus")
    public ResponseEntity<MenuDto> create(@RequestBody CreateMenuRequest request) {
        MenuDto insertedMenu = menuService.insert(request.getName(), request.getPrice());

        return ResponseEntity.ok(insertedMenu);
    }

    @PostMapping("/menus/{id}")
    public ResponseEntity<MenuDto> getById(@PathVariable("id")UUID id) {
        MenuDto menu = menuService.findById(id);

        return ResponseEntity.ok(menu);
    }
}
