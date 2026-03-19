package org.example.project_pfe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalleRequest {

    @NotBlank(message = "Le numéro de salle est obligatoire")
    private String numero;

    @NotNull(message = "La capacité est obligatoire")
    @Positive(message = "La capacité doit être positive")
    private Integer capacite;

    private String bloc;

    private Boolean booleanDisponible;
}