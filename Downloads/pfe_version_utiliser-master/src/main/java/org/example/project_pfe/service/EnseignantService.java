package org.example.project_pfe.service;

import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.EnseignantRequest;
import org.example.project_pfe.dto.response.EnseignantResponse;
import org.example.project_pfe.mapper.EnseignantMapper;
import org.example.project_pfe.model.Enseignant;
import org.example.project_pfe.model.Role;
import org.example.project_pfe.repository.EnseignantRepository;
import org.example.project_pfe.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EnseignantMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // ← ADD THIS

    /**
     * Créer un nouvel enseignant
     */
    @Transactional
    public EnseignantResponse createEnseignant(EnseignantRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Un enseignant avec cet email existe déjà");
        }

        // ── Capture plain password BEFORE encoding ──
        String plainPassword = request.getMotDePasse();

        Enseignant enseignant = mapper.requestToEntity(request);
        enseignant.setMotDePasse(passwordEncoder.encode(plainPassword));
        enseignant.setRole(Role.ROLE_ENSEIGNANT);

        Enseignant saved = enseignantRepository.save(enseignant);

        // ── Send welcome email (runs async, won't block the response) ──
        emailService.sendWelcomeEmail(
                saved.getEmail(),
                saved.getPrenom() + " " + saved.getNom(),
                plainPassword,
                "Teacher"
        );

        return mapper.entityToResponse(saved);
    }

    /**
     * Récupérer un enseignant par ID
     */
    @Transactional(readOnly = true)
    public EnseignantResponse getEnseignantById(Integer id) {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + id));
        return mapper.entityToResponse(enseignant);
    }

    /**
     * Récupérer tous les enseignants
     */
    @Transactional(readOnly = true)
    public List<EnseignantResponse> getAllEnseignants() {
        return mapper.entityToResponseList(enseignantRepository.findAll());
    }

    /**
     * Mettre à jour un enseignant
     */
    @Transactional
    public EnseignantResponse updateEnseignant(Integer id, EnseignantRequest request) {
        Enseignant existingEnseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + id));

        if (!existingEnseignant.getEmail().equals(request.getEmail())) {
            if (utilisateurRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Un enseignant avec cet email existe déjà");
            }
        }

        existingEnseignant.setNom(request.getNom());
        existingEnseignant.setPrenom(request.getPrenom());
        existingEnseignant.setEmail(request.getEmail());
        existingEnseignant.setGrade(request.getGrade());
        existingEnseignant.setSpecialite(request.getSpecialite());

        if (request.getMotDePasse() != null && !request.getMotDePasse().isEmpty()) {
            existingEnseignant.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        }

        Enseignant updated = enseignantRepository.save(existingEnseignant);
        return mapper.entityToResponse(updated);
    }

    /**
     * Supprimer un enseignant
     */
    @Transactional
    public void deleteEnseignant(Integer id) {
        if (!enseignantRepository.existsById(id)) {
            throw new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + id);
        }
        enseignantRepository.deleteById(id);
    }

    /**
     * Rechercher un enseignant par email
     */
    @Transactional(readOnly = true)
    public EnseignantResponse getEnseignantByEmail(String email) {
        Enseignant enseignant = enseignantRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé avec l'email: " + email));
        return mapper.entityToResponse(enseignant);
    }

    /**
     * Rechercher les enseignants par grade
     */
    @Transactional(readOnly = true)
    public List<EnseignantResponse> getEnseignantsByGrade(String grade) {
        return mapper.entityToResponseList(enseignantRepository.findByGrade(grade));
    }

    /**
     * Rechercher les enseignants par spécialité
     */
    @Transactional(readOnly = true)
    public List<EnseignantResponse> getEnseignantsBySpecialite(String specialite) {
        return mapper.entityToResponseList(enseignantRepository.findBySpecialite(specialite));
    }
}