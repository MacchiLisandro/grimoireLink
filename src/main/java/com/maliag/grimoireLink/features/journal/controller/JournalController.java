package com.maliag.grimoireLink.features.journal.controller;

import com.maliag.grimoireLink.features.journal.enums.JournalEntryType;
import com.maliag.grimoireLink.features.journal.service.JournalService;
import com.maliag.grimoireLink.features.journal.dto.JournalRequest;
import com.maliag.grimoireLink.features.journal.dto.JournalResponse;
import com.maliag.grimoireLink.features.journal.dto.UpdateJournalRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/journals")
public class JournalController {

    private final JournalService service;

    @GetMapping("/{publicId}")
    @ResponseStatus(HttpStatus.OK)
    public JournalResponse getByPublicId(@PathVariable UUID publicId){
        return service.getByPublicId(publicId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<JournalResponse> filterJournals(@RequestParam UUID campaignPublicId,
                                                @RequestParam(required = false) JournalEntryType journalEntryType,
                                                @RequestParam(required = false) LocalDateTime date){
        return service.filterJournals(campaignPublicId, journalEntryType, date);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JournalResponse postJournal(@Valid @RequestBody JournalRequest request){
        return service.createJournal(request);
    }

    @PatchMapping("/{publicId}")
    @ResponseStatus(HttpStatus.OK)
    public JournalResponse updateJournal(@PathVariable UUID publicId,
            @Valid @RequestBody UpdateJournalRequest request){
        return service.updateJournal(publicId, request);
    }

    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJournal(@PathVariable UUID publicId){
        service.deleteJournal(publicId);
    }
}
