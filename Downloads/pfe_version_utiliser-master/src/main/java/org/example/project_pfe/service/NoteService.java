package org.example.project_pfe.service;

import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.NoteRequest;
import org.example.project_pfe.dto.response.NoteResponse;
import org.example.project_pfe.mapper.NoteMapper;
import org.example.project_pfe.model.Enseignant;
import org.example.project_pfe.model.Note;
import org.example.project_pfe.repository.EnseignantRepository;
import org.example.project_pfe.repository.EtudiantRepository;
import org.example.project_pfe.repository.ExamenRepository;
import org.example.project_pfe.repository.NoteRepository;
import org.example.project_pfe.repository.UtilisateurRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final EtudiantRepository etudiantRepository;
    private final ExamenRepository examenRepository;
    private final EnseignantRepository enseignantRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final NoteMapper mapper;

    /**
     * Get the currently authenticated enseignant from the security context
     */
    private Enseignant getAuthenticatedEnseignant() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return enseignantRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé: " + email));
    }

    /**
     * Créer une nouvelle note
     */
    @Transactional
    public NoteResponse createNote(NoteRequest request) {
        if (!etudiantRepository.existsById(request.getIdEtudiant())) {
            throw new EntityNotFoundException("Étudiant non trouvé avec l'ID: " + request.getIdEtudiant());
        }
        if (!examenRepository.existsById(request.getIdExamen())) {
            throw new EntityNotFoundException("Examen non trouvé avec l'ID: " + request.getIdExamen());
        }
        if (!enseignantRepository.existsById(request.getIdEnseignant())) {
            throw new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + request.getIdEnseignant());
        }

        Note note = mapper.requestToEntity(request);
        if (note.getDateSaisie() == null) {
            note.setDateSaisie(LocalDate.now());
        }

        return mapper.entityToResponse(noteRepository.save(note));
    }

    /**
     * Récupérer une note par ID
     */
    @Transactional(readOnly = true)
    public NoteResponse getNoteById(Integer id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note non trouvée avec l'ID: " + id));
        return mapper.entityToResponse(note);
    }

    /**
     * Récupérer toutes les notes
     */
    @Transactional(readOnly = true)
    public List<NoteResponse> getAllNotes() {
        return mapper.entityToResponseList(noteRepository.findAll());
    }

    /**
     * Récupérer les notes par examen
     */
    @Transactional(readOnly = true)
    public List<NoteResponse> getNotesByExamen(Integer examenId) {
        return mapper.entityToResponseList(
                noteRepository.findByExamen_IdExamen(examenId)
        );
    }

    /**
     * Mettre à jour une note
     */
    @Transactional
    public NoteResponse updateNote(Integer id, NoteRequest request) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note non trouvée avec l'ID: " + id));

        if (!etudiantRepository.existsById(request.getIdEtudiant())) {
            throw new EntityNotFoundException("Étudiant non trouvé avec l'ID: " + request.getIdEtudiant());
        }
        if (!examenRepository.existsById(request.getIdExamen())) {
            throw new EntityNotFoundException("Examen non trouvé avec l'ID: " + request.getIdExamen());
        }
        if (!enseignantRepository.existsById(request.getIdEnseignant())) {
            throw new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + request.getIdEnseignant());
        }

        existingNote.setValeur(request.getValeur());
        existingNote.setCommentaire(request.getCommentaire());

        if (request.getDateSaisie() != null) {
            existingNote.setDateSaisie(request.getDateSaisie());
        }

        existingNote.setEtudiant(mapper.mapEtudiant(request.getIdEtudiant()));
        existingNote.setExamen(mapper.mapExamen(request.getIdExamen()));
        existingNote.setEnseignant(mapper.mapEnseignant(request.getIdEnseignant()));

        return mapper.entityToResponse(noteRepository.save(existingNote));
    }

    /**
     * Supprimer une note
     */
    @Transactional
    public void deleteNote(Integer id) {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException("Note non trouvée avec l'ID: " + id);
        }
        noteRepository.deleteById(id);
    }

    /**
     * Créer ou mettre à jour plusieurs notes (bulk)
     * Payload: [{ etudiantId, examenId, valeur }]
     */
    @Transactional
    public void saveBulk(List<NoteRequest> requests) {
        // Get the logged-in enseignant once for all notes in this batch
        Enseignant enseignant = getAuthenticatedEnseignant();

        for (NoteRequest request : requests) {
            if (request.getValeur() == null) continue; // skip blank entries

            Optional<Note> existing = noteRepository
                    .findByEtudiant_IdUserAndExamen_IdExamen(
                            request.getEtudiantId(),
                            request.getExamenId()
                    );

            if (existing.isPresent()) {
                // Update existing note
                Note note = existing.get();
                note.setValeur(request.getValeur());
                note.setDateSaisie(LocalDate.now());
                note.setEnseignant(enseignant);
                noteRepository.save(note);
            } else {
                // Create new note using getReferenceById to avoid detached entity issues
                Note note = new Note();
                note.setEtudiant(etudiantRepository.getReferenceById(request.getEtudiantId()));
                note.setExamen(examenRepository.getReferenceById(request.getExamenId()));
                note.setEnseignant(enseignant);
                note.setValeur(request.getValeur());
                note.setDateSaisie(LocalDate.now());
                noteRepository.save(note);
            }
        }
    }
}