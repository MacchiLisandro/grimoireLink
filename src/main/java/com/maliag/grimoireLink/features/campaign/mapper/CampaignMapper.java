package com.maliag.grimoireLink.features.campaign.mapper;

import com.maliag.grimoireLink.features.campaign.model.CampaignEntity;
import com.maliag.grimoireLink.features.campaign.dto.CampaignRequest;
import com.maliag.grimoireLink.features.campaign.dto.CampaignResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CampaignMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "inviteCode", ignore = true)
    @Mapping(target = "status", ignore = true)
    CampaignEntity toEntity(CampaignRequest request);

    CampaignResponse toResponse(CampaignEntity entity);
}
