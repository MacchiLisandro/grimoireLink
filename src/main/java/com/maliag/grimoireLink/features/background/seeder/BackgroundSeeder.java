package com.maliag.grimoireLink.features.background.seeder;

import com.maliag.grimoireLink.features.background.model.BackgroundEntity;
import com.maliag.grimoireLink.features.background.repository.BackgroundRepository;
import com.maliag.grimoireLink.features.background.dto.BackGroundSeedDto;
import com.maliag.grimoireLink.features.backgroundSkills.BackGroundSkillsEntity;
import com.maliag.grimoireLink.features.backgroundSkills.BackgroundSkillsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BackgroundSeeder  implements ApplicationRunner {

    private final BackgroundRepository backgroundRepository;
    private final BackgroundSkillsRepository backgroundSkillsRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (backgroundRepository.count() > 0){
            log.info("Backgrounds ya cargado.Seeder sigue");
            return;
        }

        InputStream inputStream = new ClassPathResource("backgrounds_seed.json").getInputStream();

        List<BackGroundSeedDto> seeds=objectMapper.readValue(
                inputStream, new TypeReference<List<BackGroundSeedDto>>() {});

        for (BackGroundSeedDto seedDto : seeds){
            BackgroundEntity background = BackgroundEntity.builder()
                    .backgroundIndex(seedDto.getIndex())
                    .name(seedDto.getName())
                    .description(seedDto.getDescription())
                    .languages(seedDto.getLanguage())
                    .build();

            backgroundRepository.save(background);

            for (String skillIndex : seedDto.getSkills()){
                BackGroundSkillsEntity skill = BackGroundSkillsEntity.builder()
                        .skillIndex(skillIndex)
                        .skillname(toDisplayName(skillIndex))
                        .background(background)
                        .build();

                backgroundSkillsRepository.save(skill);
            }
        }
    }
    private String toDisplayName(String skillIndex) {
        String[] parts = skillIndex.split("-");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            sb.append(Character.toUpperCase(part.charAt(0)));
            sb.append(part.substring(1));
            if (i < parts.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
