package com.kdt.gccoffee.service.menu;

import com.kdt.gccoffee.model.Menu;
import com.kdt.gccoffee.repository.menu.MenuRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultMenuService implements MenuService {

    private final MenuRepository menuRepository;


    @Override
    public MenuDto insert(String name, int price) {
        Menu menu = menuRepository.insert(new Menu(UUID.randomUUID(), name, price, LocalDateTime.now()));

        return MenuDto.of(menu);
    }

    @Override
    public MenuDto findById(UUID id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID로 메뉴 정보를 찾을 수 없습니다."));

        return MenuDto.of(menu);
    }

    @Override
    public List<MenuDto> findAll() {
        return menuRepository.findAll()
                .stream()
                .map(MenuDto::of)
                .collect(Collectors.toList());
    }
}
