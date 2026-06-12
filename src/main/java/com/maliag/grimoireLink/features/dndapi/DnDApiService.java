package com.maliag.grimoireLink.features.dndapi;
import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;
import com.maliag.grimoireLink.features.dndapi.dto.*;
import com.maliag.grimoireLink.features.dndapi.exceptions.ClassNotFoundException;
import com.maliag.grimoireLink.features.dndapi.exceptions.RaceNotFoundException;
import com.maliag.grimoireLink.features.monsters.exceptions.MonsterNotFoundException;
import com.maliag.grimoireLink.features.spellsXCharacter.exceptions.SpellNotFoundException;
import org.springframework.core.type.classreading.ClassFormatException;
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
                            throw new ClassNotFoundException("Class not found:" + Classindex  ); ///cambiar dps
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
                .uri("/api/2014/subclasses/{index}", index)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        (request, response) -> {
                            throw new ClassNotFoundException("Subclass not found: " + index);
                        }
                )
                .body(DndReference.class);
    }

    public DndReference getRaceByIndex(String index){
        return restClient.get()
                .uri("/api/2014/races/{index}", index)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        (request, response) -> {
                            throw new RaceNotFoundException("Race not found: " + index);
                        }
                )
                .body(DndReference.class);
    }

    public List<DndReference> getRaceFeatures(String index){
        RaceDetails details = restClient.get()
                .uri("/api/2014/races/{index}", index)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        (request, response) -> {
                            throw new RaceNotFoundException("Race not found: " + index);
                        }
                )
                .body(RaceDetails.class);

        if (details == null || details.getTraits() == null) return List.of();
        return details.getTraits();
    }

    public DndReference getSpellByIndex(String index){
        return restClient.get()
                .uri("/api/2014/spells/{index}", index)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        (request, response) -> {
                            throw new SpellNotFoundException("Spell not found: " + index);
                        }
                )
                .body(DndReference.class);
    }

    public DndReference getEquipmentByIndex(String index){
        return restClient.get()
                .uri("/api/2014/equipment/{index}",index)
                .retrieve()
                .body(DndReference.class);
    }

    public DndReference getMagicItemByIndex(String index){
        return restClient.get()
                .uri("/api/2014/magic-items/{index}",index)
                .retrieve()
                .body(DndReference.class);
    }

    public SpellcastingSlots getSpellSlots(String classIndex,int level){
        ClassLevelDetail detail=restClient.get()
                .uri("/api/2014/classes/{classIndex}/levels/{level}",classIndex,level)
                .retrieve()
                .body(ClassLevelDetail.class);

        if (detail == null) return null;

        return detail.getSpellcasting();
    }

/// Helper para devolver los slots por nivel
    public Integer getMaxSpellsLevel(String Classindex, int characterLevel)
    {
        SpellcastingSlots slots=getSpellSlots(Classindex,characterLevel);
        if (slots == null) return 0;


        int[] spellSlots = {
                0,/// nunca sos lvl 0  xd
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
                        status -> status.value() == 403 || status.value() == 404,
                        (request, response) -> {
                            throw new MonsterNotFoundException("Monster not found: " + index);
                        }
                )
                .body(MonsterApiResponse.class);
    }




}
