package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.service.designer.DesignerDto;
import com.kdt.hairsalon.service.designer.DesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DesignerRestController {

    private final DesignerService designerService;

    @PostMapping("/designers")
    public ResponseEntity<DesignerDto> create(@RequestBody CreateDesignerRequest request) {
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

    @PatchMapping("/designers")
    public ResponseEntity<UUID> updateById(@RequestBody UpdateDesignerRequest request) {
        return ResponseEntity.ok(designerService.update(request.getId(), request.getName(), request.getPosition()));
    }
}
