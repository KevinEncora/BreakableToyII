package com.project.Spotify_BT.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.Spotify_BT.service.SpotifyService;

import java.util.Map;

@Controller
public class SpotifyAuthController {

    private final SpotifyService spotifyService;

    // Inject clientId and redirectUri directly into the controller
    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    @Value("${spotify.authorization-uri}")
    private String authorizationUri;

    @Value("${spotify.token-uri}")
    private String tokenUri;

    @Value("${spotify.scope}")
    private String scope;

    // Variable para almacenar el token
    private String spotifyAccessToken;

    public SpotifyAuthController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }


    @PostMapping("/auth/spotify")
    public ResponseEntity<Void> initiateAuthFlow() {
        String authorizeUrl = "https://accounts.spotify.com/authorize?client_id=" + clientId +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scope;

        // Redirige al usuario a Spotify
        return ResponseEntity
                .status(302)
                .header("Location", authorizeUrl)
                .build();
    }
   
    
    @GetMapping("/spotify/token")
    public ResponseEntity<?> getToken() {
        // Verifica si el token está disponible

        if (spotifyAccessToken != null) {
            return ResponseEntity.status(200).body(Map.of("access_token", spotifyAccessToken));
        }

        // Si el token no está disponible, responde con un error
        return ResponseEntity.status(404).body(Map.of("error", "Token not found"));
    }
    

    @GetMapping("/auth/spotify/callback")
    public String handleSpotifyCallback(@RequestParam("code") String code) {
        try {
            // Llama al servicio para intercambiar el código por el token
            Map<String, Object> tokenData = spotifyService.exchangeAuthorizationCodeForToken(code);
            spotifyAccessToken = tokenData.get("access_token").toString();

            // Redirige a la página de éxito o donde necesites
            return "redirect:http://192.168.0.69:9090";
            
        } catch (Exception e) {
            return "redirect:/error.html";
        }
    }


    @GetMapping("/user/profile")
    public ResponseEntity<?> getUserProfile() {
        if (spotifyAccessToken == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Access token not found"));
        }

        try {
            String userProfile = spotifyService.getUserProfile(spotifyAccessToken);
            return ResponseEntity.status(200).body(Map.of("profile", userProfile));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/me/top/artists")
    public ResponseEntity<?> getuserTopArtist() {
        if (spotifyAccessToken == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Access token not found"));
        }

        try {
            String userTopArtist = spotifyService.getuserTopArtist(spotifyAccessToken);
           
            return ResponseEntity.status(200).body(Map.of("userTopArtist", userTopArtist));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/artists/{artistID}")
    public ResponseEntity<?> getArtistInfo(@PathVariable String artistID) {


        if (spotifyAccessToken == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Access token not found"));
        }

        try {
            String artistInfo = spotifyService.getArtistInfo(spotifyAccessToken, artistID);
           
            return ResponseEntity.status(200).body(Map.of("artistInfo", artistInfo));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/album/search/{artistName}")
    public ResponseEntity<?> getArtistAlbum(@PathVariable String artistName) {


        if (spotifyAccessToken == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Access token not found"));
        }

        try {
            String artistAlbum = spotifyService.getArtistAlbum(spotifyAccessToken, artistName);
           
            return ResponseEntity.status(200).body(Map.of("artistAlbum", artistAlbum));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/albums/{albumID}")
    public ResponseEntity<?> getAlbum(@PathVariable String albumID) {

        if (spotifyAccessToken == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Access token not found"));
        }

        try {
            String album = spotifyService.getAlbum(spotifyAccessToken, albumID);
           
            return ResponseEntity.status(200).body(Map.of("album", album));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }




    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<?> getSearchElements(@PathVariable String searchTerm) {

        if (spotifyAccessToken == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Access token not found"));
        }

        try {
            String searchElements = spotifyService.getSearchElements(spotifyAccessToken, searchTerm);
           
            return ResponseEntity.status(200).body(Map.of("searchElements", searchElements));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }




}

