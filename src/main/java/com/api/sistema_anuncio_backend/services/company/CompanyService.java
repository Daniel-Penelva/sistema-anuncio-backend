package com.api.sistema_anuncio_backend.services.company;

import java.io.IOException;
import java.util.List;

import com.api.sistema_anuncio_backend.dto.AdDTO;
import com.api.sistema_anuncio_backend.dto.ReservationDTO;

public interface CompanyService {

    boolean postAd(Long userId, AdDTO adDTO) throws IOException;

    List<AdDTO> getAllAds(Long userId);

    AdDTO getAdById(Long adId);

    boolean updateAd(Long adId, AdDTO adDTO) throws IOException;

    boolean deleteAd(Long adId);

    List<ReservationDTO> getAllAdBookings(Long companyId);
    
}
