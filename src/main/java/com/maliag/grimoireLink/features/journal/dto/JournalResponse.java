package com.maliag.grimoireLink.features.journal.dto;

import com.maliag.grimoireLink.features.campaign.dto.CampaignResponse;
import com.maliag.grimoireLink.features.journal.enums.JournalEntryType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class JournalResponse {

    private UUID publicId;
    private JournalEntryType journalEntryType;
    private LocalDateTime date;
    private String description;
    private CampaignResponse campaign;
}
