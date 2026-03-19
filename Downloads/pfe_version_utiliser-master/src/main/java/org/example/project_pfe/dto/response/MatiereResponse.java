package org.example.project_pfe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatiereResponse {

    private Integer idMatiere;
    private String intitule;
    private Double coefficient;
    private Integer volumeHoraire;
    private Integer idEnseignant;
    private Integer filiereId;
    private EnseignantResponse enseignant;
}