package com.maliag.grimoireLink.features.journal;

import com.maliag.grimoireLink.features.journal.dto.JournalRequest;
import com.maliag.grimoireLink.features.journal.dto.JournalResponse;
import com.maliag.grimoireLink.features.journal.dto.UpdateJournalRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.maliag.grimoireLink.features.journal.JournalSpecifications.*;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService{

    private final JournalRepository repository;
    private final JournalMapper mapper;

    @Transactional(readOnly = true)
    public JournalEntity findByPublicId(UUID publicID){
        return repository.findByPublicId(publicID)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public JournalResponse getByPublicId(UUID publicId) {
        return mapper.toResponse(findByPublicId(publicId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalResponse> filterJournals(UUID campaignPublicId,
                                                JournalEntryType journalEntryType,
                                                LocalDateTime date) {

        Specification<JournalEntity> spec = Specification
                .where(byCampaignPublicId(campaignPublicId))
                .and(byJournalEntryType(journalEntryType))
                .and(byDate(date));

        return repository.findAll(spec).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public JournalResponse createJournal(JournalRequest request) {
        JournalEntity journal = mapper.toEntity(request);
        return mapper.toResponse(repository.save(journal));
    }

    @Override
    @Transactional
    public JournalResponse updateJournal(UUID publicId, UpdateJournalRequest request) {
        JournalEntity journal = findByPublicId(publicId);
        if(request.getJournalEntryType()!=null){
            journal.setJournalEntryType(request.getJournalEntryType());
        }
        if(request.getDescription()!=null){
            journal.setDescription(request.getDescription());
        }
        return mapper.toResponse(repository.save(journal));
    }

    @Override
    @Transactional
    public void deleteJournal(UUID publicId) {
        JournalEntity journal = findByPublicId(publicId);
        repository.delete(journal);
    }
}
