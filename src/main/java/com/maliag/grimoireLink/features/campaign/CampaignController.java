package com.maliag.grimoireLink.features.campaign;

import com.maliag.grimoireLink.features.campaign.dto.CampaignRequest;
import com.maliag.grimoireLink.features.campaign.dto.CampaignResponse;
import com.maliag.grimoireLink.features.campaign.dto.UpdateCampaignRequest;
import com.maliag.grimoireLink.features.usersXCampaign.dto.CampaignMemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/campaigns")
public class CampaignController {

    private final CampaignService service;

    @GetMapping("/{publicId}")
    @ResponseStatus(HttpStatus.OK)
    public CampaignResponse getByPublicId(@PathVariable UUID publicId){
        return service.getByPublicId(publicId);
    }

    ///cambiar por jwt
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CampaignResponse> getCampaignsByUserPublicId(@RequestParam UUID userPublicId){
        return service.getAllCampaignsByUserId(userPublicId);
    }

    @GetMapping("/{publicId}/members")
    @ResponseStatus(HttpStatus.OK)
    public List<CampaignMemberResponse> getAllCampaignMembers(@PathVariable UUID publicId){
        return service.getAllCampaignMembers(publicId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CampaignResponse postCampaign(@Valid @RequestBody CampaignRequest request){
        return service.createCampaign(request);
    }

    @PatchMapping("/{publicId}")
    @ResponseStatus(HttpStatus.OK)
    public CampaignResponse updateCampaign(@PathVariable UUID publicId,
                                           @Valid @RequestBody UpdateCampaignRequest request){
        return service.updateCampaign(publicId, request);
    }

    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCampaign(@PathVariable UUID publicId){
        service.deleteCampaign(publicId);
    }
}
