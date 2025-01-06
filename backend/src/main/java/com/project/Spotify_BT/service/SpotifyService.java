package com.project.Spotify_BT.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class SpotifyService {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public Map<String, Object> exchangeAuthorizationCodeForToken(String authorizationCode) {
       
        // Construir URL y cuerpo de la solicitud
        String tokenUrl = "https://accounts.spotify.com/api/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", authorizationCode);
        body.add("redirect_uri", redirectUri);

        // Configurar encabezados con credenciales de Spotify
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedCredentials);

        // Crear solicitud
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Hacer la llamada a la API de Spotify
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error retrieving token from Spotify: " + response.getStatusCode());
        }
    }


    public String getUserProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.spotify.com/v1/me",
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error retrieving user profile: " + e.getResponseBodyAsString());
        }
    }


    public String getuserTopArtist(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://api.spotify.com/v1/me/top/artists?limit=8",
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error Top artis: " + e.getResponseBodyAsString());
        }
    }


    public String  getArtistInfo(String accessToken, String artistID) {
        String URL = "https://api.spotify.com/v1/artists/" + artistID;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {

            ResponseEntity<String> response = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error getting the artist information: " + e.getResponseBodyAsString());
        }
    }

    public String  getArtistAlbum(String accessToken, String artistName) {
        String URL = "https://api.spotify.com/v1/search?q=artist%3A" + artistName + "&type=album&limit=5";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {

            ResponseEntity<String> response = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error getting the artist information for album" + e.getResponseBodyAsString());
        }
    }

        

    public String  getAlbum(String accessToken, String albumID) {
        String URL = "https://api.spotify.com/v1/albums/" + albumID;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {

            ResponseEntity<String> response = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error getting the album information" + e.getResponseBodyAsString());
        }
    }
    
    


    public String  getSearchElements(String accessToken, String searchTerm) {

        String encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);

        // Si deseas codificar los tipos en tiempo de ejecución (por ejemplo, si varían):
        String rawTypes = "album,artist,track";
        String encodedTypes = URLEncoder.encode(rawTypes, StandardCharsets.UTF_8); 
        // encodedTypes será "album%2Cartist%2Ctrack"
        
        // Construir la URL
        String urlString = "https://api.spotify.com/v1/search?"
            + "q=" + encodedSearchTerm
            + "&type=" + encodedTypes
            + "&limit=5";   
       
       
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {

            ResponseEntity<String> response = restTemplate.exchange(
                    urlString,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error getting the query information" + e.getResponseBodyAsString());
        }
    }
}