package com.maliag.grimoireLink.features.journal;

import com.maliag.grimoireLink.features.journal.dto.JournalRequest;
import com.maliag.grimoireLink.features.journal.dto.JournalResponse;
import com.maliag.grimoireLink.features.journal.dto.UpdateJournalRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface JournalService {
    JournalResponse getByPublicId(UUID publicId);
    List<JournalResponse> getJournalsByCampaign(UUID campaignPublicId);
    List<JournalResponse> filterJournals(UUID campaignPublicId,
                                         JournalEntryType journalEntryType,
                                         LocalDateTime date);
    JournalResponse createJournal(JournalRequest request);
    JournalResponse updateJournal(UUID publicId, UpdateJournalRequest request);
    void deleteJournal(UUID publicId);
}
