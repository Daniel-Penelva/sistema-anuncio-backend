package com.api.sistema_anuncio_backend.services.authentication;

import com.api.sistema_anuncio_backend.dto.SignupRequestDTO;
import com.api.sistema_anuncio_backend.dto.UserDto;

public interface AuthService {

    UserDto signupClient(SignupRequestDTO signupRequestDTO);

}
