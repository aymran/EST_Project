package org.example.project_pfe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.ExamenRequest;
import org.example.project_pfe.dto.response.ExamenResponse;
import org.example.project_pfe.service.ExamenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examens")
@RequiredArgsConstructor
public class ExamenController {

    private final ExamenService examenService;

    /**
     * GET /api/examens
     * GET /api/examens?matiereId=5
     * Récupérer tous les examens (ou filtrés par matière)
     */
    @GetMapping
    public ResponseEntity<List<ExamenResponse>> getAllExamens(
            @RequestParam(required = false) Integer matiereId) {

        List<ExamenResponse> examens = matiereId != null
                ? examenService.getExamensByMatiere(matiereId)
                : examenService.getAllExamens();

        return ResponseEntity.ok(examens);
    }

    /**
     * GET /api/examens/{id}
     * Récupérer un examen par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExamenResponse> getExamenById(@PathVariable Integer id) {
        ExamenResponse examen = examenService.getExamenById(id);
        return ResponseEntity.ok(examen);
    }

    /**
     * POST /api/examens
     * Créer un nouvel examen
     */
    @PostMapping
    public ResponseEntity<ExamenResponse> createExamen(@Valid @RequestBody ExamenRequest request) {
        ExamenResponse created = examenService.createExamen(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/examens/{id}
     * Mettre à jour un examen
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExamenResponse> updateExamen(
            @PathVariable Integer id,
            @Valid @RequestBody ExamenRequest request) {
        ExamenResponse updated = examenService.updateExamen(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/examens/{id}
     * Supprimer un examen
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Integer id) {
        examenService.deleteExamen(id);
        return ResponseEntity.noContent().build();
    }
}