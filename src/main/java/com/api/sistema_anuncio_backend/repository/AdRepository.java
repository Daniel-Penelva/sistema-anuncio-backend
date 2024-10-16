package com.api.sistema_anuncio_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.sistema_anuncio_backend.entity.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    // Método para buscar anúncio
    List<Ad> findAllByUserId(Long userId);

    // Método para buscar uma lista de anúncios de acordo com nome do serviço
    List<Ad> findAllByServiceNameContaining(String name);

}
