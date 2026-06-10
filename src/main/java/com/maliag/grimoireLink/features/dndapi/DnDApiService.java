package com.maliag.grimoireLink.features.dndapi;
import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;
import com.maliag.grimoireLink.features.dndapi.dto.*;
import com.maliag.grimoireLink.features.monsters.exceptions.MonsterNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class DnDApiService {

    private final RestClient restClient;

    /// Constructor

    public DnDApiService(RestClient restClient) {
        this.restClient = restClient;
    }

    /// get de razas
    public List<DndReference> getRaces()
    {
        DndReferenceList dto = restClient.get()
            .uri("/api/2014/races")
            .retrieve()
            .body(DndReferenceList.class);

        if(dto != null)
        {
            return dto.getResults();
        }
        /// Lista sola
        return List.of();
    }

/// get classes
    public List<DndReference> getClasses()
    {
        DndReferenceList dto = restClient.get()
                .uri("/api/2014/classes")
                .retrieve()
                .body(DndReferenceList.class);

        if (dto != null)
        {
            return dto.getResults();
        }

        return  List.of();
    }

    /// getclassDetails

    public ClassDetail getclassdetails(String Classindex)
    {
        ClassDetail dto = restClient.get()
                .uri("/api/2014/classes/{index}",Classindex)
                .retrieve()
                .onStatus(
                        status -> status.value()==400,
                        (request, response) -> {
                            throw new IllegalArgumentException("Clase no existente:" + Classindex  ); ///cambiar dps
                        }
                )
                .body(ClassDetail.class);

        return dto;
    }

    /// Get FeaturesXClass

    public List<DndReference>getFeaturesForClass(String Classindex, int MaxLvl){
         ClassLevelDetail[] levelDetail=restClient.get()
                 .uri("/api/2014/classes/{Classindex}/levels",Classindex)
                 .retrieve()
                 .body(ClassLevelDetail[].class);

         if(levelDetail == null) return List.of();

         List<DndReference>features=new ArrayList<>();
         for (ClassLevelDetail levels:levelDetail)
         {
             /// Validaciones///Agregar GLOBALHANDLER
             if (levels.getLevel() != null
                 &&  levels.getLevel() <= MaxLvl
                 &&  levels.getFeatures() != null)
             {
                 features.addAll(levels.getFeatures());
             }
         }
         return features;


    }
    public DndReference getSubclass(String index){
         return restClient.get()
                 .uri("api/2014/subclasses/{index}", index)
                 .retrieve()
                 .body(DndReference.class);
    }

    public DndReference getRaceByIndex(String index){
        return restClient.get()
                .uri("/api/2014/races/{index}")
                .retrieve()
                .body(DndReference.class);
    }

    public List<DndReference> getRaceFeatures(String index){
        RaceDetails details=restClient.get()
                .uri("/api/2014/races/{index}",index)
                .retrieve()
                .body(RaceDetails.class);
        if (details == null || details.getTraits() == null){
            return List.of();
        }

        return details.getTraits();
    }


/// Helper para devolver los slots por nivel
    public Integer getMaxSpellsLevel(String Classindex, int characterLevel)
    {
        ClassLevelDetail detail=restClient.get()
                .uri("/api/2014/classes/{ClassIndex}/levels",Classindex,characterLevel)
                .retrieve()
                .body(ClassLevelDetail.class);

        if(detail == null || detail.getSpellcasting() == null)
        {
            return 0;
        }

        SpellcastingSlots slots = detail.getSpellcasting();
        int[] spellSlots = {
                0, // índice 0 no se usa
                slots.getSpellSlotsLevel1(),
                slots.getSpellSlotsLevel2(),
                slots.getSpellSlotsLevel3(),
                slots.getSpellSlotsLevel4(),
                slots.getSpellSlotsLevel5(),
                slots.getSpellSlotsLevel6(),
                slots.getSpellSlotsLevel7(),
                slots.getSpellSlotsLevel8(),
                slots.getSpellSlotsLevel9()
        };

        for (int i = 9; i >= 0; i--) {
            if (spellSlots[i] > 0) return i;
        }

        return 0;
    }


    public List<DndReference>getSpellsForClassAndLvl(String ClassIndex , int characterlevel)
    {
        int maxlevel=getMaxSpellsLevel(ClassIndex,characterlevel);
        if(maxlevel == 0)return List.of();

        DndReferenceList classSpells=restClient.get()
                .uri("/api/2014/classes/{classindex}/spells",ClassIndex)
                .retrieve()
                .body(DndReferenceList.class);

        if(classSpells == null ||  classSpells.getResults() == null ) return List.of();

        return classSpells.getResults();

    }

    public MonsterApiResponse getMonsterByIndex(String index) {
        return restClient.get()
                .uri("/api/2014/monsters/{index}", index)
                .retrieve()
                .onStatus(
                        status -> status.value() == 403,
                        (request, response) -> {
                            throw new MonsterNotFoundException("Monster not found: " + index);
                        }
                )
                .body(MonsterApiResponse.class);
    }




}
