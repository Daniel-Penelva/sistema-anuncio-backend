package com.api.sistema_anuncio_backend.services.client;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.sistema_anuncio_backend.dto.AdDTO;
import com.api.sistema_anuncio_backend.dto.AdDetailsForClientDTO;
import com.api.sistema_anuncio_backend.dto.ReservationDTO;
import com.api.sistema_anuncio_backend.dto.ReviewDTO;
import com.api.sistema_anuncio_backend.entity.Ad;
import com.api.sistema_anuncio_backend.entity.Reservation;
import com.api.sistema_anuncio_backend.entity.Review;
import com.api.sistema_anuncio_backend.entity.User;
import com.api.sistema_anuncio_backend.enums.ReservationStatus;
import com.api.sistema_anuncio_backend.enums.ReviewStatus;
import com.api.sistema_anuncio_backend.exception.ResourceNotFoundException;
import com.api.sistema_anuncio_backend.repository.AdRepository;
import com.api.sistema_anuncio_backend.repository.ReservationRepository;
import com.api.sistema_anuncio_backend.repository.ReviewRepository;
import com.api.sistema_anuncio_backend.repository.UserRepository;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    // Este método recupera todos os anúncios da base de dados utilizando o repositório
    public List<AdDTO> getAllAds() {
        return adRepository.findAll().stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    // Este método recupera uma lista de anúncio(s) da base de dados, passando o parâmetro name (nome do serviço)
    public List<AdDTO> searchAdByName(String name) {
        return adRepository.findAllByServiceNameContaining(name).stream().map(Ad::getAdDto)
                .collect(Collectors.toList());
    }

    // Este método é responsável por registrar uma reserva de um serviço de anúncio
    public boolean bookService(ReservationDTO reservationDTO) {
        
        Optional<Ad> optionalAd = adRepository.findById(reservationDTO.getAdId());
        Optional<User> optionalUser = userRepository.findById(reservationDTO.getUserId());

        if(optionalAd.isPresent() && optionalUser.isPresent()) {
            Reservation reservation = new Reservation();

            reservation.setBookDate(reservationDTO.getBookDate());           // define a data de reserva com a data passada no DTO.
            reservation.setReservationStatus(ReservationStatus.PENDING);     // define o status inicial da reserva como PENDING (pendente), o que significa que a reserva ainda precisa ser aprovada ou rejeitada.
            reservation.setUser(optionalUser.get());                         // associa o usuário que fez a reserva, obtido a partir do banco de dados, à reserva.

            reservation.setAd(optionalAd.get());                             // define o anúncio que está sendo reservado.
            reservation.setCompany(optionalAd.get().getUser());              // define a empresa responsável pela reserva. Aqui, o método obtém o usuário associado ao anúncio (assumindo que o anúncio está vinculado a um usuário com perfil de empresa).
            reservation.setReviewStatus(ReviewStatus.FALSE);

            reservationRepository.save(reservation);                         // define o status da avaliação como FALSE, indicando que a reserva ainda não foi avaliada ou revisada.
            return true;
        }
        return false;
    }

    // Este método é responsável por buscar os detalhes de um anúncio a partir do seu ID (adId) e retornar um objeto AdDetailsForClientDTO contendo as informações.
    public AdDetailsForClientDTO getAdDetailsByAdId(Long adId) {
        Optional<Ad> optionalAd = adRepository.findById(adId);

        // Se o anúncio não for encontrado, lança uma exceção personalizada
        if (optionalAd.isEmpty()) {
            throw new ResourceNotFoundException("Anúncio com o ID " + adId + " não foi encontrado.");
        }

        // Se encontrado, cria e retorna o DTO
        AdDetailsForClientDTO adDetailsForClientDTO = new AdDetailsForClientDTO();
        adDetailsForClientDTO.setAdDTO(optionalAd.get().getAdDto());
        return adDetailsForClientDTO;
    }

    // Este método é responsável por buscar todas as reservas do usuário com o Id fornecido.
    public List<ReservationDTO> getAllBookingsByUserId(Long userId) {
        return reservationRepository.findAllByUserId(userId).stream().map(Reservation::getReservationDTO).collect(Collectors.toList());
    }

    // Este método é responsável para que um usuário envie uma avaliação (Review) relacionada a uma reserva (Reservation) e um anúncio (Ad).
    public Boolean giveReview(ReviewDTO reviewDTO) {
        
        Optional<User> optionalUser = userRepository.findById(reviewDTO.getUserId());
        Optional<Reservation> optionalBooking = reservationRepository.findById(reviewDTO.getBookId());

        if(optionalUser.isPresent() && optionalBooking.isPresent()) {

            Review review = new Review();
            
            review.setReviewDate(new Date());
            review.setReview(reviewDTO.getReview());
            review.setRating(reviewDTO.getRating());
            
            review.setUser(optionalUser.get());
            review.setAd(optionalBooking.get().getAd());

            reviewRepository.save(review);

            Reservation booking = optionalBooking.get();
            booking.setReviewStatus(ReviewStatus.TRUE);

            reservationRepository.save(booking);

            return true;
        }

        return false;
    }
}
