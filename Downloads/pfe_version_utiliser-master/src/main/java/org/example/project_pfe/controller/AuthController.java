package org.example.project_pfe.controller;

import lombok.RequiredArgsConstructor;
import org.example.project_pfe.config.JwtUtil;
import org.example.project_pfe.dto.request.LoginRequest;
import org.example.project_pfe.dto.response.AuthResponse;
import org.example.project_pfe.model.Utilisateur;
import org.example.project_pfe.repository.UtilisateurRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // @Lazy évite la dépendance circulaire avec SecurityConfig
    @Lazy
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UtilisateurRepository utilisateurRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        // 1. Spring Security vérifie email + mot de passe
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()
                )
        );

        // 2. Récupère le rôle de l'utilisateur authentifié (enlève le préfixe ROLE_)
        String role = auth.getAuthorities().iterator().next().getAuthority();
        if (role.startsWith("ROLE_")) {
            role = role.substring(5); // Remove "ROLE_" prefix
        }

        // 3. Récupère les informations de l'utilisateur
        Utilisateur user = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 4. Génère le token JWT
        String token = jwtUtil.generateToken(request.getEmail(), role);

        // 5. Retourne le token au client avec prenom et nom
        return ResponseEntity.ok(new AuthResponse(token,user.getNom(),user.getPrenom(),role,request.getEmail()  ));
    }
}