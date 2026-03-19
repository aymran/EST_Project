package org.example.project_pfe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String prenom;
    private String nom;
    private String role;
    private String email;
}