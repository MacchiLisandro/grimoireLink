package com.maliag.grimoireLink.features.journal;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class JournalSpecifications {

    public static Specification<JournalEntity> byCampaignPublicId(UUID campaignPublicId){
        return (root, query, cb) ->
                cb.equal(root.get("campaign").get("publicId"), campaignPublicId);
    }

    public static Specification<JournalEntity> byJournalEntryType(JournalEntryType journalEntryType){
        return (root, query, cb) ->
                journalEntryType == null ? cb.conjunction() :
                cb.equal(root.get("journalEntryType"), journalEntryType);
    }

    public static Specification<JournalEntity> byDate(LocalDateTime date){
        return (root, query, cb) ->
                date == null ? cb.conjunction() :
                cb.equal(root.get("date"), date);
    }
}
