package com.api.sistema_anuncio_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.sistema_anuncio_backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
