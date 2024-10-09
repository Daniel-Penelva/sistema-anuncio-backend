package com.api.sistema_anuncio_backend.services.company;

import java.io.IOException;
import java.util.List;

import com.api.sistema_anuncio_backend.dto.AdDTO;

public interface CompanyService {

    boolean postAd(Long userId, AdDTO adDTO) throws IOException;

    List<AdDTO> getAllAds(Long userId);

    AdDTO getAdById(Long adId);
    
}
