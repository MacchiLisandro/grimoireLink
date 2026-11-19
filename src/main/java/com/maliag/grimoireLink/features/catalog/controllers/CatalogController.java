package com.maliag.grimoireLink.features.catalog.controllers;

import com.maliag.grimoireLink.features.catalog.dtos.BackgroundCatalogResponse;
import com.maliag.grimoireLink.features.catalog.dtos.CatalogItemResponse;
import com.maliag.grimoireLink.features.catalog.services.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;

    @GetMapping("/races")
    public ResponseEntity<List<CatalogItemResponse>> getRaces() {
        return ResponseEntity.ok().body(catalogService.getRaces());
    }

    @GetMapping("/classes")
    public ResponseEntity<List<CatalogItemResponse>> getClasses() {
        return ResponseEntity.ok().body(catalogService.getClasses());
    }

    @GetMapping("/backgrounds")
    public ResponseEntity<List<BackgroundCatalogResponse>> getBackgrounds() {
        return ResponseEntity.ok().body(catalogService.getBackgrounds());
    }
}
