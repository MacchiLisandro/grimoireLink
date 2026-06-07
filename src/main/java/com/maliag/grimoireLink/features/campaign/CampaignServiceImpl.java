package com.maliag.grimoireLink.features.campaign;

import com.maliag.grimoireLink.features.campaign.dto.CampaignRequest;
import com.maliag.grimoireLink.features.campaign.dto.CampaignResponse;
import com.maliag.grimoireLink.features.campaign.dto.UpdateCampaignRequest;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignMapper;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignRepository;
import com.maliag.grimoireLink.features.usersXCampaign.dto.CampaignMemberResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CampaignServiceImpl implements CampaignService{

    private CampaignRepository repository;
    private CampaignMapper mapper;
    private UsersXCampaignRepository uxcRepository;
    private UsersXCampaignMapper uxcMapper;

    @Transactional(readOnly = true)
    public CampaignEntity findByPublicId(UUID publicId){
        return repository.findByPublicId(publicId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public CampaignResponse getByPublicId(UUID publicId){
        return mapper.toResponse(findByPublicId(publicId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResponse> getAllCampaignsByUserId(UUID userId){
        return uxcRepository.findCampaignsByUserPublicId(userId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignMemberResponse> getAllCampaignMembers(UUID publicId) {
        CampaignEntity campaign = findByPublicId(publicId);
        return campaign.getUsers().stream()
                .map(uxcMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CampaignResponse createCampaign(CampaignRequest request){
        CampaignEntity campaign = mapper.toEntity(request);
        String inviteCode;
        do{
            inviteCode = generateInviteCode();
        }while (repository.existsByInviteCode(inviteCode));
        campaign.setInviteCode(inviteCode);
        return mapper.toResponse(repository.save(campaign));
    }

    private String generateInviteCode(){
        return String.format("%06d",
                ThreadLocalRandom.current().nextInt(0, 1000000));
    }

    @Override
    @Transactional
    public CampaignResponse updateCampaign(UUID publicId, UpdateCampaignRequest request){
        CampaignEntity campaign = findByPublicId(publicId);
        if(request.getName()!=null){
            campaign.setName(request.getName());
        }
        if(request.getDescription()!=null){
            campaign.setDescription(request.getDescription());
        }
        if(request.getStatus()!=null){
            campaign.setStatus(request.getStatus());
        }
        return mapper.toResponse(repository.save(campaign));
    }

    @Override
    @Transactional
    public void deleteCampaign(UUID publicId){
        CampaignEntity campaign = findByPublicId(publicId);
        ///usar baja logica?
        repository.delete(campaign);
    }
}
