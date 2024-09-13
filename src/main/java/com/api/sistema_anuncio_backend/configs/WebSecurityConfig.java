package com.api.sistema_anuncio_backend.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.sistema_anuncio_backend.services.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtRequestFilter requestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/authenticate", "/company/sign-up", "/client/sign-up", "/ads", "/search/{service}")
                .permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/**")
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

/**
 * Resumo:
 * 1. Desabilita o CSRF, já que a autenticação é feita via token JWT.
 * 
 * 2. Permite o acesso público a determinados endpoints como /authenticate (login), /sign-up (cadastro), e outras rotas públicas.
 * 
 * 3. Exige autenticação para rotas protegidas (/api/**).
 * 
 * 4. Usa um filtro JWT personalizado (JwtRequestFilter) para verificar e validar tokens em cada requisição.
 * 
 * 5. Define o gerenciamento de sessão como "stateless", o que é ideal para aplicações com autenticação baseada em tokens.
 * 
 * 6. Fornece um AuthenticationManager configurado que pode ser utilizado para realizar a autenticação.
*/
