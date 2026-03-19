package org.example.project_pfe.service;

import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.FiliereRequest;
import org.example.project_pfe.dto.response.FiliereResponse;
import org.example.project_pfe.mapper.FiliereMapper;
import org.example.project_pfe.model.Enseignant;
import org.example.project_pfe.model.Filiere;
import org.example.project_pfe.model.Utilisateur;
import org.example.project_pfe.repository.FiliereRepository;
import org.example.project_pfe.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FiliereService {

    private final FiliereRepository filiereRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final FiliereMapper mapper;

    /**
     * Créer une nouvelle filière
     */
    @Transactional
    public FiliereResponse createFiliere(FiliereRequest request) {
        // Vérifier si l'intitulé existe déjà
        if (filiereRepository.findByIntituleAndSemestre(request.getIntitule(),request.getSemestre()).isPresent()) {
            throw new IllegalArgumentException("Une filière avec cet intitulé existe déjà");
        }

        // Convertir Request -> Entity
        Filiere filiere = mapper.requestToEntity(request);

        // Sauvegarder
        Filiere saved = filiereRepository.save(filiere);

        // Convertir Entity -> Response
        return mapper.entityToResponse(saved);
    }

    /**
     * Récupérer une filière par ID
     */
    @Transactional(readOnly = true)
    public FiliereResponse getFiliereById(Integer id) {
        Filiere filiere = filiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filière non trouvée avec l'ID: " + id));

        return mapper.entityToResponse(filiere);
    }

    /**
     * Récupérer toutes les filières
     */
    @Transactional(readOnly = true)
    public List<FiliereResponse> getAllFilieres() {
        List<Filiere> filieres = filiereRepository.findAll();
        return mapper.entityToResponseList(filieres);
    }

    /**
     * Mettre à jour une filière
     */
    @Transactional
    public FiliereResponse updateFiliere(Integer id, FiliereRequest request) {
        Filiere existingFiliere = filiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filière non trouvée avec l'ID: " + id));

        // Vérifier l'unicité de l'intitulé si modifié

        if (!existingFiliere.getIntitule().equals(request.getIntitule())
                || !existingFiliere.getSemestre().equals(request.getSemestre())) {
            if (filiereRepository.findByIntituleAndSemestre(request.getIntitule(), request.getSemestre()).isPresent()) {
                throw new IllegalArgumentException("Une filière avec cet intitulé et ce semestre existe déjà");
            }
        }

        // Mettre à jour les champs
        existingFiliere.setIntitule(request.getIntitule());
        existingFiliere.setSemestre(request.getSemestre());

        Filiere updated = filiereRepository.save(existingFiliere);
        return mapper.entityToResponse(updated);
    }

    /**
     * Supprimer une filière
     */
    @Transactional
    public void deleteFiliere(Integer id) {
        if (!filiereRepository.existsById(id)) {
            throw new EntityNotFoundException("Filière non trouvée avec l'ID: " + id);
        }

        filiereRepository.deleteById(id);
    }

    /**
     * Rechercher une filière par intitulé
     */
    @Transactional(readOnly = true)
    public FiliereResponse getFiliereByIntitule(String intitule) {
        Filiere filiere = filiereRepository.findByIntitule(intitule)
                .orElseThrow(() -> new EntityNotFoundException("Filière non trouvée avec l'intitulé: " + intitule));

        return mapper.entityToResponse(filiere);
    }

    /**
     * Récupérer les filières associées aux matières de l'enseignant connecté
     */
    @Transactional(readOnly = true)
    public List<FiliereResponse> getFilieresByEnseignant(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        if (!(utilisateur instanceof Enseignant)) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un enseignant");
        }

        Enseignant enseignant = (Enseignant) utilisateur;
        
        // Stream les matières de l'enseignant, map vers les filières et déduplique par idFiliere
        List<Filiere> filieres = enseignant.getMatieres().stream()
                .map(matiere -> matiere.getFiliere())
                .filter(filiere -> filiere != null)
                .collect(Collectors.toMap(
                    Filiere::getIdFiliere,
                    filiere -> filiere,
                    (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .collect(Collectors.toList());

        return mapper.entityToResponseList(filieres);
    }
}