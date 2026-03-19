package org.example.project_pfe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project_pfe.model.TypeExamen;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenRequest {

    @NotNull(message = "La date de l'examen est obligatoire")
    private LocalDate dateExamen;

    // Used to calculate duree — not stored directly
    @NotNull(message = "L'heure de début est obligatoire")
    private String heureDebut;

    @NotNull(message = "L'heure de fin est obligatoire")
    private String heureFin;

    @NotNull(message = "Le type d'examen est obligatoire")
    private TypeExamen typeExamen;

    @NotNull(message = "L'ID de la matière est obligatoire")
    private Integer idMatiere;

    // Salle name (e.g. "A101") — looked up by name in service
    private String salle;
}