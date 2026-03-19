package org.example.project_pfe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnseignantResponse {

    private Integer idUser;
    private String nom;
    private String prenom;
    private String email;
    private String grade;
    private String specialite;
    private String role;
}
