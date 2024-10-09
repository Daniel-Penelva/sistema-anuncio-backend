package com.api.sistema_anuncio_backend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.sistema_anuncio_backend.dto.AdDTO;
import com.api.sistema_anuncio_backend.services.company.CompanyService;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // Este método REST cria um anúncio para um usuário específico.
    @PostMapping("/ad/{userId}")
    public ResponseEntity<?> postAd(@PathVariable Long userId, @ModelAttribute AdDTO adDTO) throws IOException {

        boolean success = companyService.postAd(userId, adDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Este método busca todos os anúncios (ads) pertencentes a um determinado usuário, identificado pelo userId.
    @GetMapping("/ads/{userId}")
    public ResponseEntity<?> getAllAdsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(companyService.getAllAds(userId));
    }

    // Este método expõe uma API para que clientes possam buscar o anúncio por ID através de uma requisição HTTP GET.
    @GetMapping("/ad/{adId}")
    public ResponseEntity<?> getAdById(@PathVariable Long adId) {
        AdDTO adDTO = companyService.getAdById(adId);
        if (adDTO != null) {
            return ResponseEntity.ok(adDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
