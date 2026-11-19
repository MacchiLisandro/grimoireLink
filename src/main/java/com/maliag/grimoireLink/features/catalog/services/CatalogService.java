package com.maliag.grimoireLink.features.catalog.services;

import com.maliag.grimoireLink.features.catalog.dtos.BackgroundCatalogResponse;
import com.maliag.grimoireLink.features.catalog.dtos.CatalogItemResponse;

import java.util.List;

public interface CatalogService {
    List<CatalogItemResponse> getRaces();
    List<CatalogItemResponse> getClasses();
    List<BackgroundCatalogResponse> getBackgrounds();
}
