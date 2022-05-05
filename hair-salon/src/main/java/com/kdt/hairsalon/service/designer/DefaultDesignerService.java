package com.kdt.hairsalon.service.designer;

import com.kdt.hairsalon.model.Designer;

import com.kdt.hairsalon.model.Position;
import com.kdt.hairsalon.repository.designer.DesignerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class DefaultDesignerService implements DesignerService {

    private final DesignerRepository designerRepository;

    @Override
    @Transactional
    public DesignerDto create(String name, Position position) {
        Designer designer = new Designer(UUID.randomUUID(), name, position, LocalDateTime.now());

        return DesignerDto.of(designerRepository.insert(designer));
    }

    @Override
    @Transactional(readOnly = true)
    public DesignerDto findById(UUID id) {
        return DesignerDto.of(
                designerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("디자이너 정보를 찾을 수 없습니다."))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesignerDto> findAll() {
        return designerRepository.findAll()
                .stream()
                .map(DesignerDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        designerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UUID update(UUID id, String name, Position position) {
        return designerRepository.update(id, name, position);
    }
}
