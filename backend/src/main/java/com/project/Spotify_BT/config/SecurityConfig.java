package com.project.Spotify_BT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Configuración de las peticiones HTTP que vamos a permitir y las rutas de las mismas
            .authorizeHttpRequests(auth -> {
                auth
                  // Añade aquí todos los endpoints que deseas permitir sin autenticación
                  .requestMatchers(
                      "/auth/spotify",           // POST /auth/spotify
                      "/auth/spotify/callback",  // GET /auth/spotify/callback
                      "/spotify/token",          // GET /spotify/token
                      "/user/profile",           // GET /user/profile
                      "/me/top/artists",         // GET /me/top/artists
                      "/artists/**",             // GET /artists/{artistID}
                      "/album/search/**",        // GET /album/search/{artistName}
                      "/albums/**",              // GET /albums/{albumID}
                      "/search/**",              // GET /search/{searchTerm}
                      "/login",
                      "/loginPage.html",
                      "/error"
                  )
                  .permitAll()
                  
                  // Todas las demás peticiones deben de estar 'Seguras'  
                  .anyRequest().authenticated();
            })

            .formLogin(form -> {
                form.loginPage("/login");
                form.permitAll();
            })

            .logout(logout -> {
                logout.logoutSuccessUrl("/login");
                logout.permitAll();
            })

            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Permite los orígenes de tu front
        config.setAllowedOrigins(List.of("http://192.168.0.69:9090", "http://localhost:9090"));

        // Métodos que permites (GET, POST, etc.)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Encabezados permitidos
        config.setAllowedHeaders(List.of("Content-Type", "Authorization", "Accept", "X-Requested-With"));

        // Si tu app usa sesión (cookies) o tokens vía cookies
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica la configuración a cualquier endpoint
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
