package org.example.project_pfe.repository;

import org.example.project_pfe.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    // Used by bulk save — find existing note for student + examen
    Optional<Note> findByEtudiant_IdUserAndExamen_IdExamen(
            Integer etudiantId,
            Integer examenId
    );

    // Used by GET /api/notes?examenId=X
    List<Note> findByExamen_IdExamen(Integer examenId);
}