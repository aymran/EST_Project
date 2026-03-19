package org.example.project_pfe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.FiliereRequest;
import org.example.project_pfe.dto.response.FiliereResponse;
import org.example.project_pfe.service.FiliereService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filieres")
@RequiredArgsConstructor
public class FiliereController {

    private final FiliereService filiereService;

    /**
     * GET /api/filieres
     * Récupérer toutes les filières
     */
    @GetMapping
    public ResponseEntity<List<FiliereResponse>> getAllFilieres() {
        List<FiliereResponse> filieres = filiereService.getAllFilieres();
        return ResponseEntity.ok(filieres);
    }

    /**
     * GET /api/filieres/{id}
     * Récupérer une filière par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<FiliereResponse> getFiliereById(@PathVariable Integer id) {
        FiliereResponse filiere = filiereService.getFiliereById(id);
        return ResponseEntity.ok(filiere);
    }

    /**
     * GET /api/filieres/intitule/{intitule}
     * Rechercher une filière par intitulé
     */
    @GetMapping("/intitule/{intitule}")
    public ResponseEntity<FiliereResponse> getFiliereByIntitule(@PathVariable String intitule) {
        FiliereResponse filiere = filiereService.getFiliereByIntitule(intitule);
        return ResponseEntity.ok(filiere);
    }

    /**
     * GET /api/filieres/my
     * Récupérer les filières associées aux matières de l'enseignant connecté
     */
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public ResponseEntity<List<FiliereResponse>> getMyFilieres(Authentication authentication) {
        List<FiliereResponse> filieres = filiereService.getFilieresByEnseignant(authentication.getName());
        return ResponseEntity.ok(filieres);
    }

    /**
     * POST /api/filieres
     * Créer une nouvelle filière
     */
    @PostMapping
    public ResponseEntity<FiliereResponse> createFiliere(@Valid @RequestBody FiliereRequest request) {
        FiliereResponse created = filiereService.createFiliere(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/filieres/{id}
     * Mettre à jour une filière
     */
    @PutMapping("/{id}")
    public ResponseEntity<FiliereResponse> updateFiliere(
            @PathVariable Integer id,
            @Valid @RequestBody FiliereRequest request) {
        FiliereResponse updated = filiereService.updateFiliere(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/filieres/{id}
     * Supprimer une filière
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFiliere(@PathVariable Integer id) {
        filiereService.deleteFiliere(id);
        return ResponseEntity.noContent().build();
    }
}