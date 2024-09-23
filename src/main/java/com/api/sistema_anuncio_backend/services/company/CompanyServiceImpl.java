package com.api.sistema_anuncio_backend.services.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.sistema_anuncio_backend.repository.AdRepository;
import com.api.sistema_anuncio_backend.repository.UserRepository;

@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdRepository adRepository;
    
}
