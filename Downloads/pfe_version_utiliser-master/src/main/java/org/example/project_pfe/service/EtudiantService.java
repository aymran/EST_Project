package org.example.project_pfe.service;

import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.EtudiantRequest;
import org.example.project_pfe.dto.response.EtudiantResponse;
import org.example.project_pfe.mapper.EtudiantMapper;
import org.example.project_pfe.model.Etudiant;
import org.example.project_pfe.model.Role;
import org.example.project_pfe.repository.EtudiantRepository;
import org.example.project_pfe.repository.FiliereRepository;
import org.example.project_pfe.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final FiliereRepository filiereRepository;
    private final EtudiantMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // ← ADD THIS

    /**
     * Créer un nouvel étudiant
     */
    @Transactional
    public EtudiantResponse createEtudiant(EtudiantRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Un étudiant avec cet email existe déjà");
        }

        if (etudiantRepository.existsByNumeroInscription(request.getNumeroInscription())) {
            throw new IllegalArgumentException("Ce numéro d'inscription est déjà utilisé");
        }

        if (!filiereRepository.existsById(request.getIdFiliere())) {
            throw new EntityNotFoundException("Filière non trouvée avec l'ID: " + request.getIdFiliere());
        }

        // ── Capture plain password BEFORE encoding ──
        String plainPassword = request.getMotDePasse();

        Etudiant etudiant = mapper.requestToEntity(request);
        etudiant.setMotDePasse(passwordEncoder.encode(plainPassword));
        etudiant.setRole(Role.ROLE_ETUDIANT);

        Etudiant saved = etudiantRepository.save(etudiant);

        // ── Send welcome email (runs async, won't block the response) ──
        emailService.sendWelcomeEmail(
                saved.getEmail(),
                saved.getPrenom() + " " + saved.getNom(),
                plainPassword,
                "Student"
        );

        return mapper.entityToResponse(saved);
    }

    /**
     * Récupérer un étudiant par ID
     */
    @Transactional(readOnly = true)
    public EtudiantResponse getEtudiantById(Integer id) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé avec l'ID: " + id));
        return mapper.entityToResponse(etudiant);
    }

    /**
     * Récupérer tous les étudiants
     */
    @Transactional(readOnly = true)
    public List<EtudiantResponse> getAllEtudiants() {
        return mapper.entityToResponseList(etudiantRepository.findAll());
    }

    /**
     * Mettre à jour un étudiant
     */
    @Transactional
    public EtudiantResponse updateEtudiant(Integer id, EtudiantRequest request) {
        Etudiant existingEtudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé avec l'ID: " + id));

        if (!existingEtudiant.getEmail().equals(request.getEmail())) {
            if (utilisateurRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Un étudiant avec cet email existe déjà");
            }
        }

        if (!existingEtudiant.getNumeroInscription().equals(request.getNumeroInscription())) {
            if (etudiantRepository.existsByNumeroInscription(request.getNumeroInscription())) {
                throw new IllegalArgumentException("Ce numéro d'inscription est déjà utilisé");
            }
        }

        if (!filiereRepository.existsById(request.getIdFiliere())) {
            throw new EntityNotFoundException("Filière non trouvée avec l'ID: " + request.getIdFiliere());
        }

        existingEtudiant.setNom(request.getNom());
        existingEtudiant.setPrenom(request.getPrenom());
        existingEtudiant.setEmail(request.getEmail());
        existingEtudiant.setNumeroInscription(request.getNumeroInscription());
        existingEtudiant.setDateInscription(request.getDateInscription());
        existingEtudiant.setFiliere(mapper.map(request.getIdFiliere()));

        if (request.getMotDePasse() != null && !request.getMotDePasse().isEmpty()) {
            existingEtudiant.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        }

        Etudiant updated = etudiantRepository.save(existingEtudiant);
        return mapper.entityToResponse(updated);
    }

    /**
     * Supprimer un étudiant
     */
    @Transactional
    public void deleteEtudiant(Integer id) {
        if (!etudiantRepository.existsById(id)) {
            throw new EntityNotFoundException("Étudiant non trouvé avec l'ID: " + id);
        }
        etudiantRepository.deleteById(id);
    }

    /**
     * Rechercher un étudiant par email
     */
    @Transactional(readOnly = true)
    public EtudiantResponse getEtudiantByEmail(String email) {
        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé avec l'email: " + email));
        return mapper.entityToResponse(etudiant);
    }

    /**
     * Rechercher un étudiant par numéro d'inscription
     */
    @Transactional(readOnly = true)
    public EtudiantResponse getEtudiantByNumeroInscription(String numeroInscription) {
        Etudiant etudiant = etudiantRepository.findByNumeroInscription(numeroInscription)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé avec le numéro: " + numeroInscription));
        return mapper.entityToResponse(etudiant);
    }

    /**
     * Récupérer les étudiants par filière
     */
    @Transactional(readOnly = true)
    public List<EtudiantResponse> getEtudiantsByFiliere(Integer filiereId) {
        return mapper.entityToResponseList(etudiantRepository.findByFiliere_IdFiliere(filiereId));
    }
}