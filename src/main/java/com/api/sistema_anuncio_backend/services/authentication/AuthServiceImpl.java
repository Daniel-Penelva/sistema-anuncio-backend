package com.api.sistema_anuncio_backend.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.sistema_anuncio_backend.dto.SignupRequestDTO;
import com.api.sistema_anuncio_backend.dto.UserDto;
import com.api.sistema_anuncio_backend.entity.User;
import com.api.sistema_anuncio_backend.enums.UserRole;
import com.api.sistema_anuncio_backend.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    // método para registrar um novo cliente no sistema.
    public UserDto signupClient(SignupRequestDTO signupRequestDTO) {
        User user = new User();
        user.setName(signupRequestDTO.getName());
        user.setLastname(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.CLIENT);

        return userRepository.save(user).getDto();
    }

    // Método para buscar o campo de email
    public Boolean presentByEmail(String email){
        return userRepository.findFirstByEmail(email) != null;
    }

    // método para registrar um nova empresa no sistema.
    public UserDto signupCompany(SignupRequestDTO signupRequestDTO) {
        User user = new User();
        user.setName(signupRequestDTO.getName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.COMPANY);

        return userRepository.save(user).getDto();
    }
}
