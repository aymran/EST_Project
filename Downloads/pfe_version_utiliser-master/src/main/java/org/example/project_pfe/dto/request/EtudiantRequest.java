package org.example.project_pfe.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    // No @NotBlank — optional on update, required only on create (checked in service)
    private String motDePasse;

    @NotBlank(message = "Le numéro d'inscription est obligatoire")
    private String numeroInscription;

    @NotNull(message = "La date d'inscription est obligatoire")
    private LocalDate dateInscription;

    @NotNull(message = "L'ID de la filière est obligatoire")
    private Integer idFiliere;
}