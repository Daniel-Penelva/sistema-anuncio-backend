package com.api.sistema_anuncio_backend.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.api.sistema_anuncio_backend.services.jwt.JwtRequestFilter;

//import static org.springframework.security.config.Customizer.withDefaults;  // importado manualmente para evitar conflitos com o método cors()

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtRequestFilter requestFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        
        final String clientAppUrl = "https://sistemadeanuncio.netlify.app";

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(clientAppUrl)); // aqui adiciona os domínios permitidos
        configuration.setAllowCredentials(true); // permite credenciais (cookies, autenticação HTTP básica, etc.)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // aqui os headers permitidos explicitamente
        configuration.setAllowedHeaders(List.of(
            "Authorization",
            "Content-Type",
            "X-Requested-With"
        ));

        // garantir que o frontend veja os headers personalizados também
        configuration.setExposedHeaders(List.of("Authorization"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // libera o OPTIONS
                    .requestMatchers("/authenticate", "/company/sign-up", "/client/sign-up", "/ads", "/search/{service}").permitAll()
                    .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
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
