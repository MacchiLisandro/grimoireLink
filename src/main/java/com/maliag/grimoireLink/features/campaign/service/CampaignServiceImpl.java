package com.maliag.grimoireLink.features.campaign.service;

import com.maliag.grimoireLink.features.campaign.model.CampaignEntity;
import com.maliag.grimoireLink.features.campaign.mapper.CampaignMapper;
import com.maliag.grimoireLink.features.campaign.repository.CampaignRepository;
import com.maliag.grimoireLink.features.campaign.dto.CampaignRequest;
import com.maliag.grimoireLink.features.campaign.dto.CampaignResponse;
import com.maliag.grimoireLink.features.campaign.dto.UpdateCampaignRequest;

import com.maliag.grimoireLink.features.campaign.exceptions.AlreadyMemberException;
import com.maliag.grimoireLink.features.campaign.exceptions.CampaignNotFoundException;
import com.maliag.grimoireLink.features.users.models.UserEntity;
import com.maliag.grimoireLink.features.users.repositories.UserRepository;
import com.maliag.grimoireLink.features.users.services.UserService;
import com.maliag.grimoireLink.features.usersXCampaign.Role;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignEntity;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignMapper;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignRepository;
import com.maliag.grimoireLink.features.usersXCampaign.dto.CampaignMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository repository;
    private final CampaignMapper mapper;
    private final UsersXCampaignRepository uxcRepository;
    private final UsersXCampaignMapper uxcMapper;

    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public CampaignEntity findByPublicId(UUID publicId){
        return repository.findByPublicId(publicId)
                .orElseThrow(()-> new CampaignNotFoundException("Campaign not found"));
    }

    @Override
    public CampaignResponse getByPublicId(UUID publicId){
        return mapper.toResponse(findByPublicId(publicId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResponse> getAllCampaignsByUser(){
        UserEntity user = userService.getLoggedUserEntity();
        return uxcRepository.findCampaignsByUserPublicId(user.getPublicId()).stream()
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
        UserEntity user = userService.getLoggedUserEntity();
        UsersXCampaignEntity membership = UsersXCampaignEntity.builder()
                .user(user)
                .campaign(campaign)
                .role(Role.DUNGEON_MASTER)
                .build();
        uxcRepository.save(membership);
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

        UserEntity user = userService.getLoggedUserEntity();
        CampaignEntity campaign = repository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new CampaignNotFoundException("Código de invitación inválido"));

        boolean alreadyMember = uxcRepository.existsByUserAndCampaign(user, campaign);
        if (alreadyMember) {
            throw new AlreadyMemberException("Ya sos miembro de esta campaña");
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
