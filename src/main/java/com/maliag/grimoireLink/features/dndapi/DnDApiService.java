package com.maliag.grimoireLink.features.dndapi;
import com.maliag.grimoireLink.features.dndapi.dto.*;
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
                .uri("/api/2014/classes" + Classindex)
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

    /// Get FeatsXClass

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






}
