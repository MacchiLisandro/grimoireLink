package com.maliag.grimoireLink.features.journal.enums;

public enum JournalEntryType {
    // Combate
    ENCOUNTER,
    BOSS_FIGHT,

    // Narrativo / Historia
    NARRATIVE,
    STORY_MILESTONE,
    REVELATION,

    // Interacción
    NPC_INTERACTION,
    PLAYER_MOMENT,

    // Exploración
    LOCATION_DISCOVERY,
    TRAVEL,

    // Recursos / Progresión
    LOOT,
    LEVEL_UP,

    // General
    SESSION_START,
    SESSION_END,
    DM_NOTE
}
