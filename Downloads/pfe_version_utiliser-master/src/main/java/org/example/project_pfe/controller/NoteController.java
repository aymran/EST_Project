package org.example.project_pfe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.NoteRequest;
import org.example.project_pfe.dto.response.NoteResponse;
import org.example.project_pfe.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    /**
     * GET /api/notes?examenId=X
     * Récupérer les notes par examen
     */
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotes(
            @RequestParam(required = false) Integer examenId) {
        if (examenId != null) {
            return ResponseEntity.ok(noteService.getNotesByExamen(examenId));
        }
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    /**
     * GET /api/notes/{id}
     * Récupérer une note par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Integer id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    /**
     * POST /api/notes
     * Créer une nouvelle note
     */
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(request));
    }

    /**
     * PUT /api/notes/{id}
     * Mettre à jour une note
     */
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable Integer id,
            @Valid @RequestBody NoteRequest request) {
        return ResponseEntity.ok(noteService.updateNote(id, request));
    }

    /**
     * DELETE /api/notes/{id}
     * Supprimer une note
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Integer id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/notes/bulk
     * Créer ou mettre à jour plusieurs notes — payload: [{ etudiantId, examenId, valeur }]
     */
    @PostMapping("/bulk")
    public ResponseEntity<Void> saveBulkNotes(@RequestBody List<NoteRequest> notes) {
        noteService.saveBulk(notes);
        return ResponseEntity.ok().build();
    }
}