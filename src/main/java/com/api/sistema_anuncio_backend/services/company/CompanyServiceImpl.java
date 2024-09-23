package com.api.sistema_anuncio_backend.services.company;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.sistema_anuncio_backend.dto.AdDTO;
import com.api.sistema_anuncio_backend.entity.Ad;
import com.api.sistema_anuncio_backend.entity.User;
import com.api.sistema_anuncio_backend.repository.AdRepository;
import com.api.sistema_anuncio_backend.repository.UserRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdRepository adRepository;

    // Método que permite a postagem de anúncios (Ads) por um usuário
    public boolean postAd(Long userId, AdDTO adDTO) throws IOException {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Ad ad = new Ad();
            ad.setServiceName(adDTO.getServiceName());
            ad.setDescription(adDTO.getDescription());
            ad.setImg(adDTO.getImg().getBytes());
            ad.setPrice(adDTO.getPrice());
            ad.setUser(optionalUser.get());

            adRepository.save(ad);
            return true;
        }

        return false;
    }

}
