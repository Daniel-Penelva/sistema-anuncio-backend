package com.api.sistema_anuncio_backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Permite a origem do frontend
        corsConfiguration.setAllowedOrigins(List.of("https://sistemadeanuncio.netlify.app"));

        // Permite envio de cookies e credenciais
        corsConfiguration.setAllowCredentials(true);

        // Permite todos os métodos HTTP usados no backend
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permite os headers que utiliza
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

        // Expõe o header Authorization para o frontend (útil para tokens JWT)
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        // Define o tempo que o resultado do preflight pode ser cacheado pelo navegador (em segundos)
        corsConfiguration.setMaxAge(3600L);

        // Aplica a configuração para todas as rotas da API
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}