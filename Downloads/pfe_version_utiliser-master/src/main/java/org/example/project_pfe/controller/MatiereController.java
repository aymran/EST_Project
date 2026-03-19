package org.example.project_pfe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.MatiereRequest;
import org.example.project_pfe.dto.response.MatiereResponse;
import org.example.project_pfe.service.MatiereService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matieres")
@RequiredArgsConstructor
public class MatiereController {

    private final MatiereService matiereService;

    /**
     * GET /api/matieres?filiereId=X
     * Récupérer les matières par filière
     * ETUDIANT gets all matieres for their filiere (same as ADMIN)
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT', 'ETUDIANT')")
    public ResponseEntity<List<MatiereResponse>> getAllMatieres(
            @RequestParam Integer filiereId,
            Authentication authentication) {

        boolean isEnseignant = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ENSEIGNANT"));

        // Only ENSEIGNANT gets filtered to their own matieres
        // ADMIN and ETUDIANT get all matieres for the filiere
        List<MatiereResponse> matieres = isEnseignant
                ? matiereService.getMatieresByFiliereAndEnseignant(filiereId, authentication.getName())
                : matiereService.getMatieresByFiliere(filiereId);

        return ResponseEntity.ok(matieres);
    }

    /**
     * GET /api/matieres/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatiereResponse> getMatiereById(@PathVariable Integer id) {
        return ResponseEntity.ok(matiereService.getMatiereById(id));
    }

    /**
     * POST /api/matieres
     */
    @PostMapping
    public ResponseEntity<MatiereResponse> createMatiere(@Valid @RequestBody MatiereRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matiereService.createMatiere(request));
    }

    /**
     * PUT /api/matieres/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatiereResponse> updateMatiere(
            @PathVariable Integer id,
            @Valid @RequestBody MatiereRequest request) {
        return ResponseEntity.ok(matiereService.updateMatiere(id, request));
    }

    /**
     * DELETE /api/matieres/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Integer id) {
        matiereService.deleteMatiere(id);
        return ResponseEntity.noContent().build();
    }
}