package com.api.sistema_anuncio_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.sistema_anuncio_backend.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    
    List<Review> findAllByAdId(Long adId);
}
