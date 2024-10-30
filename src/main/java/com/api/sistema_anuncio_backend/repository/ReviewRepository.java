package com.api.sistema_anuncio_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.sistema_anuncio_backend.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    
}
