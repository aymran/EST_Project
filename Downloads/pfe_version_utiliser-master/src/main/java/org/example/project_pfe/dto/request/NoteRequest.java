package org.example.project_pfe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {

    private Integer idEtudiant;   // for single create/update
    private Integer idExamen;     // for single create/update
    private Integer idEnseignant; // for single create/update
    private Double valeur;
    private String commentaire;
    private LocalDate dateSaisie;

    // for bulk save
    private Integer etudiantId;
    private Integer examenId;
}