package com.maliag.grimoireLink.features.dndapi;
import com.maliag.grimoireLink.features.dndapi.dto.DndReference;
import com.maliag.grimoireLink.features.dndapi.dto.DndReferenceList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
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

    /// classDetails

    public DndReference classdetails(String Classindex)
    {
        DndReference dto = restClient.get()
                .uri("/api/2014/classes" + Classindex)
                .retrieve()
                .onStatus(
                        status -> status.value()==400,
                        (request, response) -> {
                            throw new RuntimeException(Classindex); ///cambiar dps
                        }
                )
                .body(DndReference.class);

        return dto;
    }





}
