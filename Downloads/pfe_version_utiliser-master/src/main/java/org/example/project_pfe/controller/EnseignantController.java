package org.example.project_pfe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.EnseignantRequest;
import org.example.project_pfe.dto.response.EnseignantResponse;
import org.example.project_pfe.dto.response.FiliereResponse;
import org.example.project_pfe.service.EnseignantService;
import org.example.project_pfe.service.FiliereService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")

@RequiredArgsConstructor
public class EnseignantController {

    private final EnseignantService enseignantService;
    private final FiliereService filiereService;
  

    /**
     * GET /api/enseignants
     * Récupérer tous les enseignants
     */
    @GetMapping
    public ResponseEntity<List<EnseignantResponse>> getAllEnseignants() {
        List<EnseignantResponse> enseignants = enseignantService.getAllEnseignants();
        return ResponseEntity.ok(enseignants);
    }

    /**
     * GET /api/enseignants/{id}
     * Récupérer un enseignant par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnseignantResponse> getEnseignantById(@PathVariable Integer id) {
        EnseignantResponse enseignant = enseignantService.getEnseignantById(id);
        return ResponseEntity.ok(enseignant);
    }

    /**
     * GET /api/enseignants/email/{email}
     * Rechercher un enseignant par email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<EnseignantResponse> getEnseignantByEmail(@PathVariable String email) {
        EnseignantResponse enseignant = enseignantService.getEnseignantByEmail(email);
        return ResponseEntity.ok(enseignant);
    }

    /**
     * GET /api/enseignants/grade/{grade}
     * Rechercher les enseignants par grade
     */
    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<EnseignantResponse>> getEnseignantsByGrade(@PathVariable String grade) {
        List<EnseignantResponse> enseignants = enseignantService.getEnseignantsByGrade(grade);
        return ResponseEntity.ok(enseignants);
    }

    /**
     * GET /api/enseignants/specialite/{specialite}
     * Rechercher les enseignants par spécialité
     */
    @GetMapping("/specialite/{specialite}")
    public ResponseEntity<List<EnseignantResponse>> getEnseignantsBySpecialite(@PathVariable String specialite) {
        List<EnseignantResponse> enseignants = enseignantService.getEnseignantsBySpecialite(specialite);
        return ResponseEntity.ok(enseignants);
    }

    /**
     * POST /api/enseignants
     * Créer un nouvel enseignant
     */
    @PostMapping
    public ResponseEntity<EnseignantResponse> createEnseignant(@Valid @RequestBody EnseignantRequest request) {
        EnseignantResponse created = enseignantService.createEnseignant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/enseignants/{id}
     * Mettre à jour un enseignant
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnseignantResponse> updateEnseignant(
            @PathVariable Integer id,
            @Valid @RequestBody EnseignantRequest request) {
        EnseignantResponse updated = enseignantService.updateEnseignant(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/enseignants/{id}
     * Supprimer un enseignant
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Integer id) {
        enseignantService.deleteEnseignant(id);
        return ResponseEntity.noContent().build();
    }


}