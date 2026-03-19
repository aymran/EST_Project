package org.example.project_pfe.service;

import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.ExamenRequest;
import org.example.project_pfe.dto.response.ExamenResponse;
import org.example.project_pfe.mapper.ExamenMapper;
import org.example.project_pfe.model.Examen;
import org.example.project_pfe.model.Salle;
import org.example.project_pfe.repository.ExamenRepository;
import org.example.project_pfe.repository.MatiereRepository;
import org.example.project_pfe.repository.SalleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamenService {

    private final ExamenRepository examenRepository;
    private final MatiereRepository matiereRepository;
    private final SalleRepository salleRepository;
    private final ExamenMapper mapper;

    /**
     * Calculate duree in minutes from "HH:mm" strings
     */
    private Integer calculateDuree(String heureDebut, String heureFin) {
        if (heureDebut == null || heureFin == null) return 0;
        LocalTime debut = LocalTime.parse(heureDebut);
        LocalTime fin   = LocalTime.parse(heureFin);
        return (int) Duration.between(debut, fin).toMinutes();
    }

    /**
     * Find Salle by its name/intitule — returns null if not found
     */
    private Salle resolveSalle(String salleName) {
        if (salleName == null || salleName.isBlank()) return null;
        return salleRepository.findByNumero(salleName).orElse(null);
    }

    /**
     * Créer un nouvel examen
     */
    @Transactional
    public ExamenResponse createExamen(ExamenRequest request) {
        if (!matiereRepository.existsById(request.getIdMatiere())) {
            throw new EntityNotFoundException("Matière non trouvée avec l'ID: " + request.getIdMatiere());
        }

        Examen examen = mapper.requestToEntity(request);
        examen.setDuree(calculateDuree(request.getHeureDebut(), request.getHeureFin()));
        examen.setSalle(resolveSalle(request.getSalle()));

        return mapper.entityToResponse(examenRepository.save(examen));
    }

    /**
     * Récupérer un examen par ID
     */
    @Transactional(readOnly = true)
    public ExamenResponse getExamenById(Integer id) {
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Examen non trouvé avec l'ID: " + id));
        return mapper.entityToResponse(examen);
    }

    /**
     * Récupérer tous les examens
     */
    @Transactional(readOnly = true)
    public List<ExamenResponse> getAllExamens() {
        return mapper.entityToResponseList(examenRepository.findAll());
    }

    /**
     * Récupérer les examens par matière
     */
    @Transactional(readOnly = true)
    public List<ExamenResponse> getExamensByMatiere(Integer matiereId) {
        return mapper.entityToResponseList(
                examenRepository.findByMatiere_IdMatiere(matiereId)
        );
    }

    /**
     * Mettre à jour un examen
     */
    @Transactional
    public ExamenResponse updateExamen(Integer id, ExamenRequest request) {
        Examen existing = examenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Examen non trouvé avec l'ID: " + id));

        if (!matiereRepository.existsById(request.getIdMatiere())) {
            throw new EntityNotFoundException("Matière non trouvée avec l'ID: " + request.getIdMatiere());
        }

        existing.setDateExamen(request.getDateExamen());
        existing.setDuree(calculateDuree(request.getHeureDebut(), request.getHeureFin()));
        existing.setTypeExamen(request.getTypeExamen());
        existing.setMatiere(mapper.mapMatiere(request.getIdMatiere()));
        existing.setSalle(resolveSalle(request.getSalle()));

        return mapper.entityToResponse(examenRepository.save(existing));
    }

    /**
     * Supprimer un examen
     */
    @Transactional
    public void deleteExamen(Integer id) {
        if (!examenRepository.existsById(id)) {
            throw new EntityNotFoundException("Examen non trouvé avec l'ID: " + id);
        }
        examenRepository.deleteById(id);
    }
}