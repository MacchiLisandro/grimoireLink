package com.maliag.grimoireLink.features.journal.model;

import com.maliag.grimoireLink.features.campaign.model.CampaignEntity;
import com.maliag.grimoireLink.features.journal.enums.JournalEntryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "journals")
public class JournalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(name = "journal_entry_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private JournalEntryType journalEntryType;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private CampaignEntity campaign;

    @PrePersist
    void onCreate(){
        if(publicId==null){
            this.publicId = UUID.randomUUID();
        }
        if(date==null){
            this.date = LocalDateTime.now();
        }
    }
}
