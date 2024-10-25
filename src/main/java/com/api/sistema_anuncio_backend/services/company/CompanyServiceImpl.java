package com.api.sistema_anuncio_backend.services.company;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.sistema_anuncio_backend.dto.AdDTO;
import com.api.sistema_anuncio_backend.dto.ReservationDTO;
import com.api.sistema_anuncio_backend.entity.Ad;
import com.api.sistema_anuncio_backend.entity.Reservation;
import com.api.sistema_anuncio_backend.entity.User;
import com.api.sistema_anuncio_backend.repository.AdRepository;
import com.api.sistema_anuncio_backend.repository.ReservationRepository;
import com.api.sistema_anuncio_backend.repository.UserRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private ReservationRepository reservationRepository;

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

    // Método que exibi os anúncios de um usuário específico ao carregar a página.
    public List<AdDTO> getAllAds(Long userId) {
        return adRepository.findAllByUserId(userId)
                .stream()
                .map(Ad::getAdDto)
                .collect(Collectors.toList());
    }

    // Método que recupera os detalhes de um anúncio a partir do banco de dados com base no seu ID (adId).
    public AdDTO getAdById(Long adId) {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if (optionalAd.isPresent()) {
            return optionalAd.get().getAdDto();
        }

        return null;
    }


    // Método responsável por buscar um anúncio (Ad) com base no adId passado como argumento.
    public boolean updateAd(Long adId, AdDTO adDTO) throws IOException {

        Optional<Ad> optionalAd = adRepository.findById(adId);

        if (optionalAd.isPresent()) {
            Ad ad = optionalAd.get();

            ad.setServiceName(adDTO.getServiceName());
            ad.setDescription(adDTO.getDescription());
            ad.setPrice(adDTO.getPrice());

            if (adDTO.getImg() != null) {
                ad.setImg(adDTO.getImg().getBytes());
            }

            adRepository.save(ad);
            return true;
        } else {
            return false;
        }
    }


    // Método responsável por excluir um anúncio (Ad) do sistema com base no adId passado como argumento.
    public boolean deleteAd(Long adId) {
        Optional<Ad> optionalAd = adRepository.findById(adId);

        if(optionalAd.isPresent()) {
            adRepository.delete(optionalAd.get());
            return true;
        }

        return false;
    }

    public List<ReservationDTO> getAllAdBookings(Long companyId) {
        return reservationRepository.findAllByCompanyId(companyId)
                .stream()
                .map(Reservation::getReservationDTO)
                .collect(Collectors.toList());
    }

}
