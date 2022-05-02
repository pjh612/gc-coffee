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

@RequiredArgsConstructor
@Repository
public class DefaultDesignerService implements DesignerService {

    private final DesignerRepository designerRepository;

    @Override
    public DesignerDto create(String name, Position position) {
        Designer designer = new Designer(UUID.randomUUID(), name, position, LocalDateTime.now());

        return DesignerDto.of(designerRepository.insert(designer));
    }

    @Override
    public DesignerDto findById(UUID id) {
        return DesignerDto.of(
                designerRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("디자이너 정보를 찾을 수 없습니다."))
        );
    }

    @Override
    public List<DesignerDto> findAll() {
        return designerRepository.findAll()
                .stream()
                .map(DesignerDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        designerRepository.deleteById(id);
    }

    @Override
    public UUID update(UUID id, String name, Position position) {
        return designerRepository.update(id, name, position);
    }
}
