package com.maliag.grimoireLink.features.dndapi;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DnDApiClient {

    private final RestClient restClient;

    /// Constructor

    public DnDApiClient(RestClient restClient) {
        this.restClient = restClient;
    }





}
