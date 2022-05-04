package com.kdt.hairsalon.service.menu;

import com.kdt.hairsalon.model.Menu;
import com.kdt.hairsalon.repository.menu.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultMenuService implements MenuService {

    private final MenuRepository menuRepository;


    @Transactional
    @Override
    public MenuDto insert(String name, int price) {
        Menu menu = menuRepository.insert(new Menu(UUID.randomUUID(), name, price, LocalDateTime.now(), LocalDateTime.now()));

        return MenuDto.of(menu);
    }
    @Transactional(readOnly = true)
    @Override
    public MenuDto findById(UUID id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID로 메뉴 정보를 찾을 수 없습니다."));

        return MenuDto.of(menu);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        menuRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MenuDto> findAll() {
        return menuRepository.findAll()
                .stream()
                .map(MenuDto::of)
                .collect(Collectors.toList());
    }
}
