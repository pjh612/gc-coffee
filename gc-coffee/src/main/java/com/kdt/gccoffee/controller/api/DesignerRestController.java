package com.kdt.gccoffee.controller.api;

import com.kdt.gccoffee.repository.designer.DesignerRepository;
import com.kdt.gccoffee.service.designer.DesignerDto;

import com.kdt.gccoffee.service.designer.DesignerService;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DesignerRestController {

    private final DesignerService designerService;

    @PostMapping("/designers")
    public ResponseEntity<DesignerDto> create(CreateDesignerRequest request) {
        return ResponseEntity.ok(designerService.create(request.getName(), request.getPosition()));
    }

    @GetMapping("/designers/{id}")
    public ResponseEntity<DesignerDto> getById(@PathVariable("id")UUID id){
        return ResponseEntity.ok(designerService.findById(id));
    }

    @GetMapping("/designers")
    public ResponseEntity<List<DesignerDto>> getAll(){
        return ResponseEntity.ok(designerService.findAll());
    }

    @DeleteMapping("/designers/{id}")
    public ResponseEntity<UUID> deleteById(@PathVariable("id") UUID id) {
        designerService.deleteById(id);

        return ResponseEntity.ok(id);
    }

    @PatchMapping("/designers/{id}")
    public ResponseEntity<UUID> deleteById(UpdateDesignerDto request) {
        return ResponseEntity.ok(designerService.update(request.getId(), request.getName(), request.getPosition()));
    }
}
