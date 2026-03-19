package org.example.project_pfe.service;

import lombok.RequiredArgsConstructor;
import org.example.project_pfe.dto.request.SalleRequest;
import org.example.project_pfe.dto.response.SalleResponse;
import org.example.project_pfe.mapper.SalleMapper;
import org.example.project_pfe.model.Salle;
import org.example.project_pfe.repository.SalleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalleService {

    private final SalleRepository salleRepository;
    private final SalleMapper mapper;

    /**
     * Créer une nouvelle salle
     */
    @Transactional
    public SalleResponse createSalle(SalleRequest request) {
        // Vérifier si le numéro existe déjà
        if (salleRepository.findByNumero(request.getNumero()).isPresent()) {
            throw new IllegalArgumentException("Une salle avec ce numéro existe déjà");
        }

        // Convertir Request -> Entity
        Salle salle = mapper.requestToEntity(request);

        // Sauvegarder
        Salle saved = salleRepository.save(salle);

        // Convertir Entity -> Response
        return mapper.entityToResponse(saved);
    }

    /**
     * Récupérer une salle par ID
     */
    @Transactional(readOnly = true)
    public SalleResponse getSalleById(Integer id) {
        Salle salle = salleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salle non trouvée avec l'ID: " + id));

        return mapper.entityToResponse(salle);
    }

    /**
     * Récupérer toutes les salles
     */
    @Transactional(readOnly = true)
    public List<SalleResponse> getAllSalles() {
        List<Salle> salles = salleRepository.findAll();
        return mapper.entityToResponseList(salles);
    }

    /**
     * Mettre à jour une salle
     */
    @Transactional
    public SalleResponse updateSalle(Integer id, SalleRequest request) {
        Salle existingSalle = salleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salle non trouvée avec l'ID: " + id));

        // Vérifier l'unicité du numéro si modifié
        if (!existingSalle.getNumero().equals(request.getNumero())) {
            if (salleRepository.findByNumero(request.getNumero()).isPresent()) {
                throw new IllegalArgumentException("Une salle avec ce numéro existe déjà");
            }
        }

        // Mettre à jour les champs
        existingSalle.setNumero(request.getNumero());
        existingSalle.setCapacite(request.getCapacite());
        existingSalle.setBloc(request.getBloc());
        existingSalle.setBooleanDisponible(request.getBooleanDisponible());

        Salle updated = salleRepository.save(existingSalle);
        return mapper.entityToResponse(updated);
    }

    /**
     * Supprimer une salle
     */
    @Transactional
    public void deleteSalle(Integer id) {
        if (!salleRepository.existsById(id)) {
            throw new EntityNotFoundException("Salle non trouvée avec l'ID: " + id);
        }

        salleRepository.deleteById(id);
    }

    /**
     * Rechercher une salle par numéro
     */
    @Transactional(readOnly = true)
    public SalleResponse getSalleByNumero(String numero) {
        Salle salle = salleRepository.findByNumero(numero)
                .orElseThrow(() -> new EntityNotFoundException("Salle non trouvée avec le numéro: " + numero));

        return mapper.entityToResponse(salle);
    }
}