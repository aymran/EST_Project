package org.example.project_pfe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project_pfe.model.TypeExamen;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenResponse {

    private Integer idExamen;
    private LocalDate dateExamen;
    private Integer duree; // in minutes
    private TypeExamen typeExamen;
    private MatiereResponse matiere;
    private SalleResponse salle;
}