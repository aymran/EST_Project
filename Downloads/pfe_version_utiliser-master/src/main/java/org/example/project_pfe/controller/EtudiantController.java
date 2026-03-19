package org.example.project_pfe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.EtudiantRequest;
import org.example.project_pfe.dto.response.EtudiantResponse;
import org.example.project_pfe.service.EtudiantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

    private final EtudiantService etudiantService;

    /**
     * GET /api/etudiants
     * Récupérer tous les étudiants ou filtrer par filière
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT')")
    public ResponseEntity<List<EtudiantResponse>> getAllEtudiants(
            @RequestParam(required = false) Integer filiereId,
            @RequestParam(required = false) String semestre) {
        
        List<EtudiantResponse> etudiants;
        
        if (filiereId != null) {
            etudiants = etudiantService.getEtudiantsByFiliere(filiereId);
        } else {
            etudiants = etudiantService.getAllEtudiants();
        }
        
        return ResponseEntity.ok(etudiants);
    }

    /**
     * GET /api/etudiants/{id}
     * Récupérer un étudiant par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EtudiantResponse> getEtudiantById(@PathVariable Integer id) {
        EtudiantResponse etudiant = etudiantService.getEtudiantById(id);
        return ResponseEntity.ok(etudiant);
    }

    /**
     * GET /api/etudiants/email/{email}
     * Rechercher un étudiant par email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<EtudiantResponse> getEtudiantByEmail(@PathVariable String email) {
        EtudiantResponse etudiant = etudiantService.getEtudiantByEmail(email);
        return ResponseEntity.ok(etudiant);
    }

    /**
     * GET /api/etudiants/numero/{numero}
     * Rechercher un étudiant par numéro d'inscription
     */
    @GetMapping("/numero/{numero}")
    public ResponseEntity<EtudiantResponse> getEtudiantByNumero(@PathVariable String numero) {
        EtudiantResponse etudiant = etudiantService.getEtudiantByNumeroInscription(numero);
        return ResponseEntity.ok(etudiant);
    }

    /**
     * POST /api/etudiants
     * Créer un nouvel étudiant
     */
    @PostMapping
    public ResponseEntity<EtudiantResponse> createEtudiant(@Valid @RequestBody EtudiantRequest request) {
        EtudiantResponse created = etudiantService.createEtudiant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/etudiants/{id}
     * Mettre à jour un étudiant
     */
    @PutMapping("/{id}")
    public ResponseEntity<EtudiantResponse> updateEtudiant(
            @PathVariable Integer id,
            @Valid @RequestBody EtudiantRequest request) {
        EtudiantResponse updated = etudiantService.updateEtudiant(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/etudiants/{id}
     * Supprimer un étudiant
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Integer id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
    }
}