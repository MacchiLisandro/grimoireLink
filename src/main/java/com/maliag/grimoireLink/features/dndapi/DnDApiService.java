package com.maliag.grimoireLink.features.dndapi;
import com.maliag.grimoireLink.features.dndapi.dto.DndReference;
import com.maliag.grimoireLink.features.dndapi.dto.DndReferenceList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Service
public class DnDApiService {

    private final RestClient restClient;

    /// Constructor

    public DnDApiService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<DndReference> getRaces()
    {
        DndReferenceList dto = restClient.get()
            .uri("/api/2014/races")
            .retrieve()
            .body(DndReferenceList.class);

        if(dto != null)
        {
            /// respuesta
            return dto.getResults();
        }

        /// Lista sola
        return List.of();
    }

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





}
