package org.example.project_pfe.repository;

import org.example.project_pfe.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalleRepository extends JpaRepository<Salle, Integer> {
    Optional<Salle> findByNumero(String numero);
}