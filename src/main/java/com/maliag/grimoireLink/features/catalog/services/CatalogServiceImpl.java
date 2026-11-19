package com.maliag.grimoireLink.features.catalog.services;

import com.maliag.grimoireLink.features.background.model.BackgroundEntity;
import com.maliag.grimoireLink.features.background.repository.BackgroundRepository;
import com.maliag.grimoireLink.features.catalog.dtos.BackgroundCatalogResponse;
import com.maliag.grimoireLink.features.catalog.dtos.CatalogItemResponse;
import com.maliag.grimoireLink.features.dndapi.DnDApiService;
import com.maliag.grimoireLink.features.dndapi.dto.DndReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private final DnDApiService dndApiService;
    private final BackgroundRepository backgroundRepository;

    @Override
    public List<CatalogItemResponse> getRaces() {
        return dndApiService.getRaces().stream()
                .map(this::toItemResponse)
                .toList();
    }

    @Override
    public List<CatalogItemResponse> getClasses() {
        return dndApiService.getClasses().stream()
                .map(this::toItemResponse)
                .toList();
    }

    @Override
    public List<BackgroundCatalogResponse> getBackgrounds() {
        return backgroundRepository.findAll().stream()
                .sorted(Comparator.comparing(BackgroundEntity::getName))
                .map(this::toBackgroundResponse)
                .toList();
    }

    private CatalogItemResponse toItemResponse(DndReference reference) {
        return new CatalogItemResponse(reference.getIndex(), reference.getName());
    }

    private BackgroundCatalogResponse toBackgroundResponse(BackgroundEntity background) {
        return new BackgroundCatalogResponse(
                background.getBackgroundIndex(),
                background.getName(),
                background.getDescription());
    }
}
