package org.example.project_pfe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantResponse {

    private Integer idUser;
    private String nom;
    private String prenom;
    private String email;
    private String numeroInscription;
    private LocalDate dateInscription;
    private FiliereResponse filiere;
}