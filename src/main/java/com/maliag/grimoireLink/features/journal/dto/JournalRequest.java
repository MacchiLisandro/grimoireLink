package com.maliag.grimoireLink.features.journal.dto;

import com.maliag.grimoireLink.features.journal.JournalEntryType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
