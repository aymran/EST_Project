package org.example.project_pfe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatiereRequest {

    @NotBlank(message = "L'intitulé est obligatoire")
    private String intitule;

    @NotNull(message = "Le coefficient est obligatoire")
    @Positive(message = "Le coefficient doit être positif")
    private Double coefficient;

    @Positive(message = "Le volume horaire doit être positif")
    private Integer volumeHoraire;

    private String semestre;

    @NotNull(message = "L'ID de l'enseignant est obligatoire")
    private Integer idEnseignant;
}*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatiereRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String intitule;  // Change from 'intitule'

    @Positive(message = "La masse horaire doit être positive")
    private Integer volumeHoraire;  // Change from 'volumeHoraire'

    @NotNull(message = "Le coefficient est obligatoire")
    @Positive(message = "Le coefficient doit être positif")
    private Double coefficient;

    @NotNull(message = "L'ID de l'enseignant est obligatoire")
    private Integer idEnseignant;  // Change from 'idEnseignant'

    @NotNull(message = "L'ID de la filière est obligatoire")
    private Integer filiereId;  // Add filiereId
}