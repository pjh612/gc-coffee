package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.controller.api.request.CreateMenuRequest;
import com.kdt.hairsalon.service.menu.MenuDto;
import com.kdt.hairsalon.service.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MenuRestController {

    private final MenuService menuService;

    @GetMapping("/menus")
    public ResponseEntity<List<MenuDto>> getAll() {
        return ResponseEntity.ok(menuService.findAll());
    }

    @PostMapping("/menus")
    public ResponseEntity<MenuDto> create(@RequestBody @Valid CreateMenuRequest request) {
        MenuDto insertedMenu = menuService.insert(request.getName(), request.getPrice());

        return ResponseEntity.ok(insertedMenu);
    }

    @DeleteMapping("/menus/{id}")
    public ResponseEntity<UUID> deleteById(@PathVariable("id") UUID id) {
        menuService.deleteById(id);

        return ResponseEntity.ok(id);
    }

    @GetMapping("/menus/{id}")
    public ResponseEntity<MenuDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(menuService.findById(id));
    }
}
