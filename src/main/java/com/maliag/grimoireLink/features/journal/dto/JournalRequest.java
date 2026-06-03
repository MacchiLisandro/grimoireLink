package com.maliag.grimoireLink.features.journal.dto;

import com.maliag.grimoireLink.features.journal.JournalEntryType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class JournalRequest {

    @NotNull
    private JournalEntryType journalEntryType;

    @NotNull
    @Size(max = 65535)
    private String description;

    @NotNull
    private UUID campaignId;
}
