package com.api.sistema_anuncio_backend.services.client;

import java.util.List;

import com.api.sistema_anuncio_backend.dto.AdDTO;

public interface ClientService {

    List<AdDTO> getAllAds();
    
}
