package com.api.sistema_anuncio_backend.services.client;

import java.util.List;

import com.api.sistema_anuncio_backend.dto.AdDTO;
import com.api.sistema_anuncio_backend.dto.AdDetailsForClientDTO;
import com.api.sistema_anuncio_backend.dto.ReservationDTO;

public interface ClientService {

    List<AdDTO> getAllAds();

    List<AdDTO> searchAdByName(String name);

    boolean bookService(ReservationDTO reservationDTO);

    AdDetailsForClientDTO getAdDetailsByAdId(Long adId);

    List<ReservationDTO> getAllBookingsByUserId(Long userId);
    
}
