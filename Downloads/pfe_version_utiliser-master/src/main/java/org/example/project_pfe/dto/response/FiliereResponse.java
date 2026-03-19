package org.example.project_pfe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiliereResponse {

    private Integer idFiliere;
    private String intitule;
    private String semestre;
}