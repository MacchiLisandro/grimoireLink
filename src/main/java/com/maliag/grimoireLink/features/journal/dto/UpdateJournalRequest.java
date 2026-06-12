package com.maliag.grimoireLink.features.journal.dto;

import com.maliag.grimoireLink.features.journal.JournalEntryType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateJournalRequest {

    private JournalEntryType journalEntryType;

    @Size(max = 65535, message = "La descripción no puede tener más de 65535 caracteres")
    private String description;
}
