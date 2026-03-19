package org.example.project_pfe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {

    private Integer idNote;
    private Double valeur;
    private LocalDate dateSaisie;
    private String commentaire;
    private EtudiantResponse etudiant;
    private ExamenResponse examen;
    private EnseignantResponse enseignant;
}