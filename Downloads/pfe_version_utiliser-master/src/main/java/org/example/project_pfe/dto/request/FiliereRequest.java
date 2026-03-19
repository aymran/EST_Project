package org.example.project_pfe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiliereRequest {

    @NotBlank(message = "L'intitulé est obligatoire")
    private String intitule;

   private String responsable;

}*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiliereRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String intitule;
    @NotBlank(message = "semestre est obligatoire")
    private String semestre;  // Add semestre field
}