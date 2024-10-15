package com.api.sistema_anuncio_backend.services.client;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.sistema_anuncio_backend.dto.AdDTO;
import com.api.sistema_anuncio_backend.entity.Ad;
import com.api.sistema_anuncio_backend.repository.AdRepository;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private AdRepository adRepository;

    // Este método recupera todos os anúncios da base de dados utilizando o repositório
    public List<AdDTO> getAllAds() {
        return adRepository.findAll().stream().map(Ad::getAdDto).collect(Collectors.toList());
    }
}
