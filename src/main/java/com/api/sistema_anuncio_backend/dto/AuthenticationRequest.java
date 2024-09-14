package com.api.sistema_anuncio_backend.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;

}
