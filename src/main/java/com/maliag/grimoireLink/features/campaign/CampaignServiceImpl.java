package com.maliag.grimoireLink.features.campaign;

import com.maliag.grimoireLink.features.campaign.dto.CampaignRequest;
import com.maliag.grimoireLink.features.campaign.dto.CampaignResponse;
import com.maliag.grimoireLink.features.campaign.dto.UpdateCampaignRequest;
import com.maliag.grimoireLink.features.users.UserEntity;
import com.maliag.grimoireLink.features.users.UserRepository;
import com.maliag.grimoireLink.features.usersXCampaign.Role;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignEntity;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignMapper;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignRepository;
import com.maliag.grimoireLink.features.usersXCampaign.dto.CampaignMemberResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService{

    private final CampaignRepository repository;
    private final CampaignMapper mapper;
    private final UsersXCampaignRepository uxcRepository;
    private final UsersXCampaignMapper uxcMapper;

    private final UserRepository userRepository;

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

    @Transactional
    public CampaignResponse joinCampaign(String inviteCode) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        CampaignEntity campaign = repository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new EntityNotFoundException("Código de invitación inválido"));

        boolean alreadyMember = uxcRepository.existsByUserAndCampaign(user, campaign);
        if (alreadyMember) {
            throw new IllegalStateException("Ya sos miembro de esta campaña");
        }

        UsersXCampaignEntity membership = UsersXCampaignEntity.builder()
                .user(user)
                .campaign(campaign)
                .role(Role.PLAYER)
                .build();

        uxcRepository.save(membership);

        return mapper.toResponse(campaign);
    }



}
