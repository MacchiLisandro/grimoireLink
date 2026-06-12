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

    @NotNull(message = "Se debe incluir el tipo de entrada de diario")
    private JournalEntryType journalEntryType;

    @NotNull(message = "La entrada de diario no puede estar vacía")
    @Size(max = 65535, message = "La descripción no puede tener más de 65535 caracteres")
    private String description;

    @NotNull(message = "La entrada de diario debe estar asociada a una campaña")
    private UUID campaignId;
}
