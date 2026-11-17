package com.maliag.grimoireLink.features.journal.mapper;

import com.maliag.grimoireLink.features.campaign.mapper.CampaignMapper;
import com.maliag.grimoireLink.features.journal.model.JournalEntity;
import com.maliag.grimoireLink.features.journal.dto.JournalRequest;
import com.maliag.grimoireLink.features.journal.dto.JournalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CampaignMapper.class})
public interface JournalMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "date", ignore = true)
    JournalEntity toEntity(JournalRequest request);

    JournalResponse toResponse(JournalEntity entity);
}
