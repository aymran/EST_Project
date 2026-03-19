package org.example.project_pfe.service;

import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.MatiereRequest;
import org.example.project_pfe.dto.response.MatiereResponse;
import org.example.project_pfe.mapper.MatiereMapper;
import org.example.project_pfe.model.Enseignant;
import org.example.project_pfe.model.Matiere;
import org.example.project_pfe.model.Utilisateur;
import org.example.project_pfe.repository.EnseignantRepository;
import org.example.project_pfe.repository.MatiereRepository;
import org.example.project_pfe.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatiereService {

    private final MatiereRepository matiereRepository;
    private final EnseignantRepository enseignantRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final MatiereMapper mapper;

    /**
     * Créer une nouvelle matière
     */
    @Transactional
    public MatiereResponse createMatiere(MatiereRequest request) {
        if (!enseignantRepository.existsById(request.getIdEnseignant())) {
            throw new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + request.getIdEnseignant());
        }

        Matiere matiere = mapper.requestToEntity(request);
        Matiere saved = matiereRepository.save(matiere);
        return mapper.entityToResponse(saved);
    }

    /**
     * Récupérer une matière par ID
     */
    @Transactional(readOnly = true)
    public MatiereResponse getMatiereById(Integer id) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée avec l'ID: " + id));
        return mapper.entityToResponse(matiere);
    }

    /**
     * Récupérer toutes les matières
     */
    @Transactional(readOnly = true)
    public List<MatiereResponse> getAllMatieres() {
        return mapper.entityToResponseList(matiereRepository.findAll());
    }

    /**
     * Récupérer les matières par filière (pour ADMIN)
     */
    @Transactional(readOnly = true)
    public List<MatiereResponse> getMatieresByFiliere(Integer filiereId) {
        return mapper.entityToResponseList(
                matiereRepository.findByFiliere_IdFiliere(filiereId)
        );
    }

    /**
     * Récupérer les matières par filière et enseignant (pour ENSEIGNANT)
     */
    @Transactional(readOnly = true)
    public List<MatiereResponse> getMatieresByFiliereAndEnseignant(Integer filiereId, String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        if (!(utilisateur instanceof Enseignant)) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un enseignant");
        }

        Enseignant enseignant = (Enseignant) utilisateur;

        List<Matiere> matieres = enseignant.getMatieres().stream()
                .filter(m -> m.getFiliere().getIdFiliere().equals(filiereId))
                .collect(Collectors.toList());

        return mapper.entityToResponseList(matieres);
    }

    /**
     * Mettre à jour une matière
     */
    @Transactional
    public MatiereResponse updateMatiere(Integer id, MatiereRequest request) {
        Matiere existingMatiere = matiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée avec l'ID: " + id));

        if (!enseignantRepository.existsById(request.getIdEnseignant())) {
            throw new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + request.getIdEnseignant());
        }

        existingMatiere.setIntitule(request.getIntitule());
        existingMatiere.setCoefficient(request.getCoefficient());
        existingMatiere.setVolumeHoraire(request.getVolumeHoraire());
        existingMatiere.setEnseignant(mapper.map(request.getIdEnseignant()));

        Matiere updated = matiereRepository.save(existingMatiere);
        return mapper.entityToResponse(updated);
    }

    /**
     * Supprimer une matière
     */
    @Transactional
    public void deleteMatiere(Integer id) {
        if (!matiereRepository.existsById(id)) {
            throw new EntityNotFoundException("Matière non trouvée avec l'ID: " + id);
        }
        matiereRepository.deleteById(id);
    }
}