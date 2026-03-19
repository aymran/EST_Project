package org.example.project_pfe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.SalleRequest;
import org.example.project_pfe.dto.response.SalleResponse;
import org.example.project_pfe.service.SalleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
@RequiredArgsConstructor
public class SalleController {

    private final SalleService salleService;

    /**
     * GET /api/salles
     * Récupérer toutes les salles
     */
    @GetMapping
    public ResponseEntity<List<SalleResponse>> getAllSalles() {
        List<SalleResponse> salles = salleService.getAllSalles();
        return ResponseEntity.ok(salles);
    }

    /**
     * GET /api/salles/{id}
     * Récupérer une salle par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalleResponse> getSalleById(@PathVariable Integer id) {
        SalleResponse salle = salleService.getSalleById(id);
        return ResponseEntity.ok(salle);
    }

    /**
     * GET /api/salles/numero/{numero}
     * Rechercher une salle par numéro
     */
    @GetMapping("/numero/{numero}")
    public ResponseEntity<SalleResponse> getSalleByNumero(@PathVariable String numero) {
        SalleResponse salle = salleService.getSalleByNumero(numero);
        return ResponseEntity.ok(salle);
    }

    /**
     * POST /api/salles
     * Créer une nouvelle salle
     */
    @PostMapping
    public ResponseEntity<SalleResponse> createSalle(@Valid @RequestBody SalleRequest request) {
        SalleResponse created = salleService.createSalle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/salles/{id}
     * Mettre à jour une salle
     */
    @PutMapping("/{id}")
    public ResponseEntity<SalleResponse> updateSalle(
            @PathVariable Integer id,
            @Valid @RequestBody SalleRequest request) {
        SalleResponse updated = salleService.updateSalle(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/salles/{id}
     * Supprimer une salle
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable Integer id) {
        salleService.deleteSalle(id);
        return ResponseEntity.noContent().build();
    }
}